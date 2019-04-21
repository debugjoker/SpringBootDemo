package me.debugjoker.sell.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import me.debugjoker.sell.domain.OrderDetail;
import me.debugjoker.sell.enums.OrderStatusEnum;
import me.debugjoker.sell.enums.PayStatusEnum;
import me.debugjoker.sell.utils.EnumUtil;
import me.debugjoker.sell.utils.serializer.Date2LongSerializer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: ZhangMengwei
 * @create: 2019-01-19 15:59
 **/
@Data
public class OrderDTO {

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 买家名字
     */
    private String buyerName;

    /**
     * 买家手机号
     */
    private String buyerPhone;

    /**
     * 买家地址
     */
    private String buyerAddress;

    /**
     * 买家微信openid
     */
    private String buyerOpenid;

    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态，默认新下单
     */
    private Integer orderStatus;

    /**
     * 支付状态，默认未支付
     */
    private Integer payStatus;

    /**
     * 创建时间
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /**
     * 订单明细列表
     */
    List<OrderDetail> orderDetailList;

    /**
     * JSON序列化忽略该字段
     * 获取订单状态
     */
    @JSONField(serialize = false)
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    /**
     * JSON序列化忽略该字段
     * 获取支付状态
     */
    @JSONField(serialize = false)
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }

}