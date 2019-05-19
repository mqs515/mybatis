package com.erp.data.service;

import com.erp.data.dao.CategoryMapper;
import com.erp.data.domain.Category;
import com.erp.data.excel.param.ImportCategoryDetailLogParam;
import com.erp.data.excel.param.ImportCategoryLogParam;
import com.erp.data.excel.util.ExcelUtil;
import com.erp.data.excel.util.ImportUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Mqs
 * @Date 2018/11/12 22:16
 * @Desc
 */
@Service
public class ExcelService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * excel的导入
     * @param file
     * @return
     */
    public Boolean importExcel(MultipartFile file) {
        try {
            ExcelUtil.checkFile(file);
            InputStream inputStream = file.getInputStream();
            Workbook workBook = ExcelUtil.getWorkBook(file, inputStream);

            // 只取第一个工作薄
            Sheet sheet = workBook.getSheetAt(0);
            if (!ObjectUtils.isEmpty(sheet)){
                // 处理导入excel文件，并将记录保存到数据库
                ImportCategoryLogParam importCategoryLogParam = dealImportCategory(sheet);
                importCategoryLogParam.setFileName(file.getOriginalFilename());
                importCategoryLogParam.setFileUrl("this is import url");
                // 关闭文件流
                inputStream.close();
                // 导入成功
                List<ImportCategoryDetailLogParam> importCategoryDetailLogParams = importCategoryLogParam.getImportCategoryDetailLogParams();
                importCategoryDetailLogParams.forEach(i -> System.out.println(importCategoryDetailLogParams));
                // 添加数据库
                importCategoryDetailLogParams.forEach(importCategoryDetailLogParam -> {
                    Category category = new Category();
                    BeanUtils.copyProperties(importCategoryDetailLogParam, category);
                    category.setCreateTime(null);
                    category.setUpdateTime(null);
                    categoryMapper.insertSelective(category);
                });
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 处理导入的方法
     * @param sheet
     * @return
     */
    private ImportCategoryLogParam dealImportCategory(Sheet sheet) {
        ImportCategoryLogParam importCategoryLogParam = new ImportCategoryLogParam();
        ArrayList<ImportCategoryDetailLogParam> list = new ArrayList<>();
        for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            ImportCategoryDetailLogParam detailLogParam = new ImportCategoryDetailLogParam();
            detailLogParam.setStoreId(ImportUtil.getLongFromRow(row, 0));
            detailLogParam.setCategoryLevel(ImportUtil.getIntegerFromRow(row, 1));
            detailLogParam.setCategoryName(ImportUtil.getStringFromRow(row, 2));
            detailLogParam.setParentId(ImportUtil.getLongFromRow(row, 3));
            list.add(detailLogParam);
        }

        importCategoryLogParam.setImportCategoryDetailLogParams(list);
        return importCategoryLogParam;
    }

    /**
     * excel的导出
     * @param response
     */
    public void exportExcel(HttpServletResponse response) {
//        OutputStream out = null;
//        // 要导出的数据
//        List<Category> categorieList = categoryMapper.selectByExample(null);
//        // 设置表格表头
//        List<String[]> listHeaders = new ArrayList<>();
//        listHeaders.add(new String[] { "门店id", "类别等级", "总结", "" });
//        listHeaders.add(new String[] { "", "", "类别名称", "类别父id" });
//        // 设置合并单元格
//        List<Integer[]> merges = new ArrayList<>();
//        merges.add(new Integer[] { 0, 0, 2, 3 });
//        merges.add(new Integer[] { 0, 1, 0, 0 });
//        merges.add(new Integer[] { 0, 1, 1, 1 });
//        merges.add(new Integer[] { 0, 1, 4, 4 });
//        merges.add(new Integer[] { 0, 1, 5, 5 });
//        // 设置副表头
//        List<Integer[]> subheads = new ArrayList<>();
//        subheads.add(new Integer[] { 3, 3, 0, 5 });
//		// 设置列宽
//		List<Integer[]> columnWidths = new ArrayList<>();
//		columnWidths.add(new Integer[] { 0, 50 });
//		columnWidths.add(new Integer[] { 2, 50 });
//		// 设置行高
//		List<Integer[]> rowHeights = new ArrayList<>();
//		rowHeights.add(new Integer[] { 0, 50 });
//        // 导出
//        try {
//			Workbook book = GenerateXmlUtil.exportComplexExcel(listHeaders, categorieList, "第三方入账明细", merges, subheads, "宋体",
//					"宋体", 12, 10, true, false, true, true, "紫色", "灰色", true, true, true, null, columnWidths,rowHeights);
//            String fileName = "第三方入账明细" + ".xls";
//            response.reset(); // 清空response
//            response.setContentType("application/x-msdownload");
//            // 表示不能用浏览器直接打开
//            response.setHeader("Connection", "close");
//            // 告诉客户端允许断点续传多线程连接下载
//            response.setHeader("Accept-Ranges", "bytes");
//            response.setHeader("Content-Disposition",
//                    "attachment;filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
//            response.setCharacterEncoding("UTF-8");
//            out = response.getOutputStream();
//            book.write(out);
//            out.flush();
//        } catch (Exception e) {
//             e.printStackTrace();
//        }


    }
}
