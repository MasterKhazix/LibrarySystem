package com.qingshan.librarysystem.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 借阅记录实体类（对应 borrow_records 表）
 */
public class BorrowRecord {
    private int id;
    private int userId;
    private int bookId;
    private Timestamp borrowDate;
    private Date dueDate;             // 应还日期
    private Timestamp returnDate;     // 实际归还日期（null 表示未还）
    private String status;            // borrowed / returned / overdue
    private Timestamp createTime;

    // ===== 展示用扩展字段（不存数据库，JSP 页面显示用） =====
    private String userName;          // 借阅人姓名
    private String bookTitle;         // 图书名称

    public BorrowRecord() {}

    public BorrowRecord(int id, int userId, int bookId, Timestamp borrowDate,
                        Date dueDate, Timestamp returnDate, String status,
                        Timestamp createTime) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
        this.createTime = createTime;
    }

    // ==================== Getter / Setter ====================
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public Timestamp getBorrowDate() { return borrowDate; }
    public void setBorrowDate(Timestamp borrowDate) { this.borrowDate = borrowDate; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public Timestamp getReturnDate() { return returnDate; }
    public void setReturnDate(Timestamp returnDate) { this.returnDate = returnDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreateTime() { return createTime; }
    public void setCreateTime(Timestamp createTime) { this.createTime = createTime; }

    // ===== 展示用字段的 getter/setter =====
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    /**
     * 是否已归还
     */
    public boolean isReturned() {
        return "returned".equals(status);
    }

    @Override
    public String toString() {
        return "BorrowRecord{id=" + id + ", userId=" + userId + ", bookId=" + bookId
                + ", status='" + status + "'}";
    }
}
