package me.debugjoker.sell.enums;

import lombok.Getter;

/**
 * @author: ZhangMengwei
 * @create: 2018-12-10 21:34
 * 订单状态枚举类
 **/
@Getter
public enum OrderStatusEnum implements CodeEnum {
    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "已取消"),
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}