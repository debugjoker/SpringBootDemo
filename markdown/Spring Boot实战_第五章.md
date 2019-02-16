# Spring Boot 企业微信点餐系统学习 第五章学习笔记

## 买家商品Dao层开发
新建商品实体类
```java
package me.debugjoker.sell.domain;

import lombok.Data;

import javax.persistence.Entity;
import java.math.BigDecimal;
import javax.persistence.Id;
import java.util.Date;

/**
 * 商品
 * @author: Mengwei Zhang
 * @create: 2018-08-19 21:56
 **/
@Entity
@Data
public class ProductInfo {

    @Id
    private String productId;
    /*商品名称*/
    private String productName;
    /*单价*/
    private BigDecimal productPrice;
    /*库存*/
    private Integer productStock;
    /*描述*/
    private String productDescription;
    /*图片*/
    private String productIcon;
    /*商品状态,0正常1下架*/
    private Integer productStatus;
    /*类目编号*/
    private Integer categoryType;
    /*创建时间*/
//    private Date createTime;
    /*修改时间*/
//    private Date updateTime;

}
```
在写Dao类
ProductInfoRepository类
```java
package me.debugjoker.sell.repository;

import me.debugjoker.sell.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: Mengwei Zhang
 * @create: 2018-08-19 22:15
 **/

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);

}
```
测试类
```java
package me.debugjoker.sell.repository;

import me.debugjoker.sell.domain.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: Mengwei Zhang
 * @create: 2018-08-19 22:23
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("皮蛋瘦肉粥");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好喝的粥");
        productInfo.setProductIcon("http://xxx.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);

        ProductInfo result = productInfoRepository.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfoList = productInfoRepository.findByProductStatus(0);
        Assert.assertNotEquals(0,productInfoList.size());
    }
}
```
## 买家商品Service层开发

Service下新建ProductService接口

```java
package me.debugjoker.sell.service;

import me.debugjoker.sell.domain.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品
 * @author: Mengwei Zhang
 * @create: 2018-08-21 22:23
 **/

public interface ProductService {
    ProductInfo findOne(String productId);

    /**
     * 查询所有在架商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    // 加库存
    void increaseStock(List<CartDTO> cartDTOList);
    
    // 减库存
    void decreaseStock(List<CartDTO> cartDTOList);
}
```

Impl包下写Service层接口实现类

```java
package me.debugjoker.sell.service.impl;

import me.debugjoker.sell.domain.ProductInfo;
import me.debugjoker.sell.enums.ProductStatusEnum;
import me.debugjoker.sell.repository.ProductInfoRepository;
import me.debugjoker.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Mengwei Zhang
 * @create: 2018-08-21 22:31
 **/
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }
}
```
findUpAll方法中要使用商品状态参数，这里可以使用自定义枚举类型实现，避免直接使用数字表示商品状态以避免他人查看代码时不理解。

新建enums包存放自定义枚举类型

```java
package me.debugjoker.sell.enums;

import lombok.Getter;

/**
 * 商品状态枚举类
 * @author: Mengwei Zhang
 * @create: 2018-08-21 22:36
 **/
@Getter
public enum  ProductStatusEnum {
    UP(0,"在售"),
    DOWN(1,"下架");

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
```
接下来写测试类,这里说一下可以使用IDEA的快捷键生成测试类，打开所要测试的类，按住Ctrl+Shift+T即可

```java
package me.debugjoker.sell.service.impl;

import me.debugjoker.sell.domain.ProductInfo;
import me.debugjoker.sell.enums.ProductStatusEnum;
import me.debugjoker.sell.repository.ProductInfoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: Mengwei Zhang
 * @create: 2018-08-21 22:43
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productService.findOne("123456");
        Assert.assertEquals("123456",productInfo.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<ProductInfo> productInfoList = productService.findAll(pageRequest);
//        System.out.println("totalElements = " + productInfoList.getTotalElements());
        Assert.assertNotEquals(0,productInfoList.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("桂圆莲子粥");
        productInfo.setProductPrice(new BigDecimal(7.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("养生的粥");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);

        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }
}
```

