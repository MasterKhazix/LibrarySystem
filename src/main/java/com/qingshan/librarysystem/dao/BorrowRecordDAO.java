package com.qingshan.librarysystem.dao;

import com.qingshan.librarysystem.model.BorrowRecord;
import com.qingshan.librarysystem.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 借阅记录数据访问层 — JDBC PreparedStatement 操作 borrow_records 表
 * 涉及多表关联查询（JOIN users、JOIN books 获取展示用名称）
 */
public class BorrowRecordDAO {

    /**
     * 创建借阅记录（借书）
     */
    public boolean insert(BorrowRecord record) {
        String sql = "INSERT INTO borrow_records (user_id, book_id, due_date) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, record.getUserId());
            ps.setInt(2, record.getBookId());
            ps.setDate(3, record.getDueDate());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps);
        }
        return false;
    }

    /**
     * 归还图书：更新 return_date 和 status
     */
    public boolean returnBook(int recordId) {
        String sql = "UPDATE borrow_records SET return_date = NOW(), status = 'returned' WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, recordId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps);
        }
        return false;
    }

    /**
     * 根据 ID 查询单条记录
     */
    public BorrowRecord findById(int id) {
        String sql = "SELECT r.*, u.username AS user_name, u.real_name, b.title AS book_title "
                + "FROM borrow_records r "
                + "JOIN users u ON r.user_id = u.id "
                + "JOIN books b ON r.book_id = b.id "
                + "WHERE r.id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return null;
    }

    /**
     * 查询某用户的全部借阅记录
     */
    public List<BorrowRecord> findByUserId(int userId) {
        List<BorrowRecord> list = new ArrayList<>();
        String sql = "SELECT r.*, u.username AS user_name, u.real_name, b.title AS book_title "
                + "FROM borrow_records r "
                + "JOIN users u ON r.user_id = u.id "
                + "JOIN books b ON r.book_id = b.id "
                + "WHERE r.user_id = ? ORDER BY r.borrow_date DESC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    /**
     * 查询全部借阅记录（管理员视角）
     */
    public List<BorrowRecord> findAll() {
        List<BorrowRecord> list = new ArrayList<>();
        String sql = "SELECT r.*, u.username AS user_name, u.real_name, b.title AS book_title "
                + "FROM borrow_records r "
                + "JOIN users u ON r.user_id = u.id "
                + "JOIN books b ON r.book_id = b.id "
                + "ORDER BY r.borrow_date DESC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    /**
     * 统计当前借出数量
     */
    public int countBorrowed() {
        String sql = "SELECT COUNT(*) FROM borrow_records WHERE status = 'borrowed'";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return 0;
    }

    /**
     * ResultSet → BorrowRecord 对象（含关联字段）
     */
    private BorrowRecord mapRow(ResultSet rs) throws SQLException {
        BorrowRecord record = new BorrowRecord();
        record.setId(rs.getInt("id"));
        record.setUserId(rs.getInt("user_id"));
        record.setBookId(rs.getInt("book_id"));
        record.setBorrowDate(rs.getTimestamp("borrow_date"));
        record.setDueDate(rs.getDate("due_date"));
        record.setReturnDate(rs.getTimestamp("return_date"));
        record.setStatus(rs.getString("status"));
        record.setCreateTime(rs.getTimestamp("create_time"));
        // 展示用字段（从 JOIN 中获取）
        String realName = rs.getString("real_name");
        String username = rs.getString("user_name");
        record.setUserName(realName != null ? realName + "(" + username + ")" : username);
        record.setBookTitle(rs.getString("book_title"));
        return record;
    }
}
