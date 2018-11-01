package com.foolself.demo.controller;

import com.foolself.demo.entity.User;
import com.foolself.demo.service.SessionService;
import com.foolself.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * @author http://foolself.github.io
 * @date 2018/10/19 20:29
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Map<String, Object> map) throws Exception{
        System.out.println("HomeController.login()");
        String msg = request.getParameter("msg");
        String username = request.getParameter("username");
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名。
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("exception=" + exception);
        if (exception != null) {
            System.out.println("---> exception != null");
            if (UnknownAccountException.class.getName().equals(exception)) {
                System.out.println("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccountException -- > 账号不存在：";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                System.out.println("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                System.out.println("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                msg = "else >> "+exception;
                System.out.println("else -- >" + exception);
            }
        }
        map.put("username", username);
        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理
        return "login";
    }

    @RequestMapping(value = "/register", method= RequestMethod.GET)
    public String register(User user) {
        return "register";
    }

    // TODO: 2018/10/26 客户端进行表单验证后提交
    @RequestMapping(value = "/register", method= RequestMethod.POST)
    public String register(@ModelAttribute(value = "user") User user, Model model) {
        System.out.println("---> register: \n" + user.toString());
        String error = userService.save(user);
        if (error != null) {
            model.addAttribute("error", error);
            return "register";
        }
        model.addAttribute("user", user);
        return "registerSuccess";
    }

    // TODO: 2018/10/26 用户修改注册邮箱需邮箱验证，即发邮件给原邮箱确认。
    @RequestMapping(value = "/updateInfo", method= RequestMethod.POST)
    public String updateInfo(@ModelAttribute(value = "user") User user, Model model) {
        System.out.println("---> updateInfo: \n" + user.toString());
        String error = userService.updateInfo(user);
        userService.updateInfo(user);
        if (error != null) {
            model.addAttribute("error", error);
            return "userInfo";
        }
        model.addAttribute("user", user);
        return "/userInfo";
    }

    // TODO: 2018/10/26 客户端验证表单是否为空
    @RequestMapping(value = "/updatePassword", method= RequestMethod.POST)
    public String updatePassword(HttpServletRequest request,
                                 @Param(value = "username") String username,
                                 @Param(value = "pOld") String pOld,
                                 @Param(value = "pNew") String pNew
                                 ) {
        System.out.println("username: " + username + "\n pOld: " + pOld + "\npNew: " + pNew);
        String error = userService.updatePassword(pOld, pNew, username);
        if (error != null) {
            return "redirect:/userInfo?error=" + error;
        }
        // 密码修改成功后，用户需重新登陆
        SecurityUtils.getSubject().logout();
        return "redirect:/login?msg=login with new password: &username=" + username;
    }

    @RequestMapping("/userInfo")
    public String userInfo(HttpServletRequest request, Model model){
        System.out.println("---> UserController.userInfo()");
        String username = (String) request.getSession().getAttribute("username");
        if (username != null) {
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        return "userInfo";
    }
}
