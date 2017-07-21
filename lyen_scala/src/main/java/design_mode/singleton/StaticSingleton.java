package design_mode.singleton;

/**
 * 单例模式使用内部类维护单例的实例，当StaticSingleton被加载时，起内部类并不会被初始化
 * 故可以确保StaticSingleton类被载入jvm时，不会初始化单例类，而当getInstance被调用时
 * 才会加载SingletonHolder，从而初始化instance，由于实例建立在类加载时完成，故天生
 * 对多线程友好，getInstance也不需要使用同步关键字。兼备(Singleton_1,Lazy_Singleton)优点
 * ps:序列化和反序列化可能会破坏单例（场景并不多）
 */
public class StaticSingleton {
    private StaticSingleton() {
        System.out.println("StaticSingleton is created");
    }

    private static class SingletonHolder {
        private static StaticSingleton instance = new StaticSingleton();
    }

    public static StaticSingleton getInstance() {
        return SingletonHolder.instance;
    }
}
