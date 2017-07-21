package db;

/**
 * Created by Administrator on 2015/8/7.
 */

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private DataSource ds;
    private static ConnectionPool pool;
    private ConnectionPool(){
        ds = new ComboPooledDataSource();
        System.out.println();
    }
    public static final ConnectionPool getInstance(){
        if(pool==null){
            try{
                pool = new ConnectionPool();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pool;
    }
    public synchronized final Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
