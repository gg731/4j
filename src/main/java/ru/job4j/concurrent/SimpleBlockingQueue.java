package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private int size;

    public SimpleBlockingQueue(Integer size) {
        this.size = size;
    }

    public synchronized void offer(T value) {
        try {
            if (queue.size() == size) {
                wait();
            }
            queue.add(value);
            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized T poll()  {
        if (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        T element = queue.poll();
        notify();

        return element;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

}
