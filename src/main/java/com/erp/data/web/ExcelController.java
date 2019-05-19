package com.erp.data.web;

import com.erp.data.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author Mqs
 * @Date 2018/11/12 22:14
 * @Desc
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    /**
     * 导入的起始页
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(){
        ModelAndView view = new ModelAndView();
        view.setViewName("/index.html");
        return view;
    }

    /**
     * excel文件的导入
     * @param file
     * @return
     */
    @PostMapping("/import")
    public Boolean importExcel(MultipartFile file){
        Boolean ok = excelService.importExcel(file);
        return ok;
    }

    @PostMapping("/export")
    public void exportExcel(HttpServletResponse response){
        excelService.exportExcel(response);
    }

}
