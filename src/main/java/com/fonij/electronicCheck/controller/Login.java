package com.fonij.electronicCheck.controller;


import com.fonij.electronicCheck.model.Customer;
import java.util.Random;


public class Login {

    public static String login(String customerNumber) throws Exception {
        if (Customer.getCustomerByCustomerNumber(customerNumber) == null)
            throw new Exceptions("Not Registered Yet");
        else
            return "Login Successfully";
    }

    public static String register(String firstName, String lastName, String nationalCode) throws Exception {
        if (Customer.doesCustomerExist(firstName, lastName, nationalCode))
            throw new Exceptions("Have Already Registered");
        else {
            Customer customer = new Customer(firstName, lastName, nationalCode, String.valueOf(new Random().nextInt(900000) + 100000), false);
            return "<html><center>Registered Successfully</center>Your Customer Number is: " + customer.getCustomerNumber() + "</html>";
        }
    }

}

