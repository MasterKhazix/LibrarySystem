package com.qingshan.librarysystem.filter;

import com.qingshan.librarysystem.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * 登录拦截过滤器 — 未登录用户无法访问内部页面
 * 映射所有路径 "/*"
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI();

        // ===== 白名单：无需登录即可访问 =====
        // 1. 登录、注册相关
        if (path.contains("/login") || path.contains("/register")) {
            chain.doFilter(request, response);
            return;
        }
        // 2. 静态资源（CSS / JS / 图片等）
        if (path.contains("/css/") || path.contains("/js/")) {
            chain.doFilter(request, response);
            return;
        }
        // 3. 首页 index.jsp
        if (path.endsWith("/") || path.endsWith("/index.jsp")) {
            chain.doFilter(request, response);
            return;
        }

        // ===== 其余页面：必须登录 =====
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // ===== 管理员专有路径保护 =====
        User user = (User) session.getAttribute("user");
        if (path.contains("/user/list") || path.contains("/user/delete")) {
            if (!"admin".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
