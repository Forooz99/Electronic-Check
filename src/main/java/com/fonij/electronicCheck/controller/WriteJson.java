package com.fonij.electronicCheck.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fonij.electronicCheck.model.Cheque;
import com.fonij.electronicCheck.model.Customer;
import org.apache.log4j.Logger;
import java.io.*;
import java.nio.file.*;
import java.util.*;


public class WriteJson {

    private ObjectMapper objectMapper;
    private String fileName;
    private static Logger writeJsonLogger = Logger.getLogger(WriteJson.class);


    public WriteJson(String fileName) {
        this.objectMapper = new ObjectMapper();
        this.fileName = fileName;
    }

    public void writeAllCustomer(Map<String, Customer> allCustomers) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            writeIntoFile(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(allCustomers));
            writeJsonLogger.info("Write customer.json Successfully");
        } catch (JsonProcessingException e) {
            writeJsonLogger.info("Couldnt Write customer.json : " + e.getMessage());
        }
    }

    public void writeAllCheques(Map<Integer, Cheque> allChecks) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            writeIntoFile(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(allChecks));
            writeJsonLogger.info("Write cheques.json Successfully");
        } catch (JsonProcessingException e) {
            writeJsonLogger.info("Couldnt Write cheques.json : " + e.getMessage());
        }
    }

    private void writeIntoFile(String string) {
        try {
            Files.write(Paths.get(fileName), string.getBytes(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            writeJsonLogger.info("Couldnt Write to " + fileName + " : " + e.getMessage());
        }
    }

}

