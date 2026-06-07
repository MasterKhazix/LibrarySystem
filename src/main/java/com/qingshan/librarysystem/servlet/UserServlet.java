package com.qingshan.librarysystem.servlet;

import com.qingshan.librarysystem.dao.BookDAO;
import com.qingshan.librarysystem.dao.BorrowRecordDAO;
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
import java.util.List;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();
    private final BookDAO bookDAO = new BookDAO();
    private final BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String pathInfo = request.getPathInfo();
        User user = (User) request.getSession().getAttribute("user");

        if (pathInfo == null) {
            response.sendRedirect(request.getContextPath() + "/book/list");
            return;
        }

        switch (pathInfo) {
            case "/list":
                if (!isAdmin(user)) {
                    response.sendRedirect(request.getContextPath() + "/book/list");
                    return;
                }
                List<User> userList = userDAO.findAll();
                request.setAttribute("userList", userList);
                request.setAttribute("totalBooks", bookDAO.count());
                request.setAttribute("totalUsers", userDAO.count());
                request.setAttribute("borrowedCount", borrowRecordDAO.countBorrowed());
                request.getRequestDispatcher("/WEB-INF/views/user_list.jsp").forward(request, response);
                break;

            case "/delete":
                if (!isAdmin(user)) {
                    response.sendRedirect(request.getContextPath() + "/book/list");
                    return;
                }
                String idStr = request.getParameter("id");
                if (idStr != null) {
                    int deleteId = Integer.parseInt(idStr);
                    if (deleteId != user.getId()) {
                        userDAO.delete(deleteId);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/user/list");
                break;

            case "/profile":
                request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/book/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String pathInfo = request.getPathInfo();

        if ("/editProfile".equals(pathInfo)) {
            handleEditProfile(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/book/list");
        }
    }

    private void handleEditProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("user");
        String realName = request.getParameter("realName");
        String phone = InputValidator.trimToNull(request.getParameter("phone"));
        String email = InputValidator.trimToNull(request.getParameter("email"));
        String newPassword = request.getParameter("newPassword");

        if (realName == null || realName.trim().isEmpty()) {
            forwardProfileError(request, response, "真实姓名不能为空");
            return;
        }
        if (!InputValidator.isValidPhone(phone)) {
            forwardProfileError(request, response, "手机号必须是11位数字");
            return;
        }
        if (!InputValidator.isValidEmail(email)) {
            forwardProfileError(request, response, "邮箱格式不正确，例如 user@example.com");
            return;
        }

        User updated = new User();
        updated.setId(sessionUser.getId());
        updated.setRealName(realName.trim());
        updated.setPhone(phone);
        updated.setEmail(email);

        if (newPassword != null && !newPassword.trim().isEmpty()) {
            if (newPassword.trim().length() < 6) {
                forwardProfileError(request, response, "新密码至少6位");
                return;
            }
            updated.setPassword(MD5Util.md5(newPassword.trim()));
        }

        if (userDAO.update(updated)) {
            sessionUser.setRealName(updated.getRealName());
            sessionUser.setPhone(updated.getPhone());
            sessionUser.setEmail(updated.getEmail());
            request.setAttribute("success", "个人信息更新成功");
        } else {
            request.setAttribute("error", "更新失败，请重试");
        }

        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }

    private void forwardProfileError(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("error", message);
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }

    private boolean isAdmin(User user) {
        return user != null && "admin".equals(user.getRole());
    }
}
