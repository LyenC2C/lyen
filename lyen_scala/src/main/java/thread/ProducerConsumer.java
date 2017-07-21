package thread;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by lyen on 16-9-4.
 */
public class ProducerConsumer {
    public static void main(String args[]) {
        System.out.println("How to use wait and notify method in Java");
        System.out.println("Solving Producer Consumper Problem");
        Queue buffer;
        buffer = new LinkedList();
        int maxSize = 10;
        Thread producer = new Producer(buffer, maxSize, "PRODUCER");
        Thread consumer = new Consumer(buffer, maxSize, "CONSUMER");
        Thread consumer2 = new Consumer(buffer, maxSize, "CONSUMER");
        producer.setName("Lyen");
        producer.start();
        consumer.setName("cc_1");
        consumer.start();
        consumer2.setName("cc_2");
        consumer2.start();
    }
}
