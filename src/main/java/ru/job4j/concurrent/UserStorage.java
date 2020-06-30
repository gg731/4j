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
        if (!qUsers.contains(user)) {
            qUsers.add(user);
            return true;
        }
        return false;
    }

    public synchronized boolean delete(QUser user) {
        if (!qUsers.contains(user)) {
            qUsers.remove(user);
            return true;
        }
        return false;
    }

    public synchronized boolean update(QUser user) {
        if (qUsers.contains(user)) {
            qUsers.stream().forEach(q -> {
                if (q.getId() == user.getId()) {
                    qUsers.remove(user.getId());
                    qUsers.add(user);
                }
            });
            return true;
        }
        return false;
    }

//    public synchronized boolean transfer(int fromId, int toId, int amount) {
//        getUserById(fromId).setAmount();
//    }

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
    private volatile BigDecimal amount;

    public QUser(int id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public void transaction(BigDecimal sum) {
    }
}

