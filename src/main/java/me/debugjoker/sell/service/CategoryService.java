package me.debugjoker.sell.service;

import me.debugjoker.sell.domain.ProductCategory;

import java.util.List;

/**
 * @author: Mengwei Zhang
 * @create: 2018-08-19 20:54
 * 类目接口
 **/
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);

}
