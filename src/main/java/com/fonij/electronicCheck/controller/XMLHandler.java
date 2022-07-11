package com.fonij.electronicCheck.controller;

import com.fonij.electronicCheck.model.LoggerModel;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;

public class XMLHandler {

    private boolean isCustomerFile;
    private XMLValidator validator;
    private ReadXML xmlReader;
    private static LoggerModel handlerLogger = new LoggerModel(XMLHandler.class);


    public XMLHandler(File xmlFile, File xsdFile, boolean isCustomerFile) {
        this.isCustomerFile = isCustomerFile;
        validator = new XMLValidator(xmlFile, xsdFile);
        try {
            xmlReader = new ReadXML(xmlFile);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            handlerLogger.logInfo(xmlFile + " Not Found");
        }
    }

    public boolean validateXML() {
        return validator.validateXMLSchema();
    }

    public void readXML() {
        if (isCustomerFile)
            xmlReader.readCustomerXML();
        else
            xmlReader.readCheckXML();
    }


}

