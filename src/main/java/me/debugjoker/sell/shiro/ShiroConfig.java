package me.debugjoker.sell.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 配置shiro
 *
 * @author: ZhangMengwei
 * @create: 2019-05-10 10:55
 **/
@Configuration
@Slf4j
public class ShiroConfig {

    /**
     * 注入自定义的 Realm
     */
    @Bean
    public MyRealm myAuthRealm() {
        MyRealm myRealm = new MyRealm();
        log.info("====myRealm注册完成=====");
        return myRealm;
    }

    /**
     * 注入安全管理器
     */
    @Bean
    public SecurityManager securityManager() {
        // 将自定义realm加进来
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(myAuthRealm());
        securityManager.setRememberMeManager(rememberMeManager());
        log.info("====securityManager注册完成====");
        return securityManager;
    }

    /**
     * cookie对象
     */
    public SimpleCookie rememberMeCookie() {
        // 设置cookie名称，对应login.html页面的<input type="checkbox" name="rememberMe"/>
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 设置cookie的过期时间，单位为秒，这里为一天
        cookie.setMaxAge(86400);
        return cookie;
    }

    /**
     * cookie管理对象
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberMe cookie加密的密钥
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    /**
     * 注入 Shiro 过滤器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        // 定义 shiroFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置自定义的 securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 设置默认登录的 URL，身份认证失败会访问该 URL
        shiroFilterFactoryBean.setLoginUrl("/seller/login");

        // 设置成功之后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/seller/order/list");

        // 权限认证失败会访问该 URL
        shiroFilterFactoryBean.setUnauthorizedUrl("/seller/unauthorized");

        // LinkedHashMap 是有序的，进行顺序拦截器配置
        Map<String, String> filterChainMap = new LinkedHashMap<>();

        // 配置可以匿名访问的地址，可以根据实际情况放行一些静态资源等，anon 表示放行
        filterChainMap.put("/css/**", "anon");
        filterChainMap.put("/imgs/**", "anon");
        filterChainMap.put("/js/**", "anon");

        // 登录 URL 放行
        filterChainMap.put("/sell/seller/login", "anon");

        // 下面的链接需要用户拥有admin角色才能访问
        filterChainMap.put("/seller/category*/**", "roles[admin]");
        filterChainMap.put("/seller/product*/**", "roles[admin]");

        // 下面的链接需要用户拥有user:cancel权限才能操作
        filterChainMap.put("/seller/order/cancel", "perms[\"user:cancel\"]");

        // 配置 logout 过滤器
        filterChainMap.put("/sell/seller/logout", "logout");

        filterChainMap.put("/**", "user");

        // 设置 shiroFilterFactoryBean 的 FilterChainDefinitionMap
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        log.info("====shiroFilterFactoryBean注册完成====");
        return shiroFilterFactoryBean;
    }
}