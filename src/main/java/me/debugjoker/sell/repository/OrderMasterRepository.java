package me.debugjoker.sell.repository;

import me.debugjoker.sell.domain.OrderMaster;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: ZhangMengwei
 * @create: 2018-12-10 21:59
 **/
@CacheConfig(cacheNames = "OrderMaster")
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    @Cacheable
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
