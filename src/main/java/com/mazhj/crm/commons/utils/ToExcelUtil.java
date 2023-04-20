package com.mazhj.crm.commons.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

public class ToExcelUtil {
    public static void toExcel (OutputStream out, List<?> objectList, Class clazz, String name) throws IllegalAccessException, IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(name);
        HSSFRow row = sheet.createRow(0);
        //获取所有属性
        Field[] fields = clazz.getDeclaredFields();
        fields[0].setAccessible(true); //设置私有属性可见
        HSSFCell cell = row.createCell(0);
        //将所有属性名放入第一行
        cell.setCellValue(fields[0].getName());

        for (int i = 1; i <fields.length ; i++) {
            fields[i].setAccessible(true);
            cell = row.createCell(i);
            cell.setCellValue(fields[i].getName());
        }
        //遍历列表,依次将属性值放入表格中
        for (int i = 0; i < objectList.size(); i++) {
            row = sheet.createRow(i+1);
            for (int j = 0; j < fields.length; j++) {
                cell = row.createCell(j);
                cell.setCellValue((String) fields[j].get(objectList.get(i)));

            }

        }
        workbook.write(out);
        workbook.close();
    }
}
