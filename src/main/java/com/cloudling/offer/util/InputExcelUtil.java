package com.cloudling.offer.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-09-14  10:41
 * @Version 1.0
 **/
public class InputExcelUtil {

    public static void copyExcel(String fromexcel, String newexcel) {
        HSSFWorkbook wb = null;
        FileInputStream fis =null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(fromexcel);
            fos = new FileOutputStream(newexcel);
            wb = new HSSFWorkbook(fis);
            /*HSSFSheet fromsheet = wb.getSheetAt(0);
            Row row = fromsheet.getRow(1);
            Cell cell = row.getCell(2);
            cell.setCellValue("");*/
            wb.write(fos);
            fis.close();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if(fis != null)
                    fis.close();
                if(fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
