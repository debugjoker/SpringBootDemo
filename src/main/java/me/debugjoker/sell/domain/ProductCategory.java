package me.debugjoker.sell.domain;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 类目表对应实体类
 *
 * @author: Mengwei Zhang
 * @create: 2018-08-12 11:45
 **/
@Entity
@DynamicUpdate
@Data
public class ProductCategory {
    //类目ID
    @Id
    @GeneratedValue
    private Integer categoryId;
    //类目名
    @Column
    private String categoryName;
    //类目编号
    @Column
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public ProductCategory() {
    }
}