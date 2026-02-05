package org.example;


public class task2 {
    static final Object lock = new Object();
    static boolean t1Turn = true;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> run("t1", true), "t1");
        Thread t2 = new Thread(() -> run("t2", false), "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    static void run(String name, boolean isT1) {
        for (int i = 0; i < 10; i++) {
            synchronized (lock) {
                while (t1Turn != isT1) {
                    try {
                        lock.wait();
                    }
                    catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                System.out.println(Thread.currentThread().getName());
                t1Turn = !t1Turn;
                lock.notifyAll();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
