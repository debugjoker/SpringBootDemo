package me.debugjoker.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author: ZhangMengwei
 * @create: 2019-02-24 13:43
 * 手工获取openId
 **/
@RestController
@Slf4j
@RequestMapping("/weixin")
public class WexinController {

    /**
     * 微信端直接访问这个链接即可拿到openid
     * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2f0ca69f45b1075e&redirect_uri=http://zlq.xxgblog.com/sell/weixin/auth&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect
     */
    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code) {
        log.info("进入auth方法中.......");
        log.info("code = {}", code);

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx2f0ca69f45b1075e&secret=fb863a5f7498fe9fb18ee5a3ae78c673&code=" + code + "&grant_type=authorization_code";
        RestTemplate template = new RestTemplate();
        String response = template.getForObject(url, String.class);
        log.info("response = {}", response);
    }
}