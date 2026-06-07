package com.qingshan.librarysystem.servlet;

import com.qingshan.librarysystem.dao.UserDAO;
import com.qingshan.librarysystem.model.User;
import com.qingshan.librarysystem.util.InputValidator;
import com.qingshan.librarysystem.util.MD5Util;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String realName = request.getParameter("realName");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String phone = InputValidator.trimToNull(request.getParameter("phone"));
        String email = InputValidator.trimToNull(request.getParameter("email"));

        if (username == null || username.trim().isEmpty()) {
            forwardRegisterError(request, response, "用户名不能为空");
            return;
        }
        if (password == null || password.trim().length() < 6) {
            forwardRegisterError(request, response, "密码至少6位");
            return;
        }
        if (!password.equals(confirmPassword)) {
            forwardRegisterError(request, response, "两次密码不一致");
            return;
        }
        if (realName == null || realName.trim().isEmpty()) {
            forwardRegisterError(request, response, "真实姓名不能为空");
            return;
        }
        if (!InputValidator.isValidPhone(phone)) {
            forwardRegisterError(request, response, "手机号必须是11位数字");
            return;
        }
        if (!InputValidator.isValidEmail(email)) {
            forwardRegisterError(request, response, "邮箱格式不正确，例如 user@example.com");
            return;
        }
        if (userDAO.findByUsername(username.trim()) != null) {
            forwardRegisterError(request, response, "用户名已被注册");
            return;
        }

        User user = new User();
        user.setUsername(username.trim());
        user.setPassword(MD5Util.md5(password.trim()));
        user.setRealName(realName.trim());
        user.setPhone(phone);
        user.setEmail(email);
        user.setRole("user");

        if (userDAO.insert(user)) {
            request.setAttribute("success", "注册成功，请登录");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            forwardRegisterError(request, response, "注册失败，请重试");
        }
    }

    private void forwardRegisterError(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("error", message);
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
}
