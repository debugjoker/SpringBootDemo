package me.debugjoker.sell.domain;

import lombok.Data;

import javax.persistence.Entity;
import java.math.BigDecimal;
import javax.persistence.Id;
import java.util.Date;

/**
 * 商品
 * @author: Mengwei Zhang
 * @create: 2018-08-19 21:56
 **/
@Entity
@Data
public class ProductInfo {

    @Id
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
    /*商品状态,0正常1下架*/
    private Integer productStatus;
    /*类目编号*/
    private Integer categoryType;
    /*创建时间*/
//    private Date createTime;
    /*修改时间*/
//    private Date updateTime;

}