package me.debugjoker.sell.repository;

import me.debugjoker.sell.domain.ProductInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: Mengwei Zhang
 * @create: 2018-08-19 22:15
 **/
@CacheConfig(cacheNames = "ProductInfo")
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    @Cacheable
    List<ProductInfo> findByProductStatus(Integer productStatus);

}