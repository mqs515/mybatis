package com.erp.data.excel.param;

import lombok.Data;

/**
 * @Author Mqs
 * @Date 2018/11/12 23:42
 * @Desc
 */
@Data
public class ImportCategoryDetailLogParam {
    private Long storeId;
    private Integer categoryLevel;
    private String categoryName;
    private Long parentId;
}
