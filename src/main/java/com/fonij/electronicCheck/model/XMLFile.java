package com.fonij.electronicCheck.model;


import com.fonij.electronicCheck.controller.FileResourcesUtils;

import java.io.File;


public class XMLFile {

    private File file;
    private static XMLFile customerXML;
    private static XMLFile checkXML;


    private XMLFile(String fileName) {
        this.file = FileResourcesUtils.getFileFromResource(fileName);
    }

    public static XMLFile getCustomerXML(String fileName) {
        if (customerXML == null)
            customerXML = new XMLFile(fileName);
        return customerXML;
    }

    public static XMLFile getCheckXML(String fileName) {
        if (checkXML == null)
            checkXML = new XMLFile(fileName);
        return checkXML;
    }

    public static void setCustomerXML(File file) {
        customerXML.file = file;
    }

    public static void setCheckXML(File file) {
        checkXML.file = file;
    }

    public File getFile() {
        return file;
    }

}

