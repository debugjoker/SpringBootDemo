# Spring Boot 企业微信点餐系统学习 第六章学习笔记

## 买家订单DAO

新建订单实体类OrderMaster

```java
package me.debugjoker.sell.domain;

import lombok.Data;
import me.debugjoker.sell.enums.OrderStatusEnum;
import me.debugjoker.sell.enums.PayStatusEnum;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: ZhangMengwei
 * @create: 2018-12-10 21:32
 * 订单实体类
 **/
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    /** 订单id */
    @Id
    private String orderId;

    /** 买家名字 */
    private String buyerName;

    /** 买家手机号 */
    private String buyerPhone;

    /** 买家地址 */
    private String buyerAddress;

    /** 买家微信openid */
    private String buyerOpenid;

    /** 订单总金额 */
    private BigDecimal orderAmount;

    /** 订单状态，默认新下单 */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态，默认未支付*/
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}
```
新建订单状态枚举类
```java
package me.debugjoker.sell.enums;

import lombok.Getter;

/**
 * @author: ZhangMengwei
 * @create: 2018-12-10 21:34
 * 订单状态枚举类
 **/
@Getter
public enum OrderStatusEnum {
    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "已取消"),
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
```

新建支付状态枚举类

```java
package me.debugjoker.sell.enums;

import lombok.Getter;

/**
 * @author: ZhangMengwei
 * @create: 2018-12-10 21:45
 **/
@Getter
public enum PayStatusEnum {
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

```
同理新建OrderDetail类
```java
package me.debugjoker.sell.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author: ZhangMengwei
 * @create: 2018-12-10 21:54
 * 订单详情表
 **/
@Entity
@Data
public class OrderDetail {

    @Id
    private String detailId;

    /**
     * 订单id
     */
    private String orderId;
    /**
     * 商品id
     */
    private String productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品单价
     */
    private BigDecimal productPrice;
    /**
     * 商品数量
     */
    private Integer productQuantity;
    /**
     * 商品小图
     */
    private String productIcon;
}
```
新建两者的Repository接口
```java
package me.debugjoker.sell.repository;

import me.debugjoker.sell.domain.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: ZhangMengwei
 * @create: 2018-12-10 21:59
 **/

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}

```

```java
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

```

然后是相关的测试类



OrderMasterRepositoryTest.java

```java
package me.debugjoker.sell.repository;

import me.debugjoker.sell.domain.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author: ZhangMengwei
 * @create: 2018-12-17 21:45
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123457");
        orderMaster.setBuyerName("二师兄");
        orderMaster.setBuyerPhone("1234534232");
        orderMaster.setBuyerAddress("水帘洞");
        orderMaster.setBuyerOpenid("112113");
        orderMaster.setOrderAmount(new BigDecimal(2.7));

        OrderMaster result = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() throws Exception {
        PageRequest request = new PageRequest(0, 1);

        Page<OrderMaster> result = orderMasterRepository.findByBuyerOpenid("112112", request);

        Assert.assertNotEquals(0,result.getTotalElements());
    }
}
```

OrderDetailRepositoryTest.java

```java
package me.debugjoker.sell.repository;

import me.debugjoker.sell.domain.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: ZhangMengwei
 * @create: 2018-12-17 22:22
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void save() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1111111122");
        orderDetail.setOrderId("12121214");
        orderDetail.setProductIcon("xxx.jpg");
        orderDetail.setProductId("1214413");
        orderDetail.setProductName("product");
        orderDetail.setProductPrice(new BigDecimal(2.6));
        orderDetail.setProductQuantity(35);

        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderIdTest() throws Exception {
        List<OrderDetail> result = repository.findByOrderId("12121214");

        Assert.assertNotEquals(0, result.size());
    }

}
```
## 买家订单service创建

OrderService.java

```java
package me.debugjoker.sell.service;

import me.debugjoker.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author: ZhangMengwei
 * @create: 2019-01-19 15:42
 **/

public interface OrderService {

    /** 创建订单 */
    OrderDTO create(OrderDTO orderDTO);

    /** 查询单个订单 */
    OrderDTO findOne(String orderId);

    /** 查询订单列表 */
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /** 取消订单 */
    OrderDTO cancel(OrderDTO orderDTO);

    /** 完结订单 */
    OrderDTO finish(OrderDTO orderDTO);

    /** 支付订单 */
    OrderDTO paid(OrderDTO orderDTO);
}

```

新建包名 dto 存放数据传输对象

新建OrderDTO.java

