package db;

import java.sql.*;

/**
 * Created by lyen on 16-11-6.
 */
public final class JDBCUtilSingle {

    private static String dbname = "test";
    private static String name = "root";
    private static String password = "root";
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/%s";//缺省->"jdbc:mysql:///test"
    private static Connection conn = null;
    private static JDBCUtilSingle jdbcUtilSingle = null;

    public static JDBCUtilSingle initJDBCUtilSingle() {
        if (jdbcUtilSingle == null) {
            //给类加锁，防止线程并发
            synchronized (JDBCUtilSingle.class) {
                if (jdbcUtilSingle == null) {
                    jdbcUtilSingle = new JDBCUtilSingle();
                }
            }
        }
        return jdbcUtilSingle;
    }

    private JDBCUtilSingle() {
    }

    /**
     * 通过静态代码块注册数据库驱动，保证注册只执行一次
     */
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得连接
     */
    public Connection getConnection() {
        try {
            conn = DriverManager.getConnection(String.format(url, dbname), name, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;

    }

    /**
     * 关闭连接
     *
     * @param rs
     * @param statement
     * @param con
     */
    public void closeConnection(ResultSet rs, Statement statement, Connection con) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
