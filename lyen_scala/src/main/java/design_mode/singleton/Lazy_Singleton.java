package design_mode.singleton;

/**
 * Created by lyen on 16-9-13.
 */
public class Lazy_Singleton {
    private Lazy_Singleton() {
        System.out.println("Lazy_Singleton is creating");
    }

    //单例模式加入延时加载机制
    //对静态成员赋值null确保系统启动是没有额外的负载
    //加入了synchronized但是在多线程编程中耗时要高一些
    private static Lazy_Singleton instance = null;

    //Double Check
    public static Lazy_Singleton getInstance() {
        if (instance == null) {
            synchronized(Lazy_Singleton.class) {
                if (instance == null) {
                    instance = new Lazy_Singleton();
                }
            }
        }
        return instance;
    }
}
