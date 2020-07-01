package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private List<QUser> qUsers = new LinkedList<>();

    public synchronized boolean add(QUser user) {
        if (getUserById(user.getId()) == null) {
            qUsers.add(user);
            return true;
        }
        return false;
    }

    public synchronized boolean delete(QUser user) {
        if (getUserById(user.getId()) != null) {
            qUsers.remove(user);
            return true;
        }
        return false;
    }

    public synchronized boolean update(QUser user) {
        for (QUser q : qUsers) {
            if (q.getId() == user.getId()) {
                qUsers.remove(q);
                qUsers.add(user);
                return true;
            }
        }
        return false;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (getUserById(fromId) != null && getUserById(toId) != null) {
            getUserById(fromId).transaction(-amount);
            getUserById(toId).transaction(amount);
            return true;
        }
        return false;
    }

    private QUser getUserById(int id) {
        for (QUser q : qUsers) {
            if (q.getId() == id) {
                return q;
            }
        }
        return null;
    }
}

class QUser {
    private final int id;
    private volatile int amount;

    public QUser(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public synchronized void transaction(int sum) {
        amount += sum;
    }

    @Override
    public String toString() {
        return "QUser : " + "id=" + id + ", amount=" + amount;
    }
}

