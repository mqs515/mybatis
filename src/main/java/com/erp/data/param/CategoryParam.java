package com.erp.data.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Mqs
 * @Date: 2018/11/8 11:14
 * @Description:
 */
@Data
@ApiModel(value="com.erp.data.param.CategoryParam")
public class CategoryParam {

    @ApiModelProperty(value="categoryName类别名称")
    private String categoryName;

    @ApiModelProperty(value="storeId门店id")
    private Long storeId;

}
