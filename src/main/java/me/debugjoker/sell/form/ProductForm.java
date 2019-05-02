package me.debugjoker.sell.form;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: ZhangMengwei
 * @create: 2019-05-02 17:17
 **/
@Data
public class ProductForm {
    private String productId;
    /*商品名称*/
    private String productName;
    /*单价*/
    private BigDecimal productPrice;
    /*库存*/
    private Integer productStock;
    /*描述*/
    private String productDescription;
    /*图片*/
    private String productIcon;
    /*类目编号*/
    private Integer categoryType;
}