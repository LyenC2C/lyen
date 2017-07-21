package thread;

import java.util.concurrent.*;

/**
 * Created by lyen on 16-9-5.
 */
public class Tread_pooDIY {
    /**
     *     corePoolSize - 池中所保存的线程数，包括空闲线程。
     *     maximumPoolSize - 池中允许的最大线程数。
     *     keepAliveTime - 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
     *     unit - keepAliveTime 参数的时间单位。
     *     workQueue - 执行前用于保持任务的队列。此队列仅保持由 execute 方法提交的 Runnable 任务
     *
     */
    public static void main(String[] args) {
        //创建等待队列
        BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(20);
        //创建一个单线程执行程序，它可安排在给定延迟后运行命令或者定期地执行。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 3, 2, TimeUnit.MILLISECONDS, bqueue);
        //创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
        Thread t1 = new MyThread();
        Thread t2 = new MyThread();
        Thread t3 = new MyThread();
        Thread t4 = new MyThread();
        Thread t5 = new MyThread();
        Thread t6 = new MyThread();
        Thread t7 = new MyThread();
        //将线程放入池中进行执行
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);
        pool.execute(t6);
        pool.execute(t7);
        //关闭线程池
        pool.shutdown();
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "正在执行。。。");
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}