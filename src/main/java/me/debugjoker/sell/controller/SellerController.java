package me.debugjoker.sell.controller;

import me.debugjoker.sell.domain.User;
import me.debugjoker.sell.enums.ResultEnum;
import me.debugjoker.sell.utils.ResultVOUtil;
import me.debugjoker.sell.vo.ResultVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    @ResponseBody
    public ResultVO login(User user, HttpServletRequest request, Boolean rememberMe) {

        // 根据用户名和密码创建token
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword(), rememberMe);
        // 获取subject认证主体
        Subject subject = SecurityUtils.getSubject();
        try {
            // 开始认证，这一步会跳到我们自定义的realm中
            subject.login(token);
            request.getSession().setAttribute("user", user);
            return ResultVOUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("user", user);
            request.setAttribute("error", "用户名或密码错误！");
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL.getCode(), ResultEnum.LOGIN_FAIL.getMessage());
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