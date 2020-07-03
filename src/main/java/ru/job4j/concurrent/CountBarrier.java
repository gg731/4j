package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CountBarrier {
    @GuardedBy("this")
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            this.count++;
        }
    }

    public void await() throws InterruptedException {
        synchronized (monitor) {
            if (count != total) {
                wait();
            } else {
                notify();
            }
        }
    }
}