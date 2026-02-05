package org.example;

import java.util.ArrayList;
import java.util.Random;

public class task3 {



    public static void main(String[] args) {

        final boolean[] ready = { false };
        final boolean[] consumed = { true };
        Random rnd = new Random();

        int[] numbers = new int[5];
        Object lock = numbers;

        ArrayList<Integer> rndNumbers = new ArrayList<>();


        Thread producer = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (!consumed[0]) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    for (int i = 0; i < numbers.length; i++) {
                        numbers[i] = rnd.nextInt(100);
                    }

                    ready[0] = true;
                    consumed[0] = false;
                    lock.notifyAll();
                }
            }
        });
        Thread consumer = new Thread(() -> {

            while (true){
                synchronized (lock){
                    while (!ready[0]){
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    for(int x: numbers){
                        rndNumbers.add(x);
                    }

                    ready[0] = false;
                    consumed[0] = true;
                    lock.notifyAll();
                    if(rndNumbers.size() >= 1005){
                        System.out.println("Размер all = " + rndNumbers.size());
                        producer.interrupt();
                        return;
                    }

                }

            }
        });

        producer.start();
        consumer.start();
    }
}
