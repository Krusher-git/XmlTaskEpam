package com.kozich.xmltask.parser;


import java.io.File;
import java.net.URL;

public class FilePathParser {
    public static String filePath(String path) {
        ClassLoader loader = FilePathParser.class.getClassLoader();
        URL location = loader.getResource(path);
        String filePath = new File(location.getFile()).getAbsolutePath();
        return filePath;
    }
}
