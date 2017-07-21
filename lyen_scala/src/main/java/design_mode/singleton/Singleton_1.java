package design_mode.singleton;

/**
 * 简单单例模式，十分可靠，唯一的不足就是无法对instance做延时加载
 */
public class Singleton_1 {

    private Singleton_1() {
        System.out.println("Singleton is created");
        //创建单例的过程会比较慢
    }

    private static Singleton_1 instance;

    public static Singleton_1 getInstance() {
        if (instance == null) {
            instance = new Singleton_1();
        }
        return instance;
    }

    //模拟单例类扮演其他角色
    public static void createString() {
        System.out.println("create string in singleton");
    }
}
