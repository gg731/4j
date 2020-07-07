package ru.job4j.concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BaseCache {
    private final ConcurrentHashMap<Integer, Base> cache = new ConcurrentHashMap<>();

    public void add(Base base) {
        this.cache.put(base.getId(), base);
    }

    public void delete(Base base) {
        cache.remove(base);
    }

    public void update(Base base) {
        cache.computeIfPresent(
                base.getId(), (k, v) -> {
                    if (base.getVersion().compareAndSet(v.getVersion().intValue(),
                            v.getVersion().incrementAndGet())) {
                        return base;
                    } else {
                        throw new OptimisticException("Versions do not match!");
                    }
                });
    }
}

class Base {
    private final int id;
    private AtomicInteger version = new AtomicInteger(0);

    public Base(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public AtomicInteger getVersion() {
        return version;
    }
}

class OptimisticException extends RuntimeException {

    public OptimisticException(String message) {
        super(message);
    }
}
