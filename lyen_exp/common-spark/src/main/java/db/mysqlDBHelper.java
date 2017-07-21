package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Administrator on 2015/8/7.
 */

public class mysqlDBHelper {

    private ConnectionPool pool = ConnectionPool.getInstance();
    private Connection con = null;
    private Statement stm = null;

    public mysqlDBHelper(){
        this.getConnection();
        try {
            this.getStm();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean  getConnection(){
        this.con = pool.getConnection();
        if (this.con == null)
            return false;
        return true;
    }

    public boolean getStm() throws SQLException {
        if(this.con == null){
            if (!this.getConnection())
                return false;
        }
        this.stm = this.con.createStatement();
        return  true;
    }

    public void setAutoCommit(boolean value) throws SQLException {
        this.con.setAutoCommit(value);
    }

    public boolean getAutoCommit() throws SQLException {
        return this.con.getAutoCommit();
    }

    public boolean execSQL(String SQL) throws SQLException {
        if (this.stm == null)
            if (!this.getStm())
                return false;
        return this.stm.execute(SQL);
    }

    public ResultSet execQuerry(String SQL) throws SQLException {
        if (this.stm == null)
            this.getStm();
        ResultSet ret = this.stm.executeQuery(SQL);
        return ret;
    }

    public void commit() throws SQLException {
        this.con.commit();
    }

    public void close(){
        try {
            if (this.stm != null)
                this.stm.close();
            if (this.con != null)
                this.con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
