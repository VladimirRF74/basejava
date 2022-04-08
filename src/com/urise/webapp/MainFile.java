package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = "./.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        File dir = new File("src");
        printDir(dir, "");
        /*System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }*/

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void printDir(File dir, String indent) {
        File[] files = dir.listFiles();
        assert files != null;
        for (File f : files) {
            if (f.isFile()) {
                System.out.println(indent + "\t" + f.getName());
            } else {
                System.out.println(indent + f.getName());
                printDir(f, "\t");
            }
        }
    }
}