```java
package me.debugjoker.sell.dto;

import lombok.Data;
import me.debugjoker.sell.domain.OrderDetail;
import me.debugjoker.sell.enums.OrderStatusEnum;
import me.debugjoker.sell.enums.PayStatusEnum;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: ZhangMengwei
 * @create: 2019-01-19 15:59
 **/
@Data
public class OrderDTO {

    /** 订单id */
    private String orderId;

    /** 买家名字 */
    private String buyerName;

    /** 买家手机号 */
    private String buyerPhone;

    /** 买家地址 */
    private String buyerAddress;

    /** 买家微信openid */
    private String buyerOpenid;

    /** 订单总金额 */
    private BigDecimal orderAmount;

    /** 订单状态，默认新下单 */
    private Integer orderStatus;

    /** 支付状态，默认未支付*/
    private Integer payStatus;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    List<OrderDetail> orderDetailList;
}
```

新建OrderServiceImpl实现类

```java
package me.debugjoker.sell.service.impl;

import com.sun.org.apache.xml.internal.security.keys.KeyUtils;
import me.debugjoker.sell.domain.OrderDetail;
import me.debugjoker.sell.domain.OrderMaster;
import me.debugjoker.sell.domain.ProductInfo;
import me.debugjoker.sell.dto.CartDTO;
import me.debugjoker.sell.dto.OrderDTO;
import me.debugjoker.sell.enums.OrderStatusEnum;
import me.debugjoker.sell.enums.PayStatusEnum;
import me.debugjoker.sell.enums.ResultEnum;
import me.debugjoker.sell.exception.SellException;
import me.debugjoker.sell.repository.OrderDetailRepository;
import me.debugjoker.sell.repository.OrderMasterRepository;
import me.debugjoker.sell.service.OrderService;
import me.debugjoker.sell.service.ProductService;
import me.debugjoker.sell.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: ZhangMengwei
 * @create: 2019-01-19 16:08
 **/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(0);
        List<CartDTO> cartDTOList = new ArrayList<>();

        // 1.查询订单里的商品（数量，价格）
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            // 2.计算订单的总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            // 3.写入orderDetail订单数据库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            // Spring 提供的属性拷贝方法 将productInfo的属性拷贝到orderDetail
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);

            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity());
            cartDTOList.add(cartDTO);
        }

        // 写入orderMaster订单数据库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

//        BeanUtils.copyProperties(orderMaster, orderDTO);
        /**
        List<CartDTO> cartDTOList = new ArrayList<>();
        orderDTO.getOrderDetailList().stream()
         .map(e ->new CartDTO(e.getProductId(), e.getProductQuantity()))
         .collect(Collectors.toList());
         */

        // 4.扣库存
        productService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> detailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(detailList)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(detailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}
```

新建exception包放异常定义类

SellException.java

```java
package me.debugjoker.sell.exception;

import me.debugjoker.sell.enums.ResultEnum;

/**
 * @author: ZhangMengwei
 * @create: 2019-01-19 19:46
 **/

public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

}
```

ResultEnum.java
```java
package me.debugjoker.sell.enums;

import lombok.Getter;

/**
 * @author: ZhangMengwei
 * @create: 2019-01-19 19:47
 **/
@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "商品库存不足"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    ORDERDETAIL_NOT_EXIST(13, "订单详情不存在"),
    ;


    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
```

新建keyUtil.java
```java
package me.debugjoker.sell.utils;

import java.util.Random;

/**
 * @author: ZhangMengwei
 * @create: 2019-01-19 20:01
 **/

public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式：时间+随机数
     * 保证多线程时主键的唯一性
     **/
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer a = random.nextInt(900000) + 100000; // 生成6位随机数
        return System.currentTimeMillis() + String.valueOf(a);
    }
}
```

新建CartDTO.java

```java
package me.debugjoker.sell.dto;

import lombok.Data;

/**
 * @author: ZhangMengwei
 * @create: 2019-01-19 20:51
 * 购物车封装类
 **/
@Data
public class CartDTO {

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 数量
     */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
```

ProductService类添加两个方法

```
// 加库存
void increaseStock(List<CartDTO> cartDTOList);
    
// 减库存
void decreaseStock(List<CartDTO> cartDTOList);
```
ProductServiceImpl类添加方法实现

```
@Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);

            repository.save(productInfo);
        }
    }
```

### OrderServiceImpl测试类

