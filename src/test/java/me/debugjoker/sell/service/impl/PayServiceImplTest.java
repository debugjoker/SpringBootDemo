package me.debugjoker.sell.service.impl;

import me.debugjoker.sell.dto.OrderDTO;
import me.debugjoker.sell.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: ZhangMengwei
 * @create: 2019-04-18 20:08
 **/

@SpringBootTest
@RunWith(SpringRunner.class)
public class PayServiceImplTest {

    @Autowired
    private PayServiceImpl payService;

    @Autowired
    private OrderService orderService;

    @Test
    public void create() {
        OrderDTO orderDTO = orderService.findOne("123453");
        payService.create(orderDTO);
    }

    @Test
    public void refund() {
        OrderDTO orderDTO = orderService.findOne("123453");
        payService.refund(orderDTO);
    }
}