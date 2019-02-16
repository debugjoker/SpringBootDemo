# 1. Spring Boot 企业微信点餐系统学习 第四章学习笔记

## 1.1. 买家类目Dao层开发

### 1.1.1. 导入依赖

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
    <version>1.5.8.RELEASE</version>
</dependency>

<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.2.2</version>
</dependency>

<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.2.2</version>
</dependency>
```
### 1.1.2. 新建domain包写实体类

@Entity将数据库中的映射成为对象，标明是实体类、@Id和@GeneratedValue表明是自增主键、@Column表明是数据库中对应表中的列、@DynamicUpdate表明动态更新数据

如果数据库中的表名字和对应实体类不一致的话 可以使用注解@Table(name = "数据库中的名字")

```java
package me.debugjoker.sell.domain;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 类目表对应实体类
 * @author: Mengwei Zhang
 * @create: 2018-08-12 11:45
 **/
@Entity
@DynamicUpdate
public class ProductCategory {
    //类目ID
    @Id
    @GeneratedValue
    private Integer categoryId;
    //类目名
    @Column
    private String categoryName;
    //类目编号
    @Column
    private Integer categoryType;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryType=" + categoryType +
                '}';
    }
}
```

### 1.1.3. 新建repository包存放dao接口

类继承自JpaRepository&lt;这里写对应的实体类，这里写主键的类型&gt;

```java
package me.debugjoker.sell.repository;

import me.debugjoker.sell.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: Mengwei Zhang
 * @create: 2018-08-12 12:04
 **/

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

}
```
### 1.1.4. 配置文件

```xml
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    username: yourusername
    password: yourpassword
    url: jdbc:mysql://localhost:3306/weixin?useUnicode=true&characterEncoding=utf-8&useSSL=false
  jpa:
    show-sql: true
```

### 1.1.5. 测试类

注意repository.findOne(1)方法对应的Spring Boot 1.5.7.RELEASE版本 新版本没有该方法


```java
package me.debugjoker.sell.repository;

import me.debugjoker.sell.domain.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author: Mengwei Zhang
 * @create: 2018-08-12 12:06
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest(){
        ProductCategory productCategory = repository.findOne(1);
        System.out.println(productCategory.toString());
    }
}
```
如果想让更改时间自动修改的话要在实体类ProductCategory上添加@DynamicUpdate注解动态更新

### 1.1.6. 使用lombock

实体类可以不写Get和Set还有tostring还有构造器方法，具体如下

**引入lombock依赖**

```xml
<!--导入lombok依赖自动生成get和set方法-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

**IDEA里面安装插件**
Settings里面搜索plugins,之后在搜索lombook plugin插件安装就可以使用了
>使用方法：在实体类上添加注解@Data，这个注解会注解在类上, 为类提供读写属性就是Get和Set方法, 此外还提供了 equals()、hashCode()、toString() 方法

### 1.1.7. 测试时回滚数据库
>使用@Transactional注解在测试方法上，那么方法中对数据库的所有操作都会回滚

### 自定义dao接口方法

```java
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

}
```
接口方法命名有一定的规范，具体规范可搜索Spring data jpa方法命名规范

测试该方法,这里使用断言，断定返回值result的大小不等于0
```java
 @Test
public void findByCategoryTypeInTest(){
    List<Integer> list = Arrays.asList(0,1,2,3);
    List<ProductCategory> result = repository.findByCategoryTypeIn(list);
    Assert.assertNotEquals(0,result.size());
}
```
## 买家类目Service层开发
新建Service包存放Service类，新建CategoryService接口

```java
package me.debugjoker.sell.service;

import me.debugjoker.sell.domain.ProductCategory;

import java.util.List;

/**
 * @author: Mengwei Zhang
 * @create: 2018-08-19 20:54
 * 类目接口
 **/
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
    
}
```

新建impl包存放Service实现类,**实现类记得要添加@Service注解**
```java
package me.debugjoker.sell.service.impl;

import me.debugjoker.sell.domain.ProductCategory;
import me.debugjoker.sell.repository.ProductCategoryRepository;
import me.debugjoker.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Mengwei Zhang
 * @create: 2018-08-19 21:02
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repository.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}

```
写测试如下
```java
package me.debugjoker.sell.service.impl;

import me.debugjoker.sell.domain.ProductCategory;
import me.debugjoker.sell.service.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: Mengwei Zhang
 * @create: 2018-08-19 21:13
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void findOne() {
        ProductCategory productCategory = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> productCategoryList = categoryService.findAll();
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(Arrays.asList(0,1,2,3));
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory("男生专享",10);
        ProductCategory result = categoryService.save(productCategory);
        Assert.assertNotNull(result);
    }
}
```

