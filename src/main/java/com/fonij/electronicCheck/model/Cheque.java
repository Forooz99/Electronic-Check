package com.fonij.electronicCheck.model;

import com.fonij.electronicCheck.controller.WriteJson;
import com.fonij.electronicCheck.controller.WriteXML;

import javax.xml.stream.XMLStreamException;
import java.util.*;


public class Cheque {

    private int id;
    private int price;
    private Customer exporter;
    private Customer owner;
    private List<Customer> receivers;
    private ChequeStatus status;
    private static Map<Integer, Cheque> allUnusedCheques = new HashMap<>();
    private static Map<Integer, Cheque> allUsedCheques = new HashMap<>();
    private static Map<Integer, Cheque> allCheques = new HashMap<>();


    public Cheque() {

    }

    public Cheque(int id, boolean isUsed) {
        this.id = id;
        this.receivers = new ArrayList<>();
        this.status = ChequeStatus.NOTUSED;
        if (isUsed) allUsedCheques.put(id, this);
        else allUnusedCheques.put(id, this);
        allCheques.put(id, this);
    }

    public static Map<Integer, Cheque> getAllCheques() {
        return allCheques;
    }

    public static Map<Integer, Cheque> getAllUnusedCheques() {
        return allUnusedCheques;
    }

    public static Map<Integer, Cheque> getAllUsedCheques() {
        return allUsedCheques;
    }

    public static void setAllUsedCheques(Map<Integer, Cheque> allUsedCheck) {
        Cheque.allUsedCheques = allUsedCheck;
    }

    public static void updateFiles() {
        new WriteJson("cheques.json").writeAllCheques(allUsedCheques);
        try {
            new WriteXML("chequesTreasury.xml").writeAllCheques(allUnusedCheques);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Customer getExporter() {
        return exporter;
    }

    public void setExporter(Customer exporter) {
        this.exporter = exporter;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public List<Customer> getReceivers() {
        return receivers;
    }

    public void addReceivers(Customer receiver) {
        this.receivers.add(receiver);
    }

    public ChequeStatus getStatus() {
        return status;
    }

    public void setStatus(ChequeStatus status) {
        this.status = status;
        if (allUnusedCheques.get(id) != null) allUnusedCheques.remove(id);
        allUsedCheques.put(id, this);
    }

}

