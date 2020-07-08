package ru.job4j.concurrent;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    public void queueSimpleTest() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue();
        CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList();
        final ArrayList result = new ArrayList(Arrays.asList(0, 1, 2, 3, 4));
        final CountDownLatch cdl = new CountDownLatch(2);

        Thread threadP = new Thread(
                () -> {
                    IntStream.range(0, 5)
                            .forEach(queue::offer);
                    cdl.countDown();
                }
        );
        threadP.start();

        Thread threadC = new Thread(
                () -> {
                    while (cdl.getCount() == 2 || !queue.isEmpty()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                    cdl.countDown();
                }
        );

        threadC.start();
        cdl.await();

        assertEquals(buffer, result);
    }
}