package com.fonij.electronicCheck.model;

import com.fonij.electronicCheck.controller.WriteJson;
import com.fonij.electronicCheck.controller.WriteXML;

import javax.xml.stream.XMLStreamException;
import java.util.*;


public class Customer {

    private String firstName;
    private String lastName;
    private String nationalCode;
    private String customerNumber;
    private boolean isActive;
    private static Map<String, Customer> allCustomers = new HashMap<>();
    private static Map<String, Customer> allActivatedCustomers = new HashMap<>();


    public Customer() {}

    public Customer(String firstName, String lastName, String nationalCode, String customerNumber, boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.customerNumber = customerNumber;
        this.isActive = isActive;
        if (isActive) allActivatedCustomers.put(customerNumber, this);
        allCustomers.put(customerNumber, this);
    }

    public static boolean doesCustomerExist(String firstName, String lastName, String nationalCode) {
        for (Map.Entry<String, Customer> eachCustomer : allCustomers.entrySet()) {
            Customer c = eachCustomer.getValue();
            if (c.getLastName().equals(firstName) && c.getLastName().equals(lastName) && c.getNationalCode().equals(nationalCode))
                return true;
        }
        return false;
    }

    public static Customer getCustomerByCustomerNumber(String customerNumber) {
        return allCustomers.get(customerNumber);
    }

    public static Customer getActivatedCustomerByNumber(String customerNumber) {
        return allActivatedCustomers.get(customerNumber);
    }

    public static void updateFiles() {
        new WriteJson("customers.json").writeAllCustomer(allActivatedCustomers);
        try {
            new WriteXML("customers.xml").writeAllCustomer(allCustomers);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
        if (isActive) allActivatedCustomers.put(customerNumber, this);
        else allActivatedCustomers.remove(customerNumber);
    }

}

