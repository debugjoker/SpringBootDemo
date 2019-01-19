//package me.debugjoker.sell.service.impl;
//
//import me.debugjoker.sell.domain.ProductCategory;
//import me.debugjoker.sell.service.CategoryService;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
///**
// * @author: Mengwei Zhang
// * @create: 2018-08-19 21:13
// **/
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class CategoryServiceImplTest {
//
//    @Autowired
//    private CategoryService categoryService;
//
//    @Test
//    public void findOne() {
//        ProductCategory productCategory = categoryService.findOne(1);
//        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
//    }
//
//    @Test
//    public void findAll() {
//        List<ProductCategory> productCategoryList = categoryService.findAll();
//        Assert.assertNotEquals(0,productCategoryList.size());
//    }
//
//    @Test
//    public void findByCategoryTypeIn() {
//        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(Arrays.asList(0,1,2,3));
//        Assert.assertNotEquals(0,productCategoryList.size());
//    }
//
//    @Test
//    public void save() {
//        ProductCategory productCategory = new ProductCategory("男生专享",10);
//        ProductCategory result = categoryService.save(productCategory);
//        Assert.assertNotNull(result);
//    }
//}