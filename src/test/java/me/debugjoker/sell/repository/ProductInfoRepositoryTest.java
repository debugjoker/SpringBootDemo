//package me.debugjoker.sell.repository;
//
//import me.debugjoker.sell.domain.ProductInfo;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
///**
// * @author: Mengwei Zhang
// * @create: 2018-08-19 22:23
// **/
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ProductInfoRepositoryTest {
//
//    @Autowired
//    private ProductInfoRepository productInfoRepository;
//
//    @Test
//    public void saveTest(){
//        ProductInfo productInfo = new ProductInfo();
//        productInfo.setProductId("123456");
//        productInfo.setProductName("皮蛋瘦肉粥");
//        productInfo.setProductPrice(new BigDecimal(3.2));
//        productInfo.setProductStock(100);
//        productInfo.setProductDescription("很好喝的粥");
//        productInfo.setProductIcon("http://xxx.jpg");
//        productInfo.setProductStatus(0);
//        productInfo.setCategoryType(2);
//
//        ProductInfo result = productInfoRepository.save(productInfo);
//        Assert.assertNotNull(result);
//    }
//
//    @Test
//    public void findByProductStatus() {
//        List<ProductInfo> productInfoList = productInfoRepository.findByProductStatus(0);
//        Assert.assertNotEquals(0,productInfoList.size());
//    }
//}