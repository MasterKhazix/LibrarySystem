-- =============================================
-- 图书管理系统 - 数据库初始化脚本
-- 请在 Navicat 或 MySQL 命令行中执行此文件
-- =============================================

-- 第1步：创建数据库
DROP DATABASE IF EXISTS library_db;
CREATE DATABASE library_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE library_db;

-- 第2步：创建用户表
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50) NOT NULL,
    phone VARCHAR(11) DEFAULT NULL,
    email VARCHAR(100) DEFAULT NULL,
    role ENUM('admin', 'user') NOT NULL DEFAULT 'user',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_users_phone CHECK (phone IS NULL OR phone REGEXP '^[0-9]{11}$'),
    CONSTRAINT chk_users_email CHECK (email IS NULL OR email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 第3步：创建图书表
CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(100) NOT NULL,
    isbn VARCHAR(30) UNIQUE,
    publisher VARCHAR(100) DEFAULT NULL,
    category VARCHAR(50) DEFAULT NULL,
    description TEXT DEFAULT NULL,
    total_copies INT NOT NULL DEFAULT 1,
    available_copies INT NOT NULL DEFAULT 1,
    location VARCHAR(50) DEFAULT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 第4步：创建借阅记录表
CREATE TABLE borrow_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    borrow_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date DATE NOT NULL,
    return_date TIMESTAMP NULL,
    status ENUM('borrowed', 'returned', 'overdue') DEFAULT 'borrowed',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 第5步：插入默认管理员账号（用户名: admin，密码: admin123）
INSERT INTO users (username, password, real_name, role)
VALUES ('admin', MD5('admin123'), '系统管理员', 'admin');

-- 第6步：插入测试用普通用户（用户名: test，密码: test123）
INSERT INTO users (username, password, real_name, role)
VALUES ('test', MD5('test123'), '测试用户', 'user');

-- 第7步：插入10本测试图书
INSERT INTO books (title, author, isbn, publisher, category, description, total_copies, available_copies, location) VALUES
('Java编程思想', 'Bruce Eckel', '978-7-111-21382-6', '机械工业出版社', '计算机', 'Java语言经典入门教材，深入讲解面向对象编程思想。', 5, 5, 'A-01-01'),
('深入理解Java虚拟机', '周志明', '978-7-111-61842-6', '机械工业出版社', '计算机', 'JVM调优与内存管理的权威指南。', 3, 3, 'A-01-02'),
('算法导论', 'Thomas H. Cormen', '978-7-111-40701-0', '机械工业出版社', '计算机', '计算机科学经典教材，涵盖各种算法与数据结构。', 3, 2, 'A-02-01'),
('数据结构与算法分析', 'Mark Allen Weiss', '978-7-111-53860-8', '机械工业出版社', '计算机', '数据结构与算法的Java语言描述。', 2, 2, 'A-02-02'),
('计算机网络', '谢希仁', '978-7-121-44849-5', '电子工业出版社', '计算机', '计算机网络经典教程，详解TCP/IP协议栈。', 4, 4, 'A-03-01'),
('数据库系统概念', 'Abraham Silberschatz', '978-7-111-61843-3', '机械工业出版社', '计算机', '数据库系统理论基础与实践。', 3, 3, 'A-03-02'),
('百年孤独', '加西亚·马尔克斯', '978-7-5442-5399-4', '南海出版公司', '文学', '魔幻现实主义经典，拉丁美洲文学代表作。', 2, 1, 'B-01-01'),
('活着', '余华', '978-7-5063-6543-7', '作家出版社', '文学', '中国当代文学经典，讲述福贵坎坷的一生。', 4, 4, 'B-01-02'),
('三体', '刘慈欣', '978-7-5366-9293-0', '重庆出版社', '科幻', '雨果奖获奖作品，硬科幻的巅峰之作。', 5, 5, 'B-02-01'),
('银河帝国：基地', '艾萨克·阿西莫夫', '978-7-5399-6605-4', '江苏文艺出版社', '科幻', '科幻小说奠基之作，心理历史学的宏大叙事。', 3, 3, 'B-02-02');

-- =============================================
-- 验证：查询所有数据
-- =============================================
SELECT '=== 用户表 ===' AS '';
SELECT id, username, real_name, role FROM users;
SELECT '=== 图书表 ===' AS '';
SELECT id, title, author, category, total_copies, available_copies FROM books;
SELECT '=== 借阅记录表 ===' AS '';
SELECT COUNT(*) AS borrow_count FROM borrow_records;

SELECT '初始化完成！' AS status;
