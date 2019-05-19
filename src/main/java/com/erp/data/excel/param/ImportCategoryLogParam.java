package com.erp.data.excel.param;

import lombok.Data;

import java.util.List;

/**
 * @Author Mqs
 * @Date 2018/11/12 23:40
 * @Desc
 */
@Data
public class ImportCategoryLogParam {
    /**
     * 导入文件名
     */
    private String fileName;

    /**
     * 导入文件url（阿里云oss的链接地址）
     */
    private String fileUrl;

    private List<ImportCategoryDetailLogParam> importCategoryDetailLogParams;
}
