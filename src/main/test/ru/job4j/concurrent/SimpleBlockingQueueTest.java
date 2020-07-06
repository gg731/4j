package ru.job4j.concurrent;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    public void queueSimpleTest() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue();
        CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList();
        final ArrayList result = new ArrayList(Arrays.asList(0, 1, 2, 3, 4));

        Thread threadP = new Thread(
                () -> {
                    IntStream.range(0, 5)
                            .forEach(queue::offer);
                }
        );

        threadP.start();

        Thread threadC = new Thread(
                () -> {
                    while (!queue.isEmpty()
                            || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );

        threadC.start();
        threadP.join();
        threadC.interrupt();
        threadC.join();

        assertEquals(buffer, result);
    }
}