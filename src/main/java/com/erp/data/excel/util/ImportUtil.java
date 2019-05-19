package com.erp.data.excel.util;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
/**
 * @author Mqs
 * @date 2018/11/13 21:03
 * @desc excel导入处理每列的数据类型
 */
public class ImportUtil {

    public static String getStringFromRow(Row row, int i) {
        if(row != null && row.getCell(i) != null) {
            String cellStr = row.getCell(i).toString().trim();
            if(!"-".equals(cellStr)) {
                return cellStr;
            }
        }
        return null;
    }

    public static Long getLongFromRow(Row row, int i) {
        String cellStr = getStringFromRow(row, i);
        if(!StringUtils.isEmpty(cellStr)) {
            return Long.valueOf(trimDot(cellStr));
        }
        return null;
    }

    public static Integer getIntegerFromRow(Row row, int i) {
        Long cellLong = getLongFromRow(row, i);
        if(cellLong != null) {
            return cellLong.intValue();
        }
        return null;
    }

    public static BigDecimal getBigDecimalFromRow(Row row, int i) {
        String cellStr = getStringFromRow(row, i);
        if(!StringUtils.isEmpty(cellStr)) {
            return new BigDecimal(cellStr);
        }
        return null;
    }

    public static String trimDot(String str) {
        if(str != null && str.indexOf(".") != -1) str = str.substring(0, str.indexOf("."));
        return str;
    }
}
