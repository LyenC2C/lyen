package design_mode.Observer;

/**
 * Created by lyen on 16-9-14.
 */
public class ConcretObserver implements IObserver {

    public void update(String event) {
        System.out.println("observers receives information");

    }
}
