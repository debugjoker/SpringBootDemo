package me.debugjoker.sell.repository;

import me.debugjoker.sell.domain.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: ZhangMengwei
 * @create: 2019-05-02 23:06
 **/

public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {
    SellerInfo findByOpenid(String openid);
}
