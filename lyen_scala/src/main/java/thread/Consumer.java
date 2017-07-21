package thread;

import java.util.Queue;

/**
 * Created by lyen on 16-9-4.
 */
public class Consumer extends Thread{
    private Queue queue;
    private int maxSize;
    public Consumer(Queue queue, int maxSize, String name){
        super(name);
        this.queue = queue;
        this.maxSize = maxSize;
    }
    @Override public void run() {
        while (true) {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    System.out.println("Queue is empty," + "Consumer thread is waiting" + " for producer thread to put something in queue");
                    try {
                        queue.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName()+" Consuming value : " + queue.remove());
                queue.notifyAll();
            }
        }
    }
}
