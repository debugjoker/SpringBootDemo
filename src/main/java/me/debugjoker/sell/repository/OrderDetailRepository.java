package me.debugjoker.sell.repository;

import me.debugjoker.sell.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: ZhangMengwei
 * @create: 2018-12-10 22:02
 **/

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findByOrderId(String orderId);
}
