package com.erp.data.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author Mqs
 * @Date 2018/11/7 22:38
 * @Desc
 */
@Data
public class CategoryFirstLevelDTO {

    private Long id;
    private Long storeId;
    private Integer categoryLevel;
    private String categoryName;
    private Long parentId;
    private List<CategorySecondLevelDTO> secondLevelDTOS;
}
