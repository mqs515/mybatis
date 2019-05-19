package com.erp.data.dto;

import lombok.Data;

/**
 * @Author Mqs
 * @Date 2018/11/7 23:35
 * @Desc
 */
@Data
public class CategoryThirdLevelDTO {
    private Long id;
    private Long storeId;
    private Integer categoryLevel;
    private String categoryName;
    private Long parentId;
}
