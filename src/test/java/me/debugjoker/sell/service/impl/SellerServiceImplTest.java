package me.debugjoker.sell.service.impl;

import lombok.extern.slf4j.Slf4j;
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
 * @create: 2019-05-06 21:35
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SellerServiceImplTest {

    @Autowired
    private SellerServiceImpl sellerService;

    public static final String OPENID = "abs";

    @Test
    public void findSellerInfoByOpenid() {
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(OPENID);
        Assert.assertEquals(OPENID, sellerInfo.getOpenid());
    }
}