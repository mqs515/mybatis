package com.erp.data.web;

import com.erp.data.domain.Category;
import com.erp.data.dto.CategoryDTO;
import com.erp.data.param.CategoryDetailParam;
import com.erp.data.param.CategoryParam;
import com.erp.data.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Mqs
 * @Date 2018/11/7 22:21
 * @Desc 这个是用来发布项目的
 * 测试版本 怎么啦heihei
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * TODO 查询所有 【没有输入名称进行模糊查询】
     * @param param
     * @return
     */
    @PostMapping("/list")
    public CategoryDTO list(CategoryParam param){
        CategoryDTO list = categoryService.findAll(param);
       return list;
    }

    /**
     * 模糊查询商品类别
     * @param param
     * @return
     */
    @PostMapping("/listFuzzy")
    public List<Category> listFuzzy(CategoryParam param){
        List<Category> listFuzzy = categoryService.listFuzzy(param);
        return listFuzzy;
    }

    /**
     * 更新
     * @param param
     * @return
     */
    @PostMapping("/update")
    public Boolean update(CategoryDetailParam param){
        // 所有的类别都可以修改名称，若修改后，则关联的所有信息都要更新
        Integer row = categoryService.update(param);
        if (row > 0){
            return true;
        }
        return false;
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @PostMapping("/detail")
    public Category detail(Long id){
        Category category = categoryService.detail(id);
        return category;
    }

    /**
     * 删除
     * @param param
     * @return
     */
    @PostMapping("/delete")
    public String delete(CategoryDetailParam param){
        Integer boo = categoryService.delete(param);
        if (boo == 0){
            return "删除失败";
        }else if (boo == 1){
            return "删除成功";
        }else {
            return "删除失败，请先取消关联的商品";
        }
    }

    /**
     * TODO 添加之前得数据回显只有两级得上级分类 Level_1 和 level_2
     */
    @PostMapping("/listTwo")
    public CategoryDTO listTwoLevel(CategoryParam param){
        CategoryDTO list = categoryService.findTwoLevel(param);
        return list;
    }

    /**
     * 添加
     * @param param
     * @return
     */
    @PostMapping("/add")
    public Boolean add(CategoryDetailParam param){
        Integer row = categoryService.add(param);
        if (row > 0){
            return true;
        }
        return false;
    }


}
