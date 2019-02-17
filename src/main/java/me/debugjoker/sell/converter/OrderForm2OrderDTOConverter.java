package me.debugjoker.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import me.debugjoker.sell.domain.OrderDetail;
import me.debugjoker.sell.dto.OrderDTO;
import me.debugjoker.sell.enums.ResultEnum;
import me.debugjoker.sell.exception.SellException;
import me.debugjoker.sell.form.OrderForm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ZhangMengwei
 * @create: 2019-02-17 22:15
 **/
@Slf4j
public class OrderForm2OrderDTOConverter {
    public static OrderDTO convert(OrderForm orderForm) {
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        // json中的String转成List<OrderDetail>
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        } catch (Exception e) {
            log.error("对象转换错误, String={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}