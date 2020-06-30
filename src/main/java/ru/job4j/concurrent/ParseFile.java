package ru.job4j.concurrent;

import java.io.*;

public class ParseFile {
    private File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() throws IOException {
        InputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) != -1) {
            output += (char) data;
        }
        return output;
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        InputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) != -1) {
            if (data < 0x80) {
                output += (char) data;
            }
        }
        return output;
    }

    public synchronized void saveContent(String content) throws IOException {
        OutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i++) {
            o.write(content.charAt(i));
        }
    }

    public static void main(String[] args) throws IOException {

        File file = new File("pom.xml");
        ParseFile ps = new ParseFile(file);
        System.out.println(ps.getContentWithoutUnicode());
    }
}
