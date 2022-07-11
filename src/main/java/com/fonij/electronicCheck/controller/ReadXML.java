package com.fonij.electronicCheck.controller;


import com.fonij.electronicCheck.model.Cheque;
import com.fonij.electronicCheck.model.Customer;
import org.apache.log4j.Logger;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.*;


class ReadXML {

    private XMLEventReader reader;
    private XMLEvent event;
    private static Logger readXMLLogger = Logger.getLogger(ReadXML.class);


    ReadXML(File file) throws XMLStreamException, FileNotFoundException {
        this.reader = XMLInputFactory.newInstance().createXMLEventReader(new FileInputStream(file.getName()));
    }

    void readCustomerXML() {
        boolean res = false;
        try {
            res = readAllCustomers();
        } catch (XMLStreamException e) {
            readXMLLogger.info("Empty customers.xml File : " + e.getMessage());
        }
        if (!res) {
            try {
                throw new Exceptions("Couldnt Read customers.xml File and Load Data");
            } catch (Exceptions exceptions) {
                exceptions.printStackTrace();
            }
        }
    }

    void readCheckXML() {
        boolean res = false;
        try {
            res = readAllChecks();
        } catch (XMLStreamException e) {
            readXMLLogger.info("Empty chequesTreasury.xml File : " + e.getMessage());
        }
        if (!res) {
            try {
                throw new Exceptions("Couldnt Read chequesTreasury.xml File and Load Data");
            } catch (Exceptions exceptions) {
                exceptions.printStackTrace();
            }
        }
    }

    private boolean readAllCustomers() throws XMLStreamException {
        String firstName  = "", lastName = " ", nationalCode = " ", customerNumber = "";
        boolean isActive = false;

        while (reader.hasNext()) {
            event  = reader.nextEvent();
            if (event.isStartElement()) {
                switch (event.asStartElement().getName().getLocalPart()) {
                    case "firstName":
                        event = reader.nextEvent();
                        firstName = event.asCharacters().getData();
                        break;
                    case "lastName":
                        event = reader.nextEvent();
                        lastName = event.asCharacters().getData();
                        break;
                    case "nationalCode":
                        event = reader.nextEvent();
                        nationalCode = event.asCharacters().getData();
                        break;
                    case "customerNumber":
                        event = reader.nextEvent();
                        customerNumber = event.asCharacters().getData();
                        break;
                    case "isActive":
                        event = reader.nextEvent();
                        isActive = Boolean.parseBoolean(event.asCharacters().getData());
                        break;
                }
            }
            if (event.isEndElement())
                if (event.asEndElement().getName().getLocalPart().equals("customer"))
                    new Customer(firstName, lastName, nationalCode, customerNumber, isActive);
        }
        readXMLLogger.info("Read customers.xml Successfully");
        return true;
    }

    private boolean readAllChecks() throws XMLStreamException {
        int id = 0;

        while (reader.hasNext()) {
            event  = reader.nextEvent();
            if (event.isStartElement()) {
                switch (event.asStartElement().getName().getLocalPart()) {
                    case "id":
                        event = reader.nextEvent();
                        id = Integer.parseInt(event.asCharacters().getData());
                        break;
                }
            }
            if (event.isEndElement())
                if (event.asEndElement().getName().getLocalPart().equals("check"))
                    new Cheque(id, false);
        }
        readXMLLogger.info("Read chequesTreasury.xml Successfully");
        return true;
    }

}

