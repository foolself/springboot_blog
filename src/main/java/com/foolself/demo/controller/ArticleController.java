package com.foolself.demo.controller;

import com.foolself.demo.dao.ArticleRepository;
import com.foolself.demo.entity.Article;
import com.foolself.demo.entity.Category;
import com.foolself.demo.entity.Comment;
import com.foolself.demo.entity.Tag;
import com.foolself.demo.entity.form.ArticleForm;
import com.foolself.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author http://foolself.github.io
 * @date 2018/10/19 20:46
 */
@Controller
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    // TODO: 2018/10/23 post's author(user) how to get.
    @RequestMapping("/detail/{id}")
    public String article(@PathVariable("id") Integer id, Comment comment, Model model) {
        // TODO: 2018/10/24 判断下存在否，再执行
        Article article = articleService.findById(id);
        if (article == null) {
            return "/article/articlePage";
        }
        model.addAttribute("article", article);
        List<Tag> tagList = article.getTagList();
        model.addAttribute("tagList", tagList);
        List<Comment> commentList = commentService.findByArticleId(id);
        model.addAttribute("commentList", commentList);
        return "/article/articlePage";
    }

    // 用 ArticleForm 類作爲橋梁來完成 Article 的增改，因爲 Article 中成員有點複雜。
    @RequestMapping("/edit")
    public String edit(HttpServletRequest request, ArticleForm articleForm, Model model){
        String id = request.getParameter("id");
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);
        List<Tag> tagList = tagService.findAll();
        model.addAttribute("tagList", tagList);
        if (id == null) {
            return "/article/edit";
        }
        Article article = articleService.findById(Long.valueOf(id));
        if (article == null) {
            model.addAttribute("msg", "request article not exist!");
            return "/article/edit";
        }
        model.addAttribute("article", article);
        System.out.println(article.toString());
        return "/article/edit";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(HttpServletRequest request, ArticleForm articleForm, Model model) {
        Long id = articleService.save(articleForm).getId();
        return "redirect:/article/detail/" + id;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(HttpServletRequest request, ArticleForm articleForm, Model model) {
        Long id = articleService.update(articleForm).getId();
        return "redirect:/article/detail/" + id;
    }

    @RequestMapping("/delete")
    public String delete(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));
        // TODO: 2018/10/30 bug
        Integer authorId = articleService.findById(id).getUser().getUid();
        Integer uid = userService.findByUsername((String) request.getSession().getAttribute("username")).getUid();
        System.out.println("---> authorId: " + authorId);
        System.out.println("---> userId: " + uid);
        if (authorId == uid) {
            System.out.println("---> delete.");
            articleService.deleteById(id);
        }
        return "redirect:/index";
    }

    @RequestMapping("byCategory")
    public String byCategory(HttpServletRequest request, Model model){
        String name = request.getParameter("name");
        List<Article> articleList = articleService.findByCategory_Name(name, 0, 10).getContent();
        model.addAttribute("articleList", articleList);
        return "/article/summary";
    }

    @RequestMapping("byTag")
    public String byTag(HttpServletRequest request, Model model){
        String name = request.getParameter("name");
        // TODO: 2018/11/1 分页怎么搞，在 ArticleService 中添加方法？还是自定义 Query？
        List<Article> articleList = tagService.findByName(name).getArticleList();
        model.addAttribute("articleList", articleList);
        return "/article/summary";
    }

    @RequestMapping("/search")
    public String search(){
        return "articleList";
    }
}