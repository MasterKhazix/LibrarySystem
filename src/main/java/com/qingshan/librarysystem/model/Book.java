package com.qingshan.librarysystem.model;

import java.sql.Timestamp;

/**
 * 图书实体类（对应 books 表）
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private String category;
    private String description;
    private int totalCopies;
    private int availableCopies;
    private String location;
    private Timestamp createTime;

    public Book() {}

    public Book(int id, String title, String author, String isbn, String publisher,
                String category, String description, int totalCopies,
                int availableCopies, String location, Timestamp createTime) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.category = category;
        this.description = description;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.location = location;
        this.createTime = createTime;
    }

    // ==================== Getter / Setter ====================
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }

    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Timestamp getCreateTime() { return createTime; }
    public void setCreateTime(Timestamp createTime) { this.createTime = createTime; }

    /**
     * 是否可借（可借数量 > 0）
     */
    public boolean isAvailable() {
        return availableCopies > 0;
    }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "', author='" + author
                + "', available=" + availableCopies + "/" + totalCopies + "}";
    }
}
