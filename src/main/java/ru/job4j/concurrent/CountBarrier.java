package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CountBarrier {
    @GuardedBy("this")
    private final Object monitor = this;

    private final int total;

    private volatile int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        count++;
        if (count == total) {
            notifyAll();
            count = 0;
        }
    }

    public void await() throws InterruptedException {
        if (count != total) {
            wait();
        }
    }
}