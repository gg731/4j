package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FileDownload {
    public static void main(String[] args) throws Exception {
        String file = args[0];
        int speedS = Integer.parseInt(args[1]);

        try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("new_4j.zip")) {
            byte[] dataBuffer = new byte[speedS];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, speedS)) != -1) {
                long t = System.currentTimeMillis();
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long s = System.currentTimeMillis() - t;
                if (s < 1000) {
                    Thread.sleep(1000 - s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
