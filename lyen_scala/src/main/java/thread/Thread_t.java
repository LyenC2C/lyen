package thread;

/**
 * Created by lyen on 16-9-4.
 */
public class Thread_t extends Thread {
    /**
     * 继承Thread类创建线程
     */
    private String name;

    public Thread_t(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(name + ":" + i);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Thread_t("thread1"));
        Thread t2 = new Thread(new Thread_t("thread2"));
        // t1.setPriority(1);
        // t2.setPriority(5);
        t1.start();
        t1.join();//t1调用Thread的join方法，主函数将线程分配给t1，当t1运行完毕后，才会将线程释放出去。给其他的对象。
        //由于t1条用了join方法，t1结束后释放，t2和主函数竞争，如果主函数（main方法的操作是主线程）在t1，t2之前无论怎样都执行主线程
        t2.start();
        for (int i = 0; i < 100; i++) {
            System.out.println("I am the first one");
        }

    }
}
