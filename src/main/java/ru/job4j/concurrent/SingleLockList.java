package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private ArrayList<T> arrayList;

    public SingleLockList(ArrayList<T> arrayList) {
        this.arrayList = arrayList;
    }

    public synchronized void add(T value) {
        arrayList.add(value);
    }

    public T get(int index) {
        return arrayList.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.arrayList).iterator();
    }

    public ArrayList<T> copy(ArrayList<T> orig) {
        return (ArrayList<T>) orig.clone();
    }
}
