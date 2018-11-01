package com.foolself.demo.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author http://foolself.github.io
 * @date 2018/10/22 19:24
 * 自定义 filter，在用户选择 rememberMe 方式登陆，下次访问网站时在不通过 login 时，向 session 中添加用户信息。
 */
public class MySessionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("---> MySessionFilter.doFilterInternal()");
        Subject subject = SecurityUtils.getSubject();
        if (subject.isRemembered()){
            String username = (String) subject.getPrincipal();
            System.out.println("---> username: " + username);
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            request.getSession().setAttribute("username", username);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
