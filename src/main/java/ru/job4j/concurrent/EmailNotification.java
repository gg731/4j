package ru.job4j.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public void emailTo(UserEN user) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                String subject =
                        "Notification " + user.getUsername() + " to email " + user.getEmail();
                String body = "Add a new event to " + user.getUsername();

                send(subject, body, user.getEmail());
            }
        });
    }

    public void close() {
        pool.shutdown();
    }

    public void send(String subject, String body, String email) {

    }
}

class UserEN {
    private String username;
    private String email;

    public UserEN(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
