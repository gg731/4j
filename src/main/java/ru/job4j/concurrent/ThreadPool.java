package ru.job4j.concurrent;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() throws InterruptedException {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(tasks.poll()));
        }

        threads.stream().forEach(Thread::start);
    }

    public synchronized void work(Runnable job) {
        tasks.offer(job);
    }

    public synchronized void shutdown() {
        threads.stream().forEach(Thread::interrupt);
    }
}
