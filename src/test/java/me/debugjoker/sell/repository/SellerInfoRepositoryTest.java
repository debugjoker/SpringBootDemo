package me.debugjoker.sell.repository;

import me.debugjoker.sell.domain.SellerInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author: ZhangMengwei
 * @create: 2019-05-02 23:08
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository repository;

    @Test
    public void save() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setOpenid("abs");
        sellerInfo.setPassword("asd");
        sellerInfo.setSellerId("sss");
        sellerInfo.setUsername("sqaq");

        SellerInfo result = repository.save(sellerInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOpenid() {
        SellerInfo result = repository.findByOpenid("abs");
        Assert.assertEquals("abs", result.getOpenid());
    }
}