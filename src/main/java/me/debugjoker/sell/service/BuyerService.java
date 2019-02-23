package me.debugjoker.sell.service;

import me.debugjoker.sell.dto.OrderDTO;

/**
 * @author: ZhangMengwei
 * @create: 2019-02-23 17:22
 **/

public interface BuyerService {
    // 查询一个订单
    OrderDTO findOrderOne(String openId, String orderId);

    // 取消一个订单
    OrderDTO cancelOrderOne(String openId, String orderId);
}
