package com.cloudling.offer.export;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-09-14  15:09
 * @Version 1.0
 **/
public interface OnGetExcelKey {

    String getNormalKey(Cell cell);

    String getProductKey(Cell cell,int pos);

    int getSize();

    String getId();

    int getStartRow(Sheet sheet);

    int getEndRow(Sheet sheet);

    boolean isPicture(Cell cell);

    String getPictures(int pos);


}
