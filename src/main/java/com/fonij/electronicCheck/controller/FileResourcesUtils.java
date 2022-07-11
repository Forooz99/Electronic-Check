package com.fonij.electronicCheck.controller;


import com.fonij.electronicCheck.model.LoggerModel;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;


public class FileResourcesUtils {

    private static LoggerModel fileResourceLogger = new LoggerModel(FileResourcesUtils.class);


    public static File getFileFromResource(String relativePath) {
        URL resource = FileResourcesUtils.class.getClassLoader().getResource(relativePath);
        if (resource == null)
            fileResourceLogger.logInfo("File Not Found " + relativePath);
        else {
            fileResourceLogger.logInfo("Get File From Resource Successfully");
            try {
                return new File(resource.toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static InputStream getFileFromResourceAsStream(String relativePath) {
        InputStream inputStream = FileResourcesUtils.class.getClassLoader().getResourceAsStream(relativePath);
        if (inputStream == null)
            fileResourceLogger.logInfo("File Not Found: " + relativePath);
        else
            return inputStream;
        return null;
    }

}

