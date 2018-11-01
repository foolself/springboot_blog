package com.foolself.demo.controller;

import com.foolself.demo.dao.UserRepository;
import com.foolself.demo.entity.Article;
import com.foolself.demo.entity.User;
import com.foolself.demo.entity.UserOnline;
import com.foolself.demo.service.ArticleService;
import com.foolself.demo.service.SessionService;
import com.foolself.demo.service.TagService;
import com.foolself.demo.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @RequestMapping("")
    @RequiresRoles("admin")
    public String admin(Model model){
        List<User> userList = userService.findAll();
        List<UserOnline> userOnlineList = sessionService.getUserOnline();
        List<String> tagList = tagService.getAllTagName();
        model.addAttribute("userList", userList);
        model.addAttribute("userOnlineList", userOnlineList);
        model.addAttribute("tagList", tagList);
        return "admin/admin";
    }

    @RequiresRoles("admin")
    @RequestMapping("/forceLogout")
    public String forceLogout(@RequestParam String id){
        System.out.println("--> AdminController.forceLogout()");
        System.out.println(id);
        try {
            sessionService.forceLogoutById(id);
            System.out.println("---> Success sessionService.forceLogout(id): " + id);
        } catch (Exception e){
            System.out.println("---> Fall sessionService.forceLogout(id): " + id);
            System.out.println(e);
        }
        return "redirect:/admin";
    }

    @RequestMapping("/userInfo")
    public String userInfo(@RequestParam(value = "uid") Integer uid, Model model){
        User user = userService.findByUid(uid);
        List<Article> articleList = articleService.findByUser_Uid(uid, 0, 50).getContent();
        model.addAttribute("user", user);
        model.addAttribute("articleList", articleList);
        return "/admin/userInfo.html";
    }

    // TODO: 2018/10/19 amdin 可以添加任意属性的 user，手动设置 user 权限，区别于普通用户注册
    @RequestMapping("/userAdd")
    @RequiresPermissions("user:add")
    public String userAdd(){
        return "userAdd";
    }

    @RequestMapping("/userDelete")
    public String userDelete(@RequestParam(value = "uid") Integer uid){
        // uid ==1 is admin user
        if (uid != 1){
            userService.deleteByUid(uid);
        }
        return "redirect:/admin";
    }

    @RequestMapping("/articleDelete")
    public String articleDelete(@RequestParam(value = "id") Integer id,
                                @RequestParam(value = "uid") Integer uid) {
        articleService.deleteById(id);
        return "redirect:/admin/userInfo?uid="+uid;
    }

    @RequestMapping("/tagDelete")
    public String tagDelete(@RequestParam(value = "name") String name) {
        tagService.deleteByName(name);
        return "redirect:/admin";
    }
}
