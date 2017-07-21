package thread;

import java.util.Queue;
import java.util.Random;

/**
 * Created by lyen on 16-9-4.
 */
public class Producer extends Thread{
    private Queue queue;
    private int maxSize;
    public Producer(Queue queue, int maxSize, String name){
        super(name); this.queue = queue; this.maxSize = maxSize;
    }
    @Override public void run()
    {
        while (true)
        {
            synchronized (queue) {
                while (queue.size() == maxSize) {
                    try {
                        System.out .println("Queue is full, " + "Producer thread waiting for " + "consumer to take something from queue");
                        queue.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace(); }
                }
                Random random = new Random();
                int i = random.nextInt(10);
                System.out.println(Thread.currentThread().getName()+" Producing value : " + i);
                queue.add(i);
                queue.notifyAll();
            }
        }
    }
}
