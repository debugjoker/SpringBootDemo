package me.debugjoker.sell.controller;

import me.debugjoker.sell.domain.User;
import me.debugjoker.sell.enums.ResultEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static me.debugjoker.sell.enums.ResultEnum.AUTHORIZE_FAIL;

/**
 * @author: ZhangMengwei
 * @create: 2019-05-10 13:09
 **/
@Controller
@RequestMapping("/seller")
public class SellerController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(User user, HttpServletRequest request, Map<String, Object> map) {

        // 根据用户名和密码创建token
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        // 获取subject认证主体
        Subject subject = SecurityUtils.getSubject();
        try {
            // 开始认证，这一步会跳到我们自定义的realm中
            subject.login(token);
            request.getSession().setAttribute("user", user);
            // TODO 添加绝对路径
            return "redirect:/seller/order/list";
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("user", user);
            request.setAttribute("error", "用户名或密码错误！");
            // TODO 添加绝对路径
            return "redirect:/seller/login";
        }
    }

    @GetMapping("/unauthorized")
    public ModelAndView unauthorized(Map<String, Object> map) {
        map.put("msg", ResultEnum.AUTHORIZE_FAIL.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/error", map);
    }


    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "login";
    }

}