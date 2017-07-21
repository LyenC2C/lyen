package thread;


/**
 * Created by lyen on 16-9-4.
 */
public class Thread_syn implements Runnable {

    private Foo foo;
    private String name;

    public Thread_syn(String name) {
        this.name = name;
        this.foo = new Foo();
    }

    public void run() {

        for (int i = 0; i < 3; i++) {
            foo.setX(50);
            try {
                Thread.currentThread().sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ":当前foo的对象x的值=" + foo.getX() + ":times:" + i);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        /**
         * 调用join()方法：保证当前线程A执行，直到该线程A执行完成后所加入的线程B得以执行(使用同一对象的多线程)。
         */
        //同一个对象不同线程
        Thread_syn myRunnable = new Thread_syn("thread1");
        Thread t1 = new Thread(myRunnable, "lyen");
        Thread t2 = new Thread(myRunnable, "cc");
        t1.start();
        t1.join();
        t2.start();


    }
}

class Foo {
    private int x = 100;

    public synchronized int getX() {
        return x;
    }
    //setX
    public synchronized void setX(int y) {
        this.x = x - y;
    }
    /*
    //和setX效果一样
    public void setXX(int y) {
        synchronized (this) {
            this.x = x - y;
        }
    }
    */
}