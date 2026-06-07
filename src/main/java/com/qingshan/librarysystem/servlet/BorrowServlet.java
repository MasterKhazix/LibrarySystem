package com.qingshan.librarysystem.servlet;

import com.qingshan.librarysystem.dao.BookDAO;
import com.qingshan.librarysystem.dao.BorrowRecordDAO;
import com.qingshan.librarysystem.model.BorrowRecord;
import com.qingshan.librarysystem.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@WebServlet("/borrow/*")
public class BorrowServlet extends HttpServlet {

    private final BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();
    private final BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String pathInfo = request.getPathInfo();
        User user = (User) request.getSession().getAttribute("user");

        if ("/do".equals(pathInfo)) {
            handleBorrow(request, response, user);
        } else if ("/return".equals(pathInfo)) {
            handleReturn(request, response, user);
        } else {
            handleList(request, response, user);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void handleBorrow(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException {
        String bookIdStr = request.getParameter("bookId");
        if (bookIdStr == null || bookIdStr.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/book/list");
            return;
        }

        int bookId = Integer.parseInt(bookIdStr);

        if (!bookDAO.updateAvailableCopies(bookId, -1)) {
            request.getSession().setAttribute("error", "该图书暂无可借副本");
            response.sendRedirect(request.getContextPath() + "/book/list");
            return;
        }

        BorrowRecord record = new BorrowRecord();
        record.setUserId(user.getId());
        record.setBookId(bookId);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 30);
        record.setDueDate(new Date(cal.getTimeInMillis()));

        borrowRecordDAO.insert(record);
        response.sendRedirect(request.getContextPath() + "/borrow/list");
    }

    private void handleReturn(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException {
        String recordIdStr = request.getParameter("recordId");
        if (recordIdStr == null || recordIdStr.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/borrow/list");
            return;
        }

        int recordId = Integer.parseInt(recordIdStr);
        BorrowRecord record = borrowRecordDAO.findById(recordId);

        if (record != null && !record.isReturned() && canReturn(record, user)) {
            borrowRecordDAO.returnBook(recordId);
            bookDAO.updateAvailableCopies(record.getBookId(), 1);
        }

        response.sendRedirect(request.getContextPath() + "/borrow/list");
    }

    private void handleList(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        List<BorrowRecord> recordList;
        if ("admin".equals(user.getRole())) {
            recordList = borrowRecordDAO.findAll();
        } else {
            recordList = borrowRecordDAO.findByUserId(user.getId());
        }
        request.setAttribute("recordList", recordList);
        request.getRequestDispatcher("/WEB-INF/views/borrow_list.jsp").forward(request, response);
    }

    private boolean canReturn(BorrowRecord record, User user) {
        return user != null && ("admin".equals(user.getRole()) || record.getUserId() == user.getId());
    }
}
