package com.cloudling.offer.util;

import com.alibaba.fastjson.JSON;

import com.cloudling.offer.bean.AttrBean;
import com.cloudling.offer.bean.ProductBean;
import com.cloudling.offer.bean.SpareBean;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-20  9:13
 * @Version 1.0
 **/
public class ExceltoBeanUtil {
    private final static String excel2003L = ".xls"; // 2003- 版本的excel
    private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel


    public static List<ProductBean> analysisExcel(String path) throws IOException {
        Workbook work = new HSSFWorkbook(new FileInputStream(path));

        List<ProductBean> list = new ArrayList<>();
        //遍历所有sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            Sheet sheet = work.getSheetAt(i);
            //解析sheet内容
            list.add(analysisSheet(sheet));
        }
        return list;
    }

    private static ProductBean analysisSheet(Sheet sheet) {
        ProductBean productBean = new ProductBean();
        Iterator<Row> rowIterator = sheet.rowIterator();

        if (rowIterator.hasNext()) {
            productBean.setCode(rowIterator.next().getCell(1).toString());
            productBean.setPrice(new Float(rowIterator.next().getCell(1).toString()));
        }

        List<SpareBean> spareList = new ArrayList<>();
        List<AttrBean> attrList = new ArrayList<>();
        String attrName = "";
        Boolean attr = Boolean.TRUE;
        String spareName = "";
        SpareBean spareBean = new SpareBean();
        while (rowIterator.hasNext()) {
            Row next = rowIterator.next();
            //获得迭代器
            Iterator<Cell> cellIterator = next.cellIterator();
            Cell next1 = null;
            if (cellIterator.hasNext()) {
                String name = cellIterator.next().toString();
                if (StringUtils.isNotBlank(name)) {
                    if (StringUtils.isBlank(spareName)) {
                        spareName = name;
                    }
                    next1 = cellIterator.next();
                    if (StringUtils.isBlank(next1.toString())) {
                        continue;
                    }
                    //spareName = name;
                } else {
                    spareBean.setName(spareName);
                    spareBean.setAttrBeans(attrList);
                    spareList.add(spareBean);
                    spareBean = new SpareBean();
                    attrList = new ArrayList<>();
                }
            }
            if (cellIterator.hasNext()) {
                if (next1 == null) {
                    next1 = cellIterator.next();
                }
                if (StringUtils.isBlank(next1.toString())) {
                    AttrBean attrBean = new AttrBean();
                    attrBean.setName(attrName);
                    attrBean.setNum(cellIterator.hasNext() ? cellIterator.next().toString() : "");
                    attrBean.setAttr(cellIterator.hasNext() ? cellIterator.next().toString() : "");
                    String string = cellIterator.next().toString();
                    attrBean.setPrice(new Float(cellIterator.hasNext() && StringUtils.isNotBlank(string) ? string: "0.0"));
                    attrList.add(attrBean);
                } else {
                    attrName = next1.toString();
                }
            }
        }
        productBean.setSpareBeans(spareList);
        return productBean;
    }




    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr
     *            ,fileName
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

    /**
     * 描述：对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell) {
        Object value = null;
        DecimalFormat df = new DecimalFormat("0"); // 格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); // 日期格式化
        DecimalFormat df2 = new DecimalFormat("0"); // 格式化数字

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }

}
