package me.debugjoker.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: ZhangMengwei
 * @create: 2019-02-24 15:30
 **/
@Component
@Data
@ConfigurationProperties(prefix = "weChat")
public class WechatAccountConfig {

    private String mpAppId;

    private String mpAppSecret;
}