package db;

import utils.StringUtils;

import javax.swing.text.html.HTMLDocument;
import java.sql.*;
import java.util.*;

/**
 * Created by lyen on 16-11-6.
 */
public class DataBase {
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String connUrl = "jdbc:mysql://localhost:3306/%s?useUnicode=true&amp;charactorEncoding=UTF-8&amp;autoReconnect=ture&amp;failOverReadOnly=false";
    private String dbname = "lyen";
    private String userName = "root";
    private String userPwd = "root";

    private Connection conn = null;

    public DataBase(String dbname, String userName, String userPwd) throws ClassNotFoundException {
        Class.forName(driver);
        this.dbname = dbname;
        this.userName = userName;
        this.userPwd = userPwd;
    }

    public DataBase(String dbname) throws ClassNotFoundException {
        Class.forName(driver);
        this.dbname = dbname;
    }

    /**
     * 获取数据库连接
     */
    public Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(String.format(connUrl, this.dbname));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }


    /**
     * 获取参数
     *
     * @param statement
     * @param values
     * @throws Exception
     */
    private void attachParams(PreparedStatement statement, Object... values) throws Exception {
        if (values != null && values.length != 0) {
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
        }

    }

    /**
     * 查询数据返回ResultSet对象
     * @param sql
     * @param parameterValues
     * @return
     * @throws Exception
     */
    public ResultSet executeResultSet(String sql,
                                      Object... parameterValues) throws Exception {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            attachParams(statement, parameterValues);
            rs = statement.executeQuery();
            statement.clearParameters();
        } catch (Exception e) {
            close();
            throw e;
        }
        return rs;
    }

    /**
     * 将查询结果转换为List<Map<String,Object>>
     *
     * @param res
     * @return List<Map<String,Object>>
     * @throws SQLException
     */
    private List<Map<String, Object>> convertRes(ResultSet res) throws SQLException {

        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        if (res != null && !res.isClosed()) {
            ResultSetMetaData meta = res.getMetaData();
            int colCount = meta.getColumnCount();
            while (res.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 0; i <= colCount; i++) {
                    String key = meta.getCatalogName(i);
                    Object value = res.getObject(i);
                    map.put(key, value);
                }
                data.add(map);
            }
        }
        return data;
    }

    /**
     * 查询    (vip)
     * @param sql
     * @param parameterValues
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> query(String sql,
                                                 Object... parameterValues) throws Exception {
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            rs = executeResultSet(sql, parameterValues);
            list = convertRes(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);
        }
        return list;
    }

    /**
     * 插入数据sql
     * @param table
     * @param fields
     * @return
     */
    private String insertSql(String table, Set<String> fields) {

        String[] fieldConverts = new String[fields.size()];
        int j = 0;
        for (String field : fields) {
            fieldConverts[j++] = "`" + field + "`";
        }

        String[] values = new String[fields.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = "?";
        }
        return String.format("insert into %s (%s) values (%s)", table, StringUtils.join(fieldConverts, ","), StringUtils.join(values, ","));
    }

    public void importData(String table, String sql, Iterator<Map<String,?>> datas) throws SQLException {
        getConnection();
        conn.setAutoCommit(false);
        while (datas.hasNext()){
            Map<String,?> data = datas.next();
            PreparedStatement ps = conn.prepareStatement(insertSql(table,data.keySet()));
            int i = 1;
            for (String key : data.keySet()) {
                ps.setObject(i++, data.get(key));
            }
            ps.executeUpdate();
        }
        conn.commit();
    }


    /**
     * 关闭数据库连接
     */
    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
