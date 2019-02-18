package me.debugjoker.sell.controller;

import lombok.extern.slf4j.Slf4j;
import me.debugjoker.sell.converter.OrderForm2OrderDTOConverter;
import me.debugjoker.sell.dto.OrderDTO;
import me.debugjoker.sell.enums.ResultEnum;
import me.debugjoker.sell.exception.SellException;
import me.debugjoker.sell.form.OrderForm;
import me.debugjoker.sell.service.OrderService;
import me.debugjoker.sell.utils.ResultVOUtil;
import me.debugjoker.sell.vo.ResultVO;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ZhangMengwei
 * @create: 2019-02-17 21:19
 **/
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    // 创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[创建订单] 参数不正确, orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("[创建订单] 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO createResult = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());

        return ResultVOUtil.success(map);
    }

    // 订单列表
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam("page") Integer page,
                                         @RequestParam("size") Integer size) {
        // TODO
        return null;
    }

    // 订单详情

    // 取消订单

}