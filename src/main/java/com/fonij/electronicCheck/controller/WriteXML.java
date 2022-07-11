package com.fonij.electronicCheck.controller;


import com.fonij.electronicCheck.model.Cheque;
import com.fonij.electronicCheck.model.Customer;
import javax.xml.stream.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;


public class WriteXML {

    private XMLOutputFactory output;
    private String fileName;
    private ByteArrayOutputStream out;


    public WriteXML(String fileName) {
        this.output = XMLOutputFactory.newInstance();
        this.fileName = fileName;
        this.out = new ByteArrayOutputStream();
    }

    public void writeAllCustomer(Map<String, Customer> allCustomers) throws XMLStreamException {
        XMLStreamWriter writer = output.createXMLStreamWriter(out);
        writer.writeStartDocument("utf-8", "1.0");
        writer.writeStartElement("customers");

        for (Map.Entry<String, Customer> eachCustomer : allCustomers.entrySet()) {
            Customer customer = eachCustomer.getValue();
            writer.writeStartElement("customer");

            writer.writeStartElement("firstName");
            writer.writeCharacters(customer.getFirstName());
            writer.writeEndElement();

            writer.writeStartElement("lastName");
            writer.writeCharacters(customer.getLastName());
            writer.writeEndElement();

            writer.writeStartElement("nationalCode");
            writer.writeCharacters(customer.getNationalCode());
            writer.writeEndElement();

            writer.writeStartElement("customerNumber");
            writer.writeCharacters(String.valueOf(customer.getCustomerNumber()));
            writer.writeEndElement();

            writer.writeStartElement("isActive");
            writer.writeCharacters(String.valueOf(customer.getIsActive()));
            writer.writeEndElement();

            writer.writeEndElement();
        }

        writer.writeEndDocument();
        writer.flush();
        writer.close();

        setUpPrettyPrinting();
    }

    public void writeAllCheques(Map<Integer, Cheque> allChecks) throws XMLStreamException {
        XMLStreamWriter writer = output.createXMLStreamWriter(out);
        writer.writeStartDocument("utf-8", "1.0");
        writer.writeStartElement("checks");

        for (Map.Entry<Integer, Cheque> eachCheck : allChecks.entrySet()) {
            Cheque cheque = eachCheck.getValue();
            writer.writeStartElement("check");

            writer.writeStartElement("id");
            writer.writeCharacters(String.valueOf(cheque.getId()));
            writer.writeEndElement();

            writer.writeEndElement();
        }

        writer.writeEndDocument();
        writer.flush();
        writer.close();

        setUpPrettyPrinting();
    }

    private void setUpPrettyPrinting() {
        try {
            FileWriter fileWriter = new FileWriter(fileName, false);
            fileWriter.write(formatXML(new String(out.toByteArray(), StandardCharsets.UTF_8)));
            fileWriter.close();
        } catch (TransformerException | IOException e) {
            e.printStackTrace();
        }
    }

    private String formatXML(String xml) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        StreamSource source = new StreamSource(new StringReader(xml));
        StringWriter output = new StringWriter();
        transformer.transform(source, new StreamResult(output));
        return output.toString();
    }

}

