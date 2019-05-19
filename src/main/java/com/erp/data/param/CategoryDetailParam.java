package com.erp.data.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Mqs
 * @Date: 2018/11/8 11:33
 * @Description:
 */
@ApiModel(value = "com.erp.data.param.CategoryDetailParam")
@Data
public class CategoryDetailParam {

    @ApiModelProperty(value="id类别id")
    private Long id;

    @ApiModelProperty(value="storeId门店id")
    private Long storeId;

    @ApiModelProperty(value="categoryLevel类别level 1 第一级 2 第二级 3第三级")
    private Integer categoryLevel;

    @ApiModelProperty(value="categoryName类别名称")
    private String categoryName;

    @ApiModelProperty(value="parentId父id")
    private Long parentId;
}
