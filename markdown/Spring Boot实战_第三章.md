Spring Boot 企业微信点餐系统学习 第三章笔记
===
## 开发环境搭建

### 为什么用给定的虚拟机

+ 里面搭建好了前端项目，此课程只专注于后端开发
+ 安装了Redis和Mysql

### 安装VirtualBox
下载安装之后将centos系统镜像导入

ifconfig 查看ip 我的虚拟机ip是192.168.1.109

cmd ipconfig 查看win10本机IP为192.168.1.110

设置好网络 互相能ping通

再用数据库管理软件HeidiSQl登录虚拟机MySQL数据库，建表

### 其他

JDK 1.8
Maven 
IDEA

### 新建IDEA项目

如何通过IDEA新建SpringBoot项目这里不详细说了    

### 日志框架的使用

可以使用 ```private final Logger logger = LoggerFactory.getLogger(ProductCategoryRepository.class);```

然后使用 ```logger.info()``` 等方法

或者使用@Slf4j注解，直接就可以使用```log.info()```和```log.error()```等方法，前提要引入lombok依赖

```xml
<!--导入lombok依赖自动生成get和set方法-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

### 如何在日志中输出变量

```java
String name = "debugjoker";
String password = "123456";
log.info("name:{},password:{}",name,password);

/*
*打印结果为 name:debugjoker,password:123456
*/
```

### 如何配置logback属性

**1.在application.yml文件中配置属性**
```
logging:
  pattern:
    console: "%d - %msg%n"
  level: debug
```

"%d - %msg%n"定义的是输出格式，输出格式如下

```
2018-08-12 18:56:00,027 - name:debugjoker,password:123456
2018-08-12 18:56:00,028 - info...
2018-08-12 18:56:00,028 - error...
```

level: debug是指输出级别，也可以特别指定某个类的级别，如下：
```
logging:
  pattern:
    console: "%d - %msg%n"
  level:
    me.debugjoker.sell.repository: debug
```
**2.在xml文件中配置**

在resource文件夹下新建logback-spring.xml如下

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<configuration>
    <appender name="consoleLog" class="ch.qos.logback.core.ConsoleAppender">
        <layout class="ch.qos.logback.classic.PatternLayout">
            <pattern>
                %d - %msg%n
            </pattern>
        </layout>
    </appender>

    <root level="info">
        <appender-ref ref="consoleLog"/>
    </root>
</configuration>
```


