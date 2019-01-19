//package me.debugjoker.sell.service.impl;
////
////import me.debugjoker.sell.domain.ProductInfo;
////import me.debugjoker.sell.enums.ProductStatusEnum;
////import me.debugjoker.sell.repository.ProductInfoRepository;
////import org.junit.Assert;
////import org.junit.Test;
////import org.junit.runner.RunWith;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.test.context.SpringBootTest;
////import org.springframework.data.domain.Page;
////import org.springframework.data.domain.PageRequest;
////import org.springframework.test.context.junit4.SpringRunner;
////
////import java.math.BigDecimal;
////import java.util.List;
////
////import static org.junit.Assert.*;
////
/////**
//// * @author: Mengwei Zhang
//// * @create: 2018-08-21 22:43
//// **/
////@RunWith(SpringRunner.class)
////@SpringBootTest
////public class ProductServiceImplTest {
////
////    @Autowired
////    private ProductServiceImpl productService;
////
////    @Test
////    public void findOne() {
////        ProductInfo productInfo = productService.findOne("123456");
////        Assert.assertEquals("123456",productInfo.getProductId());
////    }
////
////    @Test
////    public void findUpAll() {
////        List<ProductInfo> productInfoList = productService.findUpAll();
////        Assert.assertNotEquals(0,productInfoList.size());
////    }
////
////    @Test
////    public void findAll() {
////        PageRequest pageRequest = new PageRequest(0,2);
////        Page<ProductInfo> productInfoList = productService.findAll(pageRequest);
//////        System.out.println("totalElements = " + productInfoList.getTotalElements());
////        Assert.assertNotEquals(0,productInfoList.getTotalElements());
////    }
////
////    @Test
////    public void save() {
////        ProductInfo productInfo = new ProductInfo();
////        productInfo.setProductId("123457");
////        productInfo.setProductName("桂圆莲子粥");
////        productInfo.setProductPrice(new BigDecimal(7.2));
////        productInfo.setProductStock(100);
////        productInfo.setProductDescription("养生的粥");
////        productInfo.setProductIcon("http://xxxxx.jpg");
////        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
////        productInfo.setCategoryType(2);
////
////        ProductInfo result = productService.save(productInfo);
////        Assert.assertNotNull(result);
////    }
////}