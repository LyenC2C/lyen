package design_mode.Observer;



import javafx.event.Event;

import java.util.Vector;

/**
 * Created by lyen on 16-9-14.
 */
public class ConcretSubject implements ISubject {
    /**
     * 具体主体实现，维护观察者队列，提供了增加和删除观察者的方法，并通过其inform通知观察者
     *
     * Vector与ArrayList一样，也是通过数组实现的，不同的是它支持线程的同步
     */
    //观察者队列
    Vector<IObserver> observers = new Vector<IObserver>();

    public void attach(IObserver observer) {

        observers.add(observer);

    }

    public void detach(IObserver observer) {

        observers.remove(observer);

    }

    public void inform() {
        String event = "information";
        for(IObserver ob:observers){
            ob.update(event); //在这里通知观察者
        }


    }
}
