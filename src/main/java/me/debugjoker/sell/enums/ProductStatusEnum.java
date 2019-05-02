package me.debugjoker.sell.enums;

import lombok.Getter;

/**
 * 商品状态枚举类
 *
 * @author: Mengwei Zhang
 * @create: 2018-08-21 22:36
 **/
@Getter
public enum ProductStatusEnum implements CodeEnum{
    UP(0, "在售"),
    DOWN(1, "下架");

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}