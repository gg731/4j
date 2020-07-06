package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount<T> {

    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer temp;
        do {
            temp = count.get();
            temp++;

        } while (!count.compareAndSet(count.get(), temp));

//        int temp = count.get() + 1;
//        count.updateAndGet(t -> t < temp ? temp : t);
    }

    public int get() {
        return count.get();
    }


    public static void main(String[] args) {
        CASCount casCount = new CASCount();
        casCount.increment();
        casCount.increment();
        System.out.println(casCount.get());
    }
}
