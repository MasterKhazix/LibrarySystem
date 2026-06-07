package com.qingshan.librarysystem.servlet;

import com.qingshan.librarysystem.dao.BookDAO;
import com.qingshan.librarysystem.model.Book;
import com.qingshan.librarysystem.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/book/*")
public class BookServlet extends HttpServlet {

    private final BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String pathInfo = request.getPathInfo();
        User user = (User) request.getSession().getAttribute("user");

        if (pathInfo == null || "/list".equals(pathInfo)) {
            handleList(request, response);
        } else if ("/add".equals(pathInfo)) {
            if (!isAdmin(user)) {
                response.sendRedirect(request.getContextPath() + "/book/list");
                return;
            }
            request.getRequestDispatcher("/WEB-INF/views/book_edit.jsp").forward(request, response);
        } else if ("/edit".equals(pathInfo)) {
            if (!isAdmin(user)) {
                response.sendRedirect(request.getContextPath() + "/book/list");
                return;
            }
            String idStr = request.getParameter("id");
            if (idStr != null) {
                Book book = bookDAO.findById(Integer.parseInt(idStr));
                request.setAttribute("book", book);
            }
            request.getRequestDispatcher("/WEB-INF/views/book_edit.jsp").forward(request, response);
        } else if ("/delete".equals(pathInfo)) {
            if (!isAdmin(user)) {
                response.sendRedirect(request.getContextPath() + "/book/list");
                return;
            }
            String idStr = request.getParameter("id");
            if (idStr != null) {
                bookDAO.delete(Integer.parseInt(idStr));
            }
            response.sendRedirect(request.getContextPath() + "/book/list");
        } else {
            response.sendRedirect(request.getContextPath() + "/book/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String pathInfo = request.getPathInfo();
        User user = (User) request.getSession().getAttribute("user");

        if (!isAdmin(user)) {
            response.sendRedirect(request.getContextPath() + "/book/list");
            return;
        }

        if ("/add".equals(pathInfo)) {
            Book book = buildBookFromRequest(request);
            bookDAO.insert(book);
            response.sendRedirect(request.getContextPath() + "/book/list");
        } else if ("/edit".equals(pathInfo)) {
            Book book = buildBookFromRequest(request);
            String idStr = request.getParameter("id");
            if (idStr != null) {
                book.setId(Integer.parseInt(idStr));
                bookDAO.update(book);
            }
            response.sendRedirect(request.getContextPath() + "/book/list");
        } else {
            response.sendRedirect(request.getContextPath() + "/book/list");
        }
    }

    private void handleList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Book> bookList;
        if (keyword != null && !keyword.trim().isEmpty()) {
            bookList = bookDAO.search(keyword.trim());
            request.setAttribute("keyword", keyword.trim());
        } else {
            bookList = bookDAO.findAll();
        }
        request.setAttribute("bookList", bookList);
        request.getRequestDispatcher("/WEB-INF/views/book_list.jsp").forward(request, response);
    }

    private Book buildBookFromRequest(HttpServletRequest request) {
        Book book = new Book();
        book.setTitle(request.getParameter("title"));
        book.setAuthor(request.getParameter("author"));
        book.setIsbn(request.getParameter("isbn"));
        book.setPublisher(request.getParameter("publisher"));
        book.setCategory(request.getParameter("category"));
        book.setDescription(request.getParameter("description"));

        String totalStr = request.getParameter("totalCopies");
        book.setTotalCopies(totalStr != null && !totalStr.isBlank() ? Integer.parseInt(totalStr) : 1);

        String availStr = request.getParameter("availableCopies");
        book.setAvailableCopies(availStr != null && !availStr.isBlank()
                ? Integer.parseInt(availStr)
                : book.getTotalCopies());

        book.setLocation(request.getParameter("location"));
        return book;
    }

    private boolean isAdmin(User user) {
        return user != null && "admin".equals(user.getRole());
    }
}
