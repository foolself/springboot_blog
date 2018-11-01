package com.foolself.demo;

import com.foolself.demo.dao.ArticleRepository;
import com.foolself.demo.dao.TagRepository;
import com.foolself.demo.entity.Article;
import com.foolself.demo.entity.Comment;
import com.foolself.demo.entity.Tag;
import com.foolself.demo.entity.form.ArticleForm;
import com.foolself.demo.service.ArticleService;
import com.foolself.demo.service.CommentService;
import com.foolself.demo.service.TagService;
import com.foolself.demo.service.UserService;
import com.foolself.demo.utils.MyUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private TagService tagService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CommentService commentService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void hashTest() {
		int hashIterations = 2;//加密的次数
		String salt = "visitor";//盐值
		String credentials = "123456";//密码
		String hashAlgorithmName = "MD5";//加密方式
		Object simpleHash = new SimpleHash(hashAlgorithmName, credentials,
				salt, hashIterations);
		String s = simpleHash.toString();
		System.out.println("加密后的值----->" + s);
	}

	@Test
	public void userTest(){
		userService.deleteByUid(32);
	}

	@Test
	public void manyTomanyTest(){
		System.out.println("---> DemoApplicationTests.manyTomanyTest()");
		List<Article> articleList = articleService.findByCategory_Name("编程", 0, 10).getContent();
		for (Article article : articleList) {
			System.out.println(article.toString());
		}
	}
}