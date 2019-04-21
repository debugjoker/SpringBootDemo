package me.debugjoker.sell.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import me.debugjoker.sell.dto.OrderDTO;

/**
 * @author: ZhangMengwei
 * @create: 2019-04-16 22:03
 * 支付
 **/

public interface PayService {
    PayResponse create(OrderDTO orderDTO);

    PayResponse notify(String notifyData);

    RefundResponse refund(OrderDTO orderDTO);
}
