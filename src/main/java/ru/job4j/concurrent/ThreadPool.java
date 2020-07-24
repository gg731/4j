package ru.job4j.concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class ThreadPool {
    private final int size = Runtime.getRuntime().availableProcessors();
    private final Pool[] threads = new Pool[size];
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);
    private volatile boolean isStopped = false;

    public ThreadPool() {
        IntStream.range(0, size).forEach(i -> threads[i] = new Pool(tasks));

        for (Pool pool : threads) {
            pool.start();
        }
    }

    public void work(Runnable job) {
        if (!isStopped) {
            tasks.offer(job);
        }
    }

    public void shutdown() {
        isStopped = true;
        for (Pool pool : threads) {
            pool.doStop();
        }
    }
}

class Pool extends Thread {
    private boolean isStopped = false;
    private SimpleBlockingQueue queue;

    public Pool(SimpleBlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!isStopped) {
            try {
                Runnable runnable = (Runnable) queue.poll();
                runnable.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void doStop() {
        this.isStopped = true;
        this.interrupt();
    }

}