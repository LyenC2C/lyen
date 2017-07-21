
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by lyen on 16-9-9.
 */
public class DataBase {

    private String dbName;
    private String userName = "jfwx";
    private String password = "Jfwx608aviup";
    private static String connectionString = "jdbc:mysql://rm-2ze2x1h29sd9was49o.mysql.rds.aliyuncs.com:3306/%s?useUnicode=true&amp;characterEncoding=UTF-8&amp;autoReconnect=true&amp;failOverReadOnly=false";

    private Connection conn;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DataBase(String dbName) {
        this.dbName = dbName;
    }

    public DataBase(String userName, String passwd, String dbName) throws ClassNotFoundException {
        this.userName = userName;
        this.password = passwd;
        this.dbName = dbName;
    }

    public Connection getConnection() throws SQLException {
        conn = DriverManager.getConnection(String.format(connectionString, dbName), userName, password);
        return conn;
    }

    public void insert(Iterator<Map<String, ?>> datas, String table) throws SQLException {
        getConnection();
        conn.setAutoCommit(true);
        PreparedStatement ps;
        while (datas.hasNext()) {
            Map<String, ?> data = datas.next();
            ps = conn.prepareStatement(insertSql(data.keySet(), table));
            int i = 1;
            for (String key : data.keySet()) {
                ps.setObject(i++, data.get(key));
            }
            ps.executeUpdate();
        }
        conn.commit();
    }

    public String insertSql(Set<String> keys, String table) {
        String[] fieldsConvert = new String[keys.size()];
        int j = 0;
        for (String key : keys) {
            fieldsConvert[j++] = "`" + key + "`";
        }
        String[] valueFields = new String[keys.size()];
        for (int i = 0; i < valueFields.length; i++) {
            valueFields[i] = "?";
        }
        return String.format("insert into %s (%s) values (%s)", table, StringUtils.join(fieldsConvert, ","), StringUtils.join(valueFields, ","));
    }

}
