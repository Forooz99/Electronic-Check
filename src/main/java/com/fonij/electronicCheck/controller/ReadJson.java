package com.fonij.electronicCheck.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fonij.electronicCheck.model.Cheque;
import org.apache.log4j.Logger;
import java.io.*;
import java.nio.file.*;
import java.util.Map;


public class ReadJson {

    private ObjectMapper objectMapper;
    private String fileName;
    private static ReadJson readChequesJson = new ReadJson("cheques.json");
    private static Logger readJsonLogger = Logger.getLogger(ReadJson.class);
    private static boolean isReadOnStart = false;


    ReadJson(String fileName) {
        this.objectMapper = new ObjectMapper();
        this.fileName = fileName;
    }

    public static void readJsonFiles() {
        if (!isReadOnStart) {
            isReadOnStart = true;
            try {
                Map<Integer, Cheque> allUsedCheck = readChequesJson.readAllChecksWithJackson();
                if (allUsedCheck != null) {
                    Cheque.setAllUsedCheques(allUsedCheck);
                    readJsonLogger.info("Read cheques.json Successfully");
                } else
                    readJsonLogger.info("Empty cheques.json File");
            } catch (IOException e) {
                readJsonLogger.info("Couldnt Read cheques.json File : " + e.getMessage());
            }
        }
    }

    Map<Integer, Cheque> readAllChecksWithJackson() throws IOException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String fileString = new String(Files.readAllBytes(Paths.get(fileName)));
        if (!fileString.isEmpty()) {
            return objectMapper.readValue(fileString, new TypeReference<Map<Integer, Cheque>>(){});
        }
        return null;
    }

}

