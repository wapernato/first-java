package org.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class task1 {

    public static void showT1(Thread t1){
        System.out.println(t1.getState());
    }

    public static void main(String[] args) throws Exception {
        final Object lock = new Object();
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1500);
                synchronized (lock) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        showT1(t1);

        t2.start();
        Thread.sleep(50);
        t1.start();

        showT1(t1);

        scheduler.schedule(() -> showT1(t1), 1, TimeUnit.SECONDS);
        scheduler.schedule(() -> showT1(t1), 2, TimeUnit.SECONDS);

        scheduler.schedule(() -> showT1(t1), 3200, TimeUnit.MILLISECONDS);

        scheduler.schedule(() -> showT1(t2), 2500, TimeUnit.MILLISECONDS);

        scheduler.schedule(() -> {
            synchronized (lock) {
                lock.notifyAll();
            }
        }, 4, TimeUnit.SECONDS);

        scheduler.schedule(() -> showT1(t1), 5, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            showT1(t1);
            showT1(t2);
            scheduler.shutdown();
        }, 6, TimeUnit.SECONDS);

        scheduler.awaitTermination(7, TimeUnit.SECONDS);
    }
}
