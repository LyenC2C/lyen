package design_mode.agent.static_agent;

/**
 * Created by lyen on 16-9-13.
 * 静态代理
 * 将代理模式用于实现延迟加载，可以有效地提升系统的启动速度，对于改善用户体验又很大的帮助
 */
public class DBQueryproxy implements IDBQuery {
    private DBQuery real_dbQuery = null;

    public String query() {
        if(real_dbQuery == null){
            //真正需要的时候才会创建真实的对象，创建过程可能很慢
            real_dbQuery = new DBQuery();
        }
        //在多线程环境下，这里返回一个虚假类，类似于future模式
        return real_dbQuery.query();
    }
}
