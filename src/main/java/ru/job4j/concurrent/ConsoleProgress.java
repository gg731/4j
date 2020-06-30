package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
        progress.interrupt();
    }

    @Override
    public void run() {
        try {
            char[] s = {'\\', '|', '/'};
            int c = 0;
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\rLoading: " + s[c]);
                c++;
                if (c > 2) {
                    c = 0;
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.err.print("\rCurrent thread is interrupted.");
        }
    }
}
