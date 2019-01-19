//package me.debugjoker.sell.repository;
//
//import lombok.extern.slf4j.Slf4j;
//import me.debugjoker.sell.domain.ProductCategory;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//
///**
// * @author: Mengwei Zhang
// * @create: 2018-08-12 12:06
// **/
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Slf4j
//public class ProductCategoryRepositoryTest {
//    @Autowired
//    private ProductCategoryRepository repository;
//
////    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryRepository.class);
//
//    @Test
//    public void findOneTest(){
//        ProductCategory productCategory = repository.findOne(1);
//        System.out.println(productCategory.toString());
//    }
//
//    @Test
//    @Transactional
//    public void saveOneTest(){
//        ProductCategory productCategory = new ProductCategory("女生最爱",7);
//        ProductCategory result = repository.save(productCategory);
//        Assert.assertNotNull(result);
//    }
////    @Test
////    public void saveTest(){
////        ProductCategory productCategory = repository.findOne(2);
////        productCategory.setCategoryType(3);
////        repository.save(productCategory);
////    }
//
////    @Test
////    public void testLog(){
////        String name = "debugjoker";
////        String password = "123456";
////        log.info("name:{},password:{}",name,password);
////        log.debug("debug...");
////        log.info("info...");
////        log.error("error...");
////    }
//
//    @Test
//    public void findByCategoryTypeInTest(){
//        List<Integer> list = Arrays.asList(0,1,2,3);
//        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
//        Assert.assertNotEquals(0,result.size());
//    }
//}