package com.erp.data.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author Mqs
 * @Date 2018/11/7 22:38
 * @Desc
 */
@Data
public class CategoryDTO {
    private List<CategoryFirstLevelDTO> firstLevelDTOS;
}
