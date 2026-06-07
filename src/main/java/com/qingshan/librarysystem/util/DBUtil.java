package com.qingshan.librarysystem.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * JDBC 数据库连接工具类
 * 从 classpath 下的 db.properties 读取连接信息
 */
public class DBUtil {

    private static String url;
    private static String username;
    private static String password;

    static {
        try (InputStream in = DBUtil.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            Properties prop = new Properties();
            prop.load(in);
            url = prop.getProperty("db.url");
            username = prop.getProperty("db.username");
            password = prop.getProperty("db.password");
            // 加载驱动
            Class.forName(prop.getProperty("db.driver"));
        } catch (Exception e) {
            throw new RuntimeException("加载数据库配置失败，请检查 db.properties", e);
        }
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * 关闭资源（查询用：有 ResultSet）
     */
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
        try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
    }

    /**
     * 关闭资源（增删改用：无 ResultSet）
     */
    public static void close(Connection conn, PreparedStatement ps) {
        close(conn, ps, null);
    }
}
