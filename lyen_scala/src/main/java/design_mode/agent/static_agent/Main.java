package design_mode.agent.static_agent;

import design_mode.agent.static_agent.DBQueryproxy;
import design_mode.agent.static_agent.IDBQuery;

/**
 * Created by lyen on 16-9-13.
 */
public class Main {

    public static void main(String[] args) {
        IDBQuery query = new DBQueryproxy();
        String result = query.query();
        System.out.println(result);
    }
}
