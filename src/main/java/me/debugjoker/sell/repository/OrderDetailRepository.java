package me.debugjoker.sell.repository;

import me.debugjoker.sell.domain.OrderDetail;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: ZhangMengwei
 * @create: 2018-12-10 22:02
 **/
@CacheConfig(cacheNames = "OrderDetail")
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    @Cacheable
    List<OrderDetail> findByOrderId(String orderId);
}
