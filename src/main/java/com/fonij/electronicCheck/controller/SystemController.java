package com.fonij.electronicCheck.controller;


import com.fonij.electronicCheck.model.Cheque;
import com.fonij.electronicCheck.model.ChequeStatus;
import com.fonij.electronicCheck.model.Customer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SystemController {
    //complete
    public static String activateCustomer(String customerNumber) throws Exception {
        if (Customer.getCustomerByCustomerNumber(customerNumber) == null)
            throw new Exceptions("Invalid Customer Number");
        else if (Customer.getActivatedCustomerByNumber(customerNumber) != null)
            throw new Exception("Customer Already Activated");
        else {
            Customer.getCustomerByCustomerNumber(customerNumber).setIsActive(true);
            Customer.updateFiles();
            return "Activated Successfully";
        }
    }
    //complete
    public static String inActivateCustomer(String customerNumber) throws Exception {
        if (Customer.getCustomerByCustomerNumber(customerNumber) == null)
            throw new Exceptions("Invalid Customer Number");
        else if (Customer.getActivatedCustomerByNumber(customerNumber) == null)
            throw new Exceptions("Customer Already Inactivated");
        else {
            Customer.getCustomerByCustomerNumber(customerNumber).setIsActive(false);
            Customer.updateFiles();
            return "Inactivated Successfully";
        }
    }
    //complete
    public static String issueTheCheck(String fromCustomerNumber, int checkNumber, int price, String toCustomerNumber) throws Exception {
        if (Cheque.getAllCheques().get(checkNumber) == null)
            throw new Exceptions("Invalid Cheque Id");
        else if (price <= 0)
            throw new Exceptions("Invalid Amount");
        else if (Customer.getCustomerByCustomerNumber(toCustomerNumber) == null)
            throw new Exceptions("Invalid Cheques Recipient");
        else if (Customer.getActivatedCustomerByNumber(toCustomerNumber) == null)
            throw new Exceptions("Cheques Recipient Inactivated");
        else if (Customer.getCustomerByCustomerNumber(fromCustomerNumber) == null)
            throw new Exceptions("Invalid Cheques Issuer");
        else if (Customer.getActivatedCustomerByNumber(fromCustomerNumber) == null)
            throw new Exceptions("Cheque Issuer Not Activated");
        else if (Cheque.getAllUnusedCheques().get(checkNumber) == null)
            throw new Exceptions("Duplicated Cheque");
        else {
            Cheque check = Cheque.getAllCheques().get(checkNumber);
            check.setStatus(ChequeStatus.ISSUED);
            check.setPrice(price);
            check.setExporter(Customer.getCustomerByCustomerNumber(fromCustomerNumber));
            check.setOwner(Customer.getCustomerByCustomerNumber(toCustomerNumber));
            new WriteJson("cheques.json").writeAllCheques(Cheque.getAllUsedCheques());
            new WriteXML("chequesTreasury.xml").writeAllCheques(Cheque.getAllUnusedCheques());
            return "Check Pulled Successfully";
        }
    }
    //complete
    public static String transferCheck(String ownerNumber, int checkNumber, String receiverNumber) throws Exception {
        if (Cheque.getAllUsedCheques().get(checkNumber) == null)
            throw new Exceptions("Invalid Cheque Id");
        else if (Customer.getCustomerByCustomerNumber(receiverNumber) == null)
            throw new Exceptions("Invalid Cheques Recipient");
        else if (Customer.getActivatedCustomerByNumber(receiverNumber) == null)
            throw new Exceptions("Cheques Recipient Inactivated");
        else if (Customer.getCustomerByCustomerNumber(ownerNumber) == null)
            throw new Exceptions("Invalid Cheques Holder");
        else if (Customer.getActivatedCustomerByNumber(ownerNumber) == null)
            throw new Exceptions("Cheque Holder Not Activated");
        else if (!Cheque.getAllUsedCheques().get(checkNumber).getStatus().equals(ChequeStatus.ISSUED)
                && !Cheque.getAllUsedCheques().get(checkNumber).getStatus().equals(ChequeStatus.TRANSFERRED))
            throw new Exceptions("Invalid Cheque Status");
        else {
            Cheque check = Cheque.getAllUsedCheques().get(checkNumber);
            check.setStatus(ChequeStatus.TRANSFERRED);
            check.setOwner(Customer.getCustomerByCustomerNumber(receiverNumber));
            check.addReceivers(Customer.getCustomerByCustomerNumber(ownerNumber));
            new WriteJson("cheques.json").writeAllCheques(Cheque.getAllUsedCheques());
            return "Check Transferred Successfully";
        }
    }
    //complete
    public static Cheque inquiryCheck(int checkNumber) throws Exception {
        if (!Cheque.getAllUsedCheques().containsKey(checkNumber))
            throw new Exceptions("Invalid Cheque Id");
        else {
            ReadJson readCheckJson = new ReadJson("cheques.json");
            return readCheckJson.readAllChecksWithJackson().get(checkNumber);
        }
    }
    //complete
    public static String acceptRejectCheck(String ownerNumber, int checkNumber, boolean isAccepted) throws Exception {
        if (!Cheque.getAllCheques().containsKey(checkNumber))
            throw new Exceptions("Invalid Cheque Id");
        else if (!Cheque.getAllUsedCheques().get(checkNumber).getOwner().getCustomerNumber().equals(ownerNumber))
            throw new Exceptions("Invalid Cheques Holder");
        else if (Customer.getCustomerByCustomerNumber(ownerNumber) == null)
            throw new Exceptions("Cheque Holder Not Activated");
        else if (!Cheque.getAllCheques().get(checkNumber).getStatus().equals(ChequeStatus.TRANSFERRED)
                && !Cheque.getAllCheques().get(checkNumber).getStatus().equals(ChequeStatus.ACCEPTED))
            throw new Exceptions("Invalid Cheque Status");
        else {
            if (isAccepted) {
                Cheque.getAllCheques().get(checkNumber).setStatus(ChequeStatus.ACCEPTED);
                return "Check Accepted Successfully";
            }
            else {
                Cheque.getAllCheques().get(checkNumber).setStatus(ChequeStatus.REJECT);
                return "Check Rejected Successfully";
            }
        }
    }
    //complete
    public static ArrayList<String> getChecksList(String ownerNumber) throws Exception {
        if (Customer.getCustomerByCustomerNumber(ownerNumber) == null)
            throw new Exceptions("Invalid Cheques Holder");
        else if (Customer.getActivatedCustomerByNumber(ownerNumber) == null)
            throw new Exceptions("Cheque Holder Not Activated");
        else {
            ReadJson readCheckJson = new ReadJson("cheques.json");
            Map<Integer, Cheque> allChecks =  new HashMap<>(readCheckJson.readAllChecksWithJackson());
            ArrayList<String> checkInfo = new ArrayList<>();
            for (Map.Entry<Integer, Cheque> x : allChecks.entrySet()) {
                Cheque check = x.getValue();
                if (check.getOwner().getCustomerNumber().equals(ownerNumber) || check.getReceivers().contains(Customer.getCustomerByCustomerNumber(ownerNumber))
                        || check.getExporter().getCustomerNumber().equals(ownerNumber)) {
                    checkInfo.add("Check Number: " + check.getId() + "<br>Status: " + check.getStatus().name() + "<br>Price: " + check.getPrice() +
                            "<br>Exporter: " + check.getExporter().getCustomerNumber() + "<br>Owner: " + check.getOwner().getCustomerNumber() + "<br>Receivers: " + check.getReceivers().toString());
                }
            }
            return checkInfo;
        }
    }
    //complete
    public static String cacheTheCheck(String ownerNumber, int checkNumber) throws Exception {
        if (!Cheque.getAllCheques().containsKey(checkNumber))
            throw new Exceptions("Invalid Cheque Id");
        else if (!Cheque.getAllUsedCheques().get(checkNumber).getOwner().getCustomerNumber().equals(ownerNumber))
            throw new Exceptions("Invalid Cheques Holder");
        else if (Customer.getActivatedCustomerByNumber(ownerNumber) == null)
            throw new Exceptions("Cheque Holder Not Activated");
        else if (!Cheque.getAllCheques().get(checkNumber).getStatus().equals(ChequeStatus.ACCEPTED))
            throw new Exceptions("Invalid Cheque Status");
        else {
            Cheque.getAllUsedCheques().get(checkNumber).setStatus(ChequeStatus.CASHED);
            return "Check Cached Successfully";
        }
    }

}

