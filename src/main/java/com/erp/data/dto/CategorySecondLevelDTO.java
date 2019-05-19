package com.erp.data.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author Mqs
 * @Date 2018/11/7 23:34
 * @Desc
 */
@Data
public class CategorySecondLevelDTO {
    private Long id;
    private Long storeId;
    private Integer categoryLevel;
    private String categoryName;
    private Long parentId;
    private List<CategoryThirdLevelDTO> thirdLevelDTOS;
}