新建OrderServiceImpl测试类
```java
package me.debugjoker.sell.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.debugjoker.sell.domain.OrderDetail;
import me.debugjoker.sell.dto.OrderDTO;
import me.debugjoker.sell.repository.OrderDetailRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: ZhangMengwei
 * @create: 2019-01-20 20:54
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "1101110";

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("王思聪");
        orderDTO.setBuyerAddress("南京路88号楼701室");
        orderDTO.setBuyerPhone("13388788878");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        // 购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123456");
        orderDetail.setProductQuantity(2);

        orderDetailList.add(orderDetail);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("创建订单 result={}", result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        String orderId = "1547995126238367642";
        OrderDTO orderDTO = orderService.findOne(orderId);
        log.info("查询单个订单 result = {}", orderDTO);
        Assert.assertNotNull(orderDTO);
    }

    @Test
    public void findList() {
        PageRequest request = new PageRequest(0, 2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, request);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne("1547995126238367642");
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne("1547995126238367642");
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne("1547995126238367642");
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
    }
}
```

OrderServiceImpl实现类添加方法实现

```
@Override
public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
    Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
    List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
    Page<OrderDTO> result = new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    return result;
}

@Override
@Transactional
public OrderDTO cancel(OrderDTO orderDTO) {
    OrderMaster orderMaster = new OrderMaster();


    // 判断订单状态
    if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
        log.error("[取消订单] 订单状态不正确，orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
        throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
    }

    // 修改订单状态
    orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
    BeanUtils.copyProperties(orderDTO, orderMaster);
    OrderMaster updateResult = orderMasterRepository.save(orderMaster);
    if (updateResult == null) {
        log.error("[取消订单] 更新失败, orderMaster={}", orderMaster);
        throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
    }

    // 返回库存
    if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
        log.error("[取消订单] 订单中无商品详情,orderDTO={}", orderDTO);
        throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
    }
    List<CartDTO> cartDTOList = new ArrayList<>();
    List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
    for (OrderDetail orderDetail : orderDetailList) {
        CartDTO cartDTO = new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity());
        cartDTOList.add(cartDTO);
    }
    productService.increaseStock(cartDTOList);

    // 如果已支付，需要退款
    if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
        //TODO
    }

    return orderDTO;
}

@Override
@Transactional
public OrderDTO finish(OrderDTO orderDTO) {
    // 判断订单状态
    if (!orderDTO.getPayStatus().equals(OrderStatusEnum.NEW.getCode())) {
        log.error("[完结订单] 订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
        throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
    }
    // 修改订单状态
    OrderMaster orderMaster = new OrderMaster();
    orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
    BeanUtils.copyProperties(orderDTO, orderMaster);
    OrderMaster updateResult = orderMasterRepository.save(orderMaster);

    if (updateResult == null) {
        log.error("[完结订单] 更新失败, orderMaster={}", orderMaster);
        throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
    }

    return orderDTO;
}

@Override
@Transactional
public OrderDTO paid(OrderDTO orderDTO) {
    // 判断订单状态
    if (!orderDTO.getPayStatus().equals(OrderStatusEnum.NEW.getCode())) {
        log.error("[支付订单] 订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
        throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
    }

    // 判断支付状态
    if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
        log.error("[支付订单] 订单支付状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getPayStatus());
        throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
    }

    // 修改支付状态
    OrderMaster orderMaster = new OrderMaster();
    orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
    BeanUtils.copyProperties(orderDTO, orderMaster);
    OrderMaster updateResult = orderMasterRepository.save(orderMaster);

    if (updateResult == null) {
        log.error("[支付订单] 更新失败, orderMaster={}", orderMaster);
        throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
    }

    return orderDTO;
}
```

ProductServiceImpl实现类添加方法实现
```
@Override
@Transactional
public void increaseStock(List<CartDTO> cartDTOList) {
    for (CartDTO cartDTO : cartDTOList) {
        ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
        productInfo.setProductStock(result);
        repository.save(productInfo);
    }
}
```

新建converter包存放类转换方法类-OrderMaster2OrderDTOConverter类
```java
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
```

## 买家订单
新建BuyerOrderController类

新建form包用来包装表单传过来的对象

新建OrderForm类
```java
package me.debugjoker.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author: ZhangMengwei
 * @create: 2019-02-17 21:30
 * 包装表单传过来的对象
 **/
@Data
public class OrderForm {
    
    /**
     * 买家姓名
     **/
    @NotEmpty(message = "姓名必填")
    private String name;

    /**
     * 买家手机号
     **/
    @NotEmpty(message = "手机号必填")
    private String phone;

    /**
     * 买家地址
     **/
    @NotEmpty(message = "地址必填")
    private String address;

    /**
     * 买家微信openid
     **/
    @NotEmpty(message = "openid必填")
    private String openid;

    /**
     * 购物车
     **/
    @NotEmpty(message = "购物车不能为空")
    private String items;

}
```

新建OrderForm2OrderDTOConverter类OrderForm转换成OrderDTO
```java
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
```

pom.xml中添加gson依赖
```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
</dependency>
```

6-11





