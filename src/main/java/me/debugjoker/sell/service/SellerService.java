package me.debugjoker.sell.service;

import me.debugjoker.sell.domain.SellerInfo;

/**
 * 卖家端
 * @author: ZhangMengwei
 * @create: 2019-05-06 21:31
 **/

public interface SellerService {

    /***
     * 通过openid查询卖家端信息
     */
    SellerInfo findSellerInfoByOpenid(String openid);
}
