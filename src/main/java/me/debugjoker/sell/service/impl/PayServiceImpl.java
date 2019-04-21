package me.debugjoker.sell.service.impl;

import com.alibaba.fastjson.JSON;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import me.debugjoker.sell.dto.OrderDTO;
import me.debugjoker.sell.enums.ResultEnum;
import me.debugjoker.sell.exception.SellException;
import me.debugjoker.sell.service.OrderService;
import me.debugjoker.sell.service.PayService;
import me.debugjoker.sell.utils.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author: ZhangMengwei
 * @create: 2019-04-16 22:05
 **/
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】发起支付request={}", JSON.toJSONString(payRequest));

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付response={}", JSON.toJSONString(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        // 1.验证签名
        // 2.支付的状态
        // 3.支付金额
        // 4.支付人和下单人是否一致

        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知, payResponse = {}", JSON.toJSONString(payResponse));

        // 查询订单
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());

        // 判断订单是否存在
        if (orderDTO == null) {
            log.error("【微信支付】异步通知，订单不存在，orderId = {}", payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 判断订单金额是否一致
        if (!MathUtil.equals(orderDTO.getOrderAmount().doubleValue(), payResponse.getOrderAmount())) {
            log.error("【微信支付】异步通知，订单金额前后不一致，orderId={}, 微信异步通知金额为={},订单实际金额={}", payResponse.getOrderId(), payResponse.getOrderAmount(), orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERTIFY_ERROR);

        }

        // 修改订单的支付状态
        orderService.paid(orderDTO);
        return payResponse;
    }

    /***
     * 退款
     */
    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】 request={}", JSON.toJSONString(refundRequest));

        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】response={}", JSON.toJSONString(refundResponse));

        return refundResponse;
    }
}