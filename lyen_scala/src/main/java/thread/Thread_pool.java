package thread;

import org.apache.mesos.Executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by lyen on 16-9-5.
 */
public class Thread_pool extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "正在执行。。。。。");
    }
}

class Test_pool {
    public static void main(String[] args) {
        /*
        //创建一个可重用固定线程数的线程池
        ExecutorService pool = Executors.newFixedThreadPool(3);
        */
        /*
        //创建一个使用单个 worker 线程的 Executor，以无界队列方式来运行该线程。
        ExecutorService pool = Executors.newSingleThreadExecutor();
        */
        /*
        //创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。
        ExecutorService pool = Executors.newCachedThreadPool();

        Thread t1 = new Thread_pool();
        Thread t2 = new Thread_pool();
        Thread t3 = new Thread_pool();
        Thread t4 = new Thread_pool();
        Thread t5 = new Thread_pool();
        //将线程放入池中进行执行
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);
        //关闭线程池
        pool.shutdown();
        */
        //创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行。
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
        /*
        //创建一个单线程执行程序，它可安排在给定延迟后运行命令或者定期地执行。
        ScheduledExecutorService pool = Executors.newSingleThreadScheduledExecutor();
        */
        Thread t1 = new Thread_pool();
        Thread t2 = new Thread_pool();
        Thread t3 = new Thread_pool();
        Thread t4 = new Thread_pool();
        Thread t5 = new Thread_pool();
        //将线程放入池中进行执行
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        //使用延迟执行风格的方法
        pool.schedule(t4, 10, TimeUnit.MILLISECONDS);
        pool.schedule(t5, 10, TimeUnit.MILLISECONDS);
        //关闭线程池
        pool.shutdown();
    }
}
