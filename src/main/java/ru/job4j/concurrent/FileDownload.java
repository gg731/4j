package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FileDownload implements Runnable {
    private File file;
    private String url;
    private String fileName;
    private int speed;

    public FileDownload(String url, String fileName, int speed) {
        this.url = url;
        this.fileName = fileName;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[speed];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, speed)) != -1) {
                long t = System.currentTimeMillis();
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long s = System.currentTimeMillis() - t;
                if (s < 1000) {
                    Thread.sleep(1000 - s);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        this.file = new File(fileName);
    }

    public File getFile() {
        return file;
    }

    public static void main(String[] args) throws Exception {
        String url = args[0];
        int speed = Integer.valueOf(args[1]);
//        String url = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
//        int speed = 200;
        String fileName = "new_file.xml";

        Thread thread = new Thread(new FileDownload(url, fileName, speed));
        thread.start();
    }
}
