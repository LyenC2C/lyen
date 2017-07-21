package thread;

/**
 * Created by lyen on 16-9-4.
 */
public class Thread_r implements Runnable {
    /**
     * 实现Runnable接口创建线程
     */
    private String name;

    public Thread_r(String name) {
        this.name = name;
    }

    public void run() {

        for (int i = 0; i < 100; i++) {
            System.out.println(name + ":" + i);
        }

    }

    public static void main(String[] args) {
        new Thread(new Thread_r("thread1")).start();
        new Thread(new Thread_r("thread2")).start();
    }
}
