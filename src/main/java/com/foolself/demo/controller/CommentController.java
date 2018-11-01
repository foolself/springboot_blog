package com.foolself.demo.controller;

import com.foolself.demo.entity.Article;
import com.foolself.demo.entity.Category;
import com.foolself.demo.entity.Comment;
import com.foolself.demo.entity.Tag;
import com.foolself.demo.entity.form.ArticleForm;
import com.foolself.demo.service.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author http://foolself.github.io
 * @date 2018/10/19 20:46
 */
@Controller
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(HttpServletRequest request, Comment comment, Model model) {
        System.out.println("---> CommentController.save()");
        System.out.println("---> comment: " + comment.toString());
        commentService.save(comment);
        return "redirect:/article/detail/" + comment.getArticleId();
    }

    @RequiresRoles("admin")
    @RequestMapping("/delete")
    public String delete(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));
        commentService.deleteById(id);
        return "redirect:/index";
    }

/*
    @RequestMapping("/detail/{id}")
    public String article(@PathVariable("id") Integer id, Model model) {
        // TODO: 2018/10/24 判断下存在否，再执行
        Article article = articleService.findById(id);
        if (article == null) {
            return "/article/articlePage";
        }
        model.addAttribute("article", article);

        List<Comment> commentList = commentService.findByArticleId(id);
        model.addAttribute("commentList", commentList );

        for (Comment comment : commentList) {
            System.out.println(comment.toString());
        }
        return "/article/articlePage";
    }

    */
}