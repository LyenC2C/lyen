package design_mode.Observer;

/**
 * Created by lyen on 16-9-14.
 * 被观察者
 */
public interface ISubject {
    void attach(IObserver observer);  //添加观察者
    void detach(IObserver observer);  //删除观察者
    void inform();  //通知所有观察者
}
