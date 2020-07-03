package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int size = 10;

    public synchronized void offer(T value) throws InterruptedException {
        if (queue.size() == size) {
            wait();
        }
        queue.add(value);
        notify();
    }

    public synchronized T poll() throws InterruptedException {
        if (queue.isEmpty()) {
            wait();
        }
        T element = queue.poll();
        notify();

        return element;
    }
}
