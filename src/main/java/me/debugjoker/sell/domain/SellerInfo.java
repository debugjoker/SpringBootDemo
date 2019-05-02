package me.debugjoker.sell.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author: ZhangMengwei
 * @create: 2019-05-02 23:03
 **/
@Data
@Entity
public class SellerInfo {

    @Id
    private String sellerId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * openid
     */
    private String openid;

}