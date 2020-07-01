package ru.job4j.concurrent;

import java.io.*;

public class ParseFile {
    private File file;
    private String content;

    public ParseFile(File file) throws IOException {
        this.file = file;
        this.content = initContent();
    }

    public synchronized File getFile() {
        return file;
    }

    public String getContent() {
        return content;
    }

    private synchronized String initContent() throws IOException {
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

    public synchronized void saveContent() throws IOException {
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
