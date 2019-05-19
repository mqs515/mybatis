package com.erp.data.excel.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author Mqs
 * @Date 2018/11/12 22:44
 * @Desc
 */
public class ExcelUtil {

    /**
     * 检查导入的excel文件
     * @param file
     * @throws Exception
     */
    public static void checkFile(MultipartFile file)throws Exception {
        if (ObjectUtils.isEmpty(file)){
            throw new Exception("导入的文件不存在！！！");
        }

        String filename = file.getOriginalFilename();
        if (!filename.endsWith(".xls") && !filename.endsWith(".xlsx")){
            throw new Exception("导入的文件不是excel格式的文件！！！");
        }
    }

    /**
     * 判断当前的文件版本，返回不同的WorkBook
     * @param file
     * @return
     */
    public static Workbook getWorkBook(MultipartFile file, InputStream inputStream){
        String filename = file.getOriginalFilename();
        // 创建WorkBook对象
        Workbook workbook = null;
        try {
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            // 2003
            if (filename.endsWith(".xls")){
                workbook = new HSSFWorkbook(inputStream);
            }
            // 2007
            if (filename.endsWith(".xlsx")){
                workbook = new XSSFWorkbook(inputStream);
            }
            if (filename.endsWith(".xls") && !filename.endsWith(".xlsx")){
                return null;
            }
            return workbook;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
