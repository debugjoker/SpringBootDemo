package me.debugjoker.sell.service.impl;

import me.debugjoker.sell.domain.SellerInfo;
import me.debugjoker.sell.repository.SellerInfoRepository;
import me.debugjoker.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: ZhangMengwei
 * @create: 2019-05-06 21:33
 **/
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoRepository.findByOpenid(openid);
    }
}