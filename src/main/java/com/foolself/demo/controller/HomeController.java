package com.foolself.demo.controller;

import com.foolself.demo.dao.CategoryRepository;
import com.foolself.demo.dao.TagRepository;
import com.foolself.demo.entity.Article;
import com.foolself.demo.entity.Category;
import com.foolself.demo.entity.User;
import com.foolself.demo.service.ArticleService;
import com.foolself.demo.service.CommentService;
import com.foolself.demo.service.TagService;
import com.foolself.demo.service.UserService;
import com.foolself.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping({"", "/index"})
    public String index(HttpServletRequest request, Model model) {
        // TODO: 2018/10/28 公共資源展示, articlelist, tagbar, commentbar
        List<Article> articleList = articleService.findAllOrderByLikes(0, 10).getContent();
        List<String> tagList = tagService.getAllTagName();
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("articleList", articleList);
        model.addAttribute("tagList", tagList);
        model.addAttribute("categoryList", categoryList);
        return "index";
    }

    @RequestMapping("/home")
    public String home(HttpServletRequest request, Model model) {
        // TODO: 2018/11/1 要做缓存呀！！！
        String username = (String) request.getSession().getAttribute("username");
        User user = userService.findByUsername(username);
        Pageable pageable = PageRequest.of(0, 10, new Sort(Sort.Direction.DESC, "id"));
        List<Article> articleList = articleService.findByUser_Uid(user.getUid(), 0, 10).getContent();
        Set<String> tagList = MyUtils.getTags(articleList);
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("articleList", articleList);
        model.addAttribute("tagList", tagList);
        model.addAttribute("categoryList", categoryList);
        return "userHome";
    }
}
