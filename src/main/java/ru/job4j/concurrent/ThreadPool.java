package ru.job4j.concurrent;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public void work(Runnable job) {
        tasks.offer(job);
    }

    public void shutdown() throws InterruptedException {
        threads.add(new Thread(tasks.poll()));
    }
}
