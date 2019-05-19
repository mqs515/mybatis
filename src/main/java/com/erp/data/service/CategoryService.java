package com.erp.data.service;

import com.erp.data.dao.CategoryMapper;
import com.erp.data.domain.Category;
import com.erp.data.domain.CategoryExample;
import com.erp.data.dto.CategoryDTO;
import com.erp.data.dto.CategoryFirstLevelDTO;
import com.erp.data.dto.CategorySecondLevelDTO;
import com.erp.data.dto.CategoryThirdLevelDTO;
import com.erp.data.param.CategoryDetailParam;
import com.erp.data.param.CategoryParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Mqs
 * @Date 2018/11/7 22:22
 * @Desc
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 查询所有
     * @param param
     * @return
     */
    public CategoryDTO findAll(CategoryParam param) {
        // TODO level_1
        CategoryExample example = new CategoryExample();
        CategoryExample.Criteria criteria = example.createCriteria();
        criteria.andStoreIdEqualTo(param.getStoreId()).andParentIdEqualTo(0L);
        List<Category> categories = categoryMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(categories)){
            return null;
        }
        List<CategoryFirstLevelDTO> category1 = categories.stream().map(category -> {
            CategoryFirstLevelDTO firstLevelDTO = new CategoryFirstLevelDTO();
            BeanUtils.copyProperties(category, firstLevelDTO);
            // TODO level_2
            Long parentId = category.getId();
            CategoryExample example2 = new CategoryExample();
            CategoryExample.Criteria criteria2 = example2.createCriteria();
            criteria2.andParentIdEqualTo(parentId);
            List<Category> categories2 = categoryMapper.selectByExample(example2);
            if (CollectionUtils.isEmpty(categories2)){
                return null;
            }
            List<CategorySecondLevelDTO> categoryList2 = categories2.stream().map(category2 -> {
                CategorySecondLevelDTO categorySecondLevelDTO = new CategorySecondLevelDTO();
                BeanUtils.copyProperties(category2, categorySecondLevelDTO);
                // TODO level_3
                Long parentId2 = category2.getId();
                CategoryExample example3 = new CategoryExample();
                CategoryExample.Criteria criteria3 = example3.createCriteria();
                criteria3.andParentIdEqualTo(parentId2);
                List<Category> categories3 = categoryMapper.selectByExample(example3);
                if (CollectionUtils.isEmpty(categories3)){
                    return null;
                }
                List<CategoryThirdLevelDTO> categoryList3 = categories3.stream().map(category3 -> {
                    CategoryThirdLevelDTO categoryThirdLevelDTO = new CategoryThirdLevelDTO();
                    BeanUtils.copyProperties(category3, categoryThirdLevelDTO);
                    return categoryThirdLevelDTO;
                }).collect(Collectors.toList());
                categorySecondLevelDTO.setThirdLevelDTOS(categoryList3);
                return categorySecondLevelDTO;
            }).collect(Collectors.toList());
            firstLevelDTO.setSecondLevelDTOS(categoryList2);
            return firstLevelDTO;
        }).collect(Collectors.toList());

        // TODO level_1
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setFirstLevelDTOS(category1);
        return categoryDTO;
    }

    /**
     * 删除
     * @return
     */
    public Integer delete(CategoryDetailParam param) {
        int row = 0;
        // TODO 1、查看是否有关连得商品

        // 2、查看是否有子项
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andParentIdEqualTo(param.getId());
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        if (CollectionUtils.isEmpty(categories)){
            // 执行删除
            row = categoryMapper.deleteByPrimaryKey(param.getId());
        }
        return row;
    }

    /**
     * 详情
     * @param id
     * @return
     */
    public Category detail(Long id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        if (!ObjectUtils.isEmpty(category)){
            return category;
        }
        return null;
    }

    /**
     * 添加
     * @param param
     * @return
     */
    public Integer add(CategoryDetailParam param) {
        if (ObjectUtils.isEmpty(param)){
           return null;
        }

        Category category = new Category();
        BeanUtils.copyProperties(param, category);
        category.setId(null);
        category.setParentId( param.getId());
        category.setCategoryLevel(param.getCategoryLevel() + 1);

        int row = categoryMapper.insertSelective(category);
        return row;
    }

    /**
     * 修改
     * @param param
     * @return
     */
    public Integer update(CategoryDetailParam param) {
        Category category = new Category();
        BeanUtils.copyProperties(param, category);
        int row = categoryMapper.updateByPrimaryKeySelective(category);
        return row;

        // TODO 若修改后，则关联的所有信息都要更新
    }

    /**
     * 查询之前的数据回显
     * @param param
     * @return
     */
    public CategoryDTO findTwoLevel(CategoryParam param) {
        // TODO level_1
        CategoryExample example = new CategoryExample();
        CategoryExample.Criteria criteria = example.createCriteria();
        criteria.andStoreIdEqualTo(param.getStoreId()).andParentIdEqualTo(0L);
        List<Category> categories = categoryMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(categories)){
            return null;
        }
        List<CategoryFirstLevelDTO> category1 = categories.stream().map(category -> {
            CategoryFirstLevelDTO firstLevelDTO = new CategoryFirstLevelDTO();
            BeanUtils.copyProperties(category, firstLevelDTO);
            // TODO level_2
            Long parentId = category.getId();
            CategoryExample example2 = new CategoryExample();
            CategoryExample.Criteria criteria2 = example2.createCriteria();
            criteria2.andParentIdEqualTo(parentId);
            List<Category> categories2 = categoryMapper.selectByExample(example2);
            if (CollectionUtils.isEmpty(categories2)){
                return null;
            }
            List<CategorySecondLevelDTO> categoryList2 = categories2.stream().map(category2 -> {
                CategorySecondLevelDTO categorySecondLevelDTO = new CategorySecondLevelDTO();
                BeanUtils.copyProperties(category2, categorySecondLevelDTO);
                return categorySecondLevelDTO;
            }).collect(Collectors.toList());
            firstLevelDTO.setSecondLevelDTOS(categoryList2);
            return firstLevelDTO;
        }).collect(Collectors.toList());

        // TODO level_1
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setFirstLevelDTOS(category1);
        return categoryDTO;
    }

    public List<Category> listFuzzy(CategoryParam param) {
        if (ObjectUtils.isEmpty(param.getCategoryName()) || ObjectUtils.isArray(param.getStoreId())){
            return null;
        }

        CategoryExample example = new CategoryExample();
        CategoryExample.Criteria criteria = example.createCriteria();
        criteria.andStoreIdEqualTo(param.getStoreId()).andCategoryNameLike("%" + param.getCategoryName() + "%");

        List<Category> categories = categoryMapper.selectByExample(example);


        return categories;
    }

}
