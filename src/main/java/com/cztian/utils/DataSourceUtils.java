package com.cztian.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据源工具类
 */
public class DataSourceUtils {

    private static final String driverClass;
    private static final String url;
    private static final String username;
    private static final String password;

    static {
        //读取属性文件
        Properties properties = new Properties();
        InputStream in = DataSourceUtils.class.getClassLoader().getResourceAsStream("generator.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driverClass = properties.getProperty("driverClass", "");
        url = properties.getProperty("url", "");
        username = properties.getProperty("username", "");
        password = properties.getProperty("password", "");

        //注册驱动类
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接对象
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
