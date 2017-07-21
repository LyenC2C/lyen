package design_mode.Observer;



/**
 * Created by lyen on 16-9-14.
 * 观察者
 *
 */
public interface IObserver {
    void update(String event); // 更新观察者
    /**
     * 在jdk中已经实现了一套观察者模式，可以复用相关代码
     * 应用：Swing JButton
     */
}
