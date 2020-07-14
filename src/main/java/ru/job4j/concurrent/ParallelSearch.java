package ru.job4j.concurrent;

import javax.crypto.spec.PSource;

public class ParallelSearch {

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(5);
        final Thread consumer = new Thread(
                () -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            System.out.println(queue.poll());
                        }
                }
        );

        consumer.start();
        new Thread(
                () -> {
                    try {
                        for (int index = 0; index != 3; index++) {
                            queue.offer(index);
                            Thread.sleep(500);
                        }
                        consumer.interrupt();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }
}
