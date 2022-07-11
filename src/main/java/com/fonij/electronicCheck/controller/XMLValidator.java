package com.fonij.electronicCheck.controller;


import java.io.*;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

import com.fonij.electronicCheck.model.LoggerModel;
import org.xml.sax.SAXException;


public class XMLValidator {

    private File xml;
    private File xsd;
    private static LoggerModel XSDLogger = new LoggerModel(XMLValidator.class);


    public XMLValidator(File xml, File xsd) {
        this.xml = xml;
        this.xsd = xsd;
    }

    public boolean validateXMLSchema(){
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsd);
            schema.newValidator().validate(new StreamSource(xml));
        } catch (IOException | SAXException e){
            XSDLogger.logInfo("Invalid XML File: " + xml.getName());
            return false;
        }
        XSDLogger.logInfo("Validate XML File " + xml.getName() + " Successfully");
        return true;
    }

}
