package me.debugjoker.sell.repository;

import me.debugjoker.sell.domain.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @author: ZhangMengwei
 * @create: 2018-12-17 21:45
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123453");
        orderMaster.setBuyerName("name");
        orderMaster.setBuyerPhone("1234534232");
        orderMaster.setBuyerAddress("test");
        orderMaster.setBuyerOpenid("112113");
        orderMaster.setOrderAmount(new BigDecimal(2.7));

        OrderMaster result = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() throws Exception {
        PageRequest request = new PageRequest(0, 1);

        Page<OrderMaster> result = orderMasterRepository.findByBuyerOpenid("112112", request);

        Assert.assertNotEquals(0, result.getTotalElements());
    }
}