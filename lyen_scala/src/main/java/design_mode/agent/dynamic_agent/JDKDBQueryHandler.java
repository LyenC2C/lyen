package design_mode.agent.dynamic_agent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by lyen on 16-9-13.
 * 动态代理有待学习，暂时只是了解.....
 */
public class JDKDBQueryHandler implements InvocationHandler {
    private DBQuery real_dbquery = null;

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (real_dbquery == null) {
            real_dbquery = new DBQuery();
        }
        return real_dbquery.query();
    }
    //实现IDBQuery接口的代理类，代理类的内部逻辑由JDKDBQueryHandler决定。生成代理后newProxyInstance（）方法返回该代理类的一个实例
    public static IDBQuery createJDKProxy() {
        IDBQuery jdkProxy = (IDBQuery) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{IDBQuery.class}, new JDKDBQueryHandler()
        );
        return jdkProxy;
    }
}
