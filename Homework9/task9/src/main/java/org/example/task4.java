package org.example;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class task4 {
    static boolean flag = true;
    public static void main(String[] args) {


        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");

        Thread service = new Thread(() -> {

                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println(LocalTime.now().format(fmt));
                    try{
                        Thread.sleep(2000);
                    }catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                        break;
                    }
                }

        });

        service.start();
    }
}
