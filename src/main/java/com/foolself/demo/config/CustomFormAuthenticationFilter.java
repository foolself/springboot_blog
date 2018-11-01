package com.foolself.demo.config;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author http://foolself.github.io
 * @date 2018/10/23 18:21
 * 用户登陆成功时，添加用户信息到 session 。
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("---> CustomFormAuthenticationFilter.onLoginSuccess()");
        String username = (String) subject.getPrincipal();
        System.out.println("---> username: " + username);
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        httpServletRequest.getSession().setAttribute("username", username);
        return super.onLoginSuccess(token, subject, request, response);
    }

}
