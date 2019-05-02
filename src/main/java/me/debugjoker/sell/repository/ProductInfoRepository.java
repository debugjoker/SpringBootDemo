package me.debugjoker.sell.repository;

import me.debugjoker.sell.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: Mengwei Zhang
 * @create: 2018-08-19 22:15
 **/

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);

}