package me.debugjoker.sell.repository;

import me.debugjoker.sell.domain.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: ZhangMengwei
 * @create: 2018-12-17 22:22
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void save() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1111111122");
        orderDetail.setOrderId("12121214");
        orderDetail.setProductIcon("xxx.jpg");
        orderDetail.setProductId("1214413");
        orderDetail.setProductName("product");
        orderDetail.setProductPrice(new BigDecimal(2.6));
        orderDetail.setProductQuantity(35);

        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderIdTest() throws Exception {
        List<OrderDetail> result = repository.findByOrderId("12121214");

        Assert.assertNotEquals(0, result.size());
    }

}