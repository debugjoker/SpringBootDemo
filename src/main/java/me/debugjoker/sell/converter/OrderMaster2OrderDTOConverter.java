package me.debugjoker.sell.converter;

import me.debugjoker.sell.domain.OrderMaster;
import me.debugjoker.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ZhangMengwei
 * @create: 2019-02-17 14:32
 * OrderMaster转换成OrderDTO类型
 **/

public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
        OrderDTO orderDTO = new OrderDTO();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderMaster orderMaster : orderMasterList) {
            BeanUtils.copyProperties(orderMaster, orderDTO);
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }
}