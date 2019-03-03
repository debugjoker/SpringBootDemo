## 

## 微信开发文档
+ 官方文档 [https://mp.weixin.qq.com/wiki](https://mp.weixin.qq.com/wiki)
+ 支付文档 [https://pay.weixin.qq.com/wiki/doc/api/index.html](https://pay.weixin.qq.com/wiki/doc/api/index.html)
+ 调试 [https://natapp.cn](https://natapp.cn)
+ 第三方SDK [https://github.com/Wechat-Group/weixin-java-tools](https://github.com/Wechat-Group/weixin-java-tools)

## 手工获取openId

+ 申请微信测试账号 [地址](https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index)
+ 在网页服务里面的网页账号点击修改，设置回调域名。域名填下面这步购买的域名
+ 使用内网穿透工具 [内网穿透工具地址](https://natapp.cn),下载安装并购买隧道和二级域名，然后具体使用看官网教程。做这个的目的是将买的域名映射到本地127.0.0.1:8080端口

+ 然后看这个文档[微信网页授权](https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842),将下面的appid填测试账号页面的appid,redirect_uri填买的域名及自己定义的路由,scope填snsapi_base。
类似这个[域名](https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2f0ca69f45b1075e&redirect_uri=http://debugjoker.natapp1.cc/sell/weixin/auth&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect)

+ 获取code后，请求以下链接获取access_token [链接](https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx2f0ca69f45b1075e&secret=fb863a5f7498fe9fb18ee5a3ae78c673&code=CODE&grant_type=authorization_code)
返回json如下，拿到openid
```json
{
   "access_token": "18_FguGdtNHqPwFg-B0vLCM9gaswEynjvvJcw6upVIHSliIxOc94Aaq3CQDKW45hBMgiZ28ngRiKwTy8pE-DF73lwIxJSwBOPxw02fQP7VCZLU", 
   "expires_in": 7200, 
   "refresh_token": "18_1hGZrUrDvD2UI-1qP6NHEtdQ6YSwzCAf6RHUD2ufSHbjPzBtQDxdJafXNggO-hafk_X7ucG_XjCmIzJtJ1LF3mfpNwXXxWgqvInLqa3iIYU", 
   "openid": "o5qXt58QFNhZVFOgM3pdc06c25Sw", 
   "scope": "snsapi_base"
}
```

## SDK获取openId



