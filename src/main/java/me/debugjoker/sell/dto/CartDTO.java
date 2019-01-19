package me.debugjoker.sell.dto;

import lombok.Data;

/**
 * @author: ZhangMengwei
 * @create: 2019-01-19 20:51
 * 购物车封装类
 **/
@Data
public class CartDTO {

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 数量
     */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}