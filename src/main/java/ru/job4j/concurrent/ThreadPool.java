package ru.job4j.concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class ThreadPool {
    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<Pool> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);
    private boolean isStopped = false;

    public ThreadPool() throws InterruptedException {
        IntStream.range(0, size).forEach(i -> threads.add(new Pool(tasks)));

        threads.stream().forEach(Thread::start);
    }

    public void work(Runnable job) {
        if (!isStopped) {
            tasks.offer(job);
        }
    }

    public void shutdown() {
        isStopped = true;
        threads.stream().forEach(t -> t.doStop());
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
            Runnable runnable = (Runnable) queue.poll();
            runnable.run();
        }
    }

    public void doStop() {
        this.isStopped = true;
        this.interrupt();
    }

}