这里使用PageRequest对象，PageRequest继承自AbstractPageRequest,AbstractPageRequest实现Pageable, Serializable接口。

## 买家商品Controller层开发
查看项目前期约定好的[API开发文档](https://www.zybuluo.com/debugjoker/note/1262580)编写对应的Controller


新建controller包存放Controller类
```java
package me.debugjoker.sell.controller;

import me.debugjoker.sell.domain.ProductCategory;
import me.debugjoker.sell.domain.ProductInfo;
import me.debugjoker.sell.service.CategoryService;
import me.debugjoker.sell.service.ProductService;
import me.debugjoker.sell.utils.ResultVOUtil;
import me.debugjoker.sell.vo.ProductInfoVO;
import me.debugjoker.sell.vo.ProductVO;
import me.debugjoker.sell.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Mengwei Zhang
 * @create: 2018-08-26 18:39
 **/
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list() {
        // 查询所有上架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();
        // 查询所有类目
        List<Integer> categoryTypeList = new ArrayList<>();
        // 获取categoryTypeList
        for (ProductInfo productInfo : productInfoList) {
            categoryTypeList.add(productInfo.getCategoryType());
        }
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        // 数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }
}
```

新建VO视图对象包存放返回给前端的对象
```java
package me.debugjoker.sell.VO;

import lombok.Data;

/**
 * 返回给前端的数据封装
 * @author: Mengwei Zhang
 * @create: 2018-08-26 21:43
 **/
@Data
public class ResultVO<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回的具体内容
     */
    private T data;
}
```

新建ProductVO存放产品的返回JSON格式

注意：@JsonProperty("type")这个注解是可以将categoryType属性对外现实的JSON值改为"type"
```java
package me.debugjoker.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/** 返回给前端的商品详情（包含类目）
 * @author: Mengwei Zhang
 * @create: 2018-08-26 22:03
 **/
@Data
public class ProductVO {

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
```

新建ProductInfoVO存放商品详情JSON

注意：前端需要什么信息就只返回这些信息 避免多余的信息 可能造成损失
```java
package me.debugjoker.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 返回给前端的商品信息
 * @author: Mengwei Zhang
 * @create: 2018-08-26 22:07
 **/
@Data
public class ProductInfoVO {
    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
```
新建一个utils包封装返回的结果

新建ResultVOUtil
```java
package me.debugjoker.sell.utils;

import me.debugjoker.sell.vo.ResultVO;

/**
 * @author: ZhangMengwei
 * @create: 2018-12-09 19:11
 **/

public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

}
```
## 访问虚拟机
### ip地址访问
浏览器地址栏输入虚拟机的ip地址，发现跳转到open.weixin.qq.com地址栏了，这是因为cookie中缺少了openid，

先在浏览器地址栏输入 ```你的ip地址/#/order``` f12 console中输入 ```document.cookie='openid=abc123';``` 在访问```你的ip地址···

进入虚拟机修改nginx配置 

```vi /usr/local/nginx/conf/nginx.conf``` 

修改下面的配置项改为

```json
location /sell/ {
    proxy_pass http://127.0.0.1:8080/sell;
    // 127.0.0.1 换成自己电脑的ip地址
}
```
重启nginx ```nginx -s reload```

在访问虚拟机ip地址就可以看到了

### 用域名访问

修改nginx配置

将server_name 由localhost改为sell.com 后，重启nginx ```nginx -s reload```

修改本机的host文件将sell.com 域名解析到虚拟机ip 

将C:\Windows\System32\drivers\etc下的hosts文件添加一条记录 

虚拟机ip sell.com

再次访问sell.com即可

## 补充 

自己尝试过程中发现虚拟机ping主机ping不通，主机ping虚拟机可以。解决方案：

1. 打开windows防火墙 
2. 选择高级设置 
3.入站规则 
4. 找到配置文件类型为“公用”的“文件和打印共享（回显请求 – ICMPv4-In）”规则，设置为允许。

![图片](http://blog.51cto.com/attachment/201305/165205184.png)








