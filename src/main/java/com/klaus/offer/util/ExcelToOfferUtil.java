package com.klaus.offer.util;

import com.klaus.offer.export.OnGetExcelKey;
import com.klaus.offer.export.ProData;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-26  9:23
 * @Version 1.0
 **/
public class ExcelToOfferUtil {


    private final static String excel2003L = ".xls"; // 2003- 版本的excel
    private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel



    /**
     * @Description: 遍历sheet
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */
    public static List<Sheet> getSheet(InputStream in, String fileName) throws Exception {
        // 根据文件名来创建Excel工作薄
        Workbook work = getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        // 返回数据
        List<Sheet> ls = new ArrayList<Sheet>();

        // 遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null)
                continue;
            ls.add(sheet);
        }
        work.close();
        return ls;
    }


    /**
     * 将流中的Excel数据转成List<Map>
     *
     * @param in       输入流
     * @param fileName 文件名（判断Excel版本）
     * @return
     * @throws Exception
     */
    public static String parseExcel(InputStream in, String fileName, OnGetExcelKey onGetExcelKey) throws Exception {
        // 根据文件名来创建Excel工作薄
        Workbook work = getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        // 返回数据
        List<HashMap<String, String>> ls = new ArrayList<>();

        // 遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null)
                continue;

            // 取第一行标题
//            row = sheet.getRow(0);
//            String title[] = null;
//            if (row != null) {
//                title = new String[row.getLastCellNum()];
//
//                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
//                    cell = row.getCell(y);
//                    title[y] = cell.getStringCellValue();
//                }
//
//            } else
//                continue;
            // 遍历当前sheet中的所有行
            //修改静态数据
            for (int j = 1; j < sheet.getLastRowNum()+1; j++) {
                row = sheet.getRow(j);
                HashMap<String, String> m = new HashMap<>();
                if(row!=null) {
                    for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                        cell = row.getCell(y);
                        if(cell!=null){
                            String key = onGetExcelKey.getNormalKey(cell);
                            sheet.getRow(j).getCell(y).setCellValue(key);
                        }

                    }
                }
            }
            //修改产品
            int size = onGetExcelKey.getSize();
            List<ProData> proDataList = new ArrayList<>();

            int start = onGetExcelKey.getStartRow(sheet);
            if(start==-1) continue;
            int end = onGetExcelKey.getEndRow(sheet);
            sheet.removeRow(sheet.getRow(start));
            sheet.removeRow(sheet.getRow(end));

            proDataList.add(new ProData(start,end,0));

            int lastRow = end-1;

            for(int j=1;j<size;j++){
                int s = lastRow;
                copyRows(sheet,start+1,end,lastRow);
                int e = lastRow + (end-start);
                proDataList.add(new ProData(s,e,j));
                lastRow = e-1;
            }


            for( int j=0;j<proDataList.size();j++){
                ProData proData = proDataList.get(j);
                for (int k = proData.startRow; k < proData.endRow; k++) {
                    row = sheet.getRow(k);
                    if(row!=null) {
                        for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                            cell = row.getCell(y);
                            if(onGetExcelKey.isPicture(cell)){
                                String pic = onGetExcelKey.getPictures(proData.pos);
                                insertPic(cell,pic);
                            }else{
                                String key = onGetExcelKey.getProductKey(cell,proData.pos);
                                sheet.getRow(k).getCell(y).setCellValue(key);
                            }

                        }
                    }


                }

            }
        }
        String res_url = "/out/"+onGetExcelKey.getId()+"_"+TimeUtil.getShortTimeStamp()+".xlsx";
        FileOutputStream excelFileOutPutStream = new FileOutputStream("assets/"+res_url);
        work.write(excelFileOutPutStream);
        work.close();
        return res_url;
    }


    /**
     * @Description:把图片输入到单元格
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */

    static CellRangeAddress getCellRange (Cell cell){
        List<CellRangeAddress> s = cell.getSheet().getMergedRegions();
        for(int i=0;i<s.size();i++){
            if(s.get(i).getFirstColumn()==cell.getColumnIndex() && s.get(i).getFirstRow()==cell.getRowIndex()){
                return  s.get(i);
            }
        }
        return null;
    }

    public static void insertPic(Cell cell,String pic){
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        //将图片读到BufferedImage
        BufferedImage bufferImg = null;
        try {
            bufferImg = ImageIO.read(new File(pic));
            if(pic.indexOf("jpg")>-1) {
                ImageIO.write(bufferImg, "jpg", byteArrayOut);
            }else if(pic.indexOf("png")>-1){
                ImageIO.write(bufferImg, "png", byteArrayOut);
            }else if(pic.indexOf("PNG")>-1){
                ImageIO.write(bufferImg, "PNG", byteArrayOut);
            }

            CellRangeAddress cellRangeAddress = getCellRange(cell);
            XSSFClientAnchor anchor;
            if(cellRangeAddress==null){
                anchor = new XSSFClientAnchor(10, 10, 10, 10,
                        cell.getColumnIndex(), cell.getRowIndex(), cell.getColumnIndex()+1, cell.getRowIndex()+1);
            }else{
                anchor = new XSSFClientAnchor(10, 10, 10, 10,
                        cell.getColumnIndex(), cell.getRowIndex(), cellRangeAddress.getLastColumn()+1, cellRangeAddress.getLastRow()+1);
            }


            // 插入图片
            Drawing patriarch = cell.getSheet().createDrawingPatriarch();

            patriarch.createPicture(anchor, cell.getSheet().getWorkbook().addPicture(byteArrayOut
                    .toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 复制行
     *
     *            结束行
     * @param pPosition
     *           目标起始行位置
     */
    public static void copyRows(Sheet currentSheet ,int startRow, int endRow, int pPosition) {
        int pStartRow = startRow - 1;
        int pEndRow = endRow - 1;
        int targetRowFrom;
        int targetRowTo;
        int columnCount = 0;
        CellRangeAddress region = null;
        int i;
        int j;
        if (pStartRow == -1 || pEndRow == -1) {
            return;
        }
        for (i = 0; i < currentSheet.getNumMergedRegions(); i++) {
            region = currentSheet.getMergedRegion(i);
            if ((region.getFirstRow() >= pStartRow)
                    && (region.getLastRow() <= pEndRow)) {
                targetRowFrom = region.getFirstRow() - pStartRow + pPosition;
                targetRowTo = region.getLastRow() - pStartRow + pPosition;
                CellRangeAddress newRegion = region.copy();
                newRegion.setFirstRow(targetRowFrom);
                newRegion.setFirstColumn(region.getFirstColumn());
                newRegion.setLastRow(targetRowTo);
                newRegion.setLastColumn(region.getLastColumn());
                try {
                    currentSheet.addMergedRegion(newRegion);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
        for (i = pStartRow; i <= pEndRow; i++) {
            Row sourceRow =  currentSheet.getRow(i);
            try {
                columnCount = sourceRow.getLastCellNum();
            }catch (Exception e){
              //  e.printStackTrace();
            }

            if (sourceRow != null) {
                int lastRowNo = currentSheet.getLastRowNum();
                try {
                    currentSheet.shiftRows(pPosition - pStartRow
                            + i, lastRowNo, 1);
                }catch (Exception e){
                   // e.printStackTrace();
                }

                Row newRow = currentSheet.createRow(pPosition - pStartRow
                        + i);
                newRow.setHeight(sourceRow.getHeight());
                for (j = 0; j < columnCount; j++) {
                    Cell templateCell = sourceRow.getCell(j);
                    if (templateCell != null) {
                        Cell newCell = newRow.createCell(j);
                        copyCell(templateCell, newCell);
                    }
                }


            }
        }
    }

    private static void copyCell(Cell srcCell, Cell distCell) {
        distCell.setCellStyle(srcCell.getCellStyle());
        if (srcCell.getCellComment() != null) {
            distCell.setCellComment(srcCell.getCellComment());
        }
        int srcCellType = srcCell.getCellType();
        distCell.setCellType(srcCellType);
        if (srcCellType == HSSFCell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(srcCell)) {
                distCell.setCellValue(srcCell.getDateCellValue());
            } else {
                distCell.setCellValue(srcCell.getNumericCellValue());
            }
        } else if (srcCellType == HSSFCell.CELL_TYPE_STRING) {
            distCell.setCellValue(srcCell.getRichStringCellValue());
        } else if (srcCellType == HSSFCell.CELL_TYPE_BLANK) {
            // nothing21
        } else if (srcCellType == HSSFCell.CELL_TYPE_BOOLEAN) {
            distCell.setCellValue(srcCell.getBooleanCellValue());
        } else if (srcCellType == HSSFCell.CELL_TYPE_ERROR) {
            distCell.setCellErrorValue(srcCell.getErrorCellValue());
        } else if (srcCellType == HSSFCell.CELL_TYPE_FORMULA) {
            distCell.setCellFormula(srcCell.getCellFormula());
        } else { // nothing29

        }
    }


    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr ,fileName
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr); // 2003-
        } else if (excel2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr); // 2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }


}