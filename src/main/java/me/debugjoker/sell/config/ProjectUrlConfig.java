package me.debugjoker.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: ZhangMengwei
 * @create: 2019-05-08 21:28
 **/
@Data
@Component
@ConfigurationProperties(prefix = "projectUrl")
public class ProjectUrlConfig {

    // 微信公众平台授权URL
    public String wechatMpAuthorize;

    // 点餐系统
    public String sell;
}