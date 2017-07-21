package design_mode.agent.dynamic_agent;

import design_mode.agent.static_agent.IDBQuery;

/**
 * Created by lyen on 16-9-13.
 */
public class DBQuery implements IDBQuery {

    public DBQuery() {
        try {
            Thread.sleep(1000);//可能包含数据库等耗时操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String query() {
        return "get message here";
    }

}
