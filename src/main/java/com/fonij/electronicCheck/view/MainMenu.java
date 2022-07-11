package com.fonij.electronicCheck.view;


import com.fonij.electronicCheck.controller.Exceptions;
import com.fonij.electronicCheck.controller.SystemController;
import com.fonij.electronicCheck.controller.ValidatingTxt;
import com.fonij.electronicCheck.model.*;
import com.fonij.electronicCheck.model.ButtonModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


class MainMenu {

    private JFrame mainFrame;
    private PanelModel mainPanel;
    private ButtonModel activateCustomerBtn;
    private ButtonModel inactivateCustomerBtn;
    private ButtonModel issueCheckBtn;
    private ButtonModel transferCheckBtn;
    private ButtonModel inquiryCheckBtn;
    private ButtonModel acceptCheckBtn;
    private ButtonModel rejectCheckBtn;
    private ButtonModel getChecksListBtn;
    private ButtonModel cacheTheCheckBtn;
    private ButtonModel logoutBtn;
    private ButtonModel okBtn;
    private TextBoxModel checkNumberTxt;
    private TextBoxModel priceTxt;
    private TextBoxModel receiverNumberTxt;
    private LabelModel checkNumberLbl;
    private LabelModel priceLbl;
    private LabelModel receiverNumberLbl;
    private LabelModel messageLbl;
    private CustomerAction customerAction;
    private LoggerModel mainMenuPageLogger;
    private String onlineUserCustomerNumber;
    private Customer onlineCustomer;
    private String LOG_MESSAGE_CONSTANT;


    MainMenu(String onlineUserCustomerNumber) {
        this.onlineUserCustomerNumber = onlineUserCustomerNumber;
        onlineCustomer = Customer.getCustomerByCustomerNumber(onlineUserCustomerNumber);
        LOG_MESSAGE_CONSTANT = "Customer " + onlineUserCustomerNumber + " : ";
        mainFrame = FrameModel.getFrame();
        mainPanel = new PanelModel(900, 700, 0, 0, "#14204a", null);

        initializingButtons();
        initializingTextFields();
        initializingLabels();

        mainMenuPageLogger = new LoggerModel(MainMenu.class);
        mainMenuPageLogger.logInfo("Enter in Main Page");

        setBtnActions();
    }

    private void initializingButtons() {
        ButtonModel.setDefaultPanel(mainPanel);
        activateCustomerBtn = new ButtonModel("Activate", 150, 30, 100, 150, 20, "#fed038", true);
        inactivateCustomerBtn = new ButtonModel("Inactivate", 150, 30, 100, 190, 20, "#fed038", true);
        issueCheckBtn = new ButtonModel("Issue", 150, 30, 100, 230, 20, "#fed038", true);
        transferCheckBtn = new ButtonModel("Transfer", 150, 30, 100, 270, 20, "#fed038", true);
        inquiryCheckBtn = new ButtonModel("Inquiry", 150, 30, 100, 310, 20, "#fed038", true);
        acceptCheckBtn = new ButtonModel("Accept", 150, 30, 100, 350, 20, "#fed038", true);
        rejectCheckBtn = new ButtonModel("Reject", 150, 30, 100, 390, 20, "#fed038", true);
        getChecksListBtn = new ButtonModel("Check List", 150, 30, 100, 430, 20, "#fed038", true);
        cacheTheCheckBtn = new ButtonModel("Cache", 150, 30, 100, 470, 20, "#fed038", true);
        logoutBtn = new ButtonModel("Log Out", 150, 30, 100, 510, 20, "#fed038", true);
        okBtn = new ButtonModel("Ok", 100, 30, 600, 400, 20, "#fed038", false);
    }

    private void initializingTextFields() {
        TextBoxModel.setDefaultPanel(mainPanel);
        checkNumberTxt = new TextBoxModel(150, 30, 575, 240, 20, "#fed038", false);
        priceTxt = new TextBoxModel(150, 30, 575, 320, 20, "#fed038", false);
        receiverNumberTxt = new TextBoxModel(150, 30, 575, 280, 20, "#fed038", false);
    }

    private void initializingLabels() {
        LabelModel.setDefaultPanel(mainPanel);
        checkNumberLbl = new LabelModel("Check Number", 150, 30, 420, 240,20, "#fed038", false, false);
        priceLbl = new LabelModel("Price", 150, 30, 420, 320,20, "#fed038", false, false);
        receiverNumberLbl = new LabelModel("Receiver Number", 150, 30, 420, 280,20, "#fed038", false, false);
        messageLbl = new LabelModel("", 600, 700, 300, 0,20, "#fed038", true, true);
        new LabelModel(onlineCustomer.getFirstName() + " " + onlineCustomer.getLastName() + " Welcome!", 300, 40, 40, 50,30, "#fed038", true, true);
    }

    private void designPageBasedOnAction(CustomerAction action) {
        messageLbl.setText("");
        switch (action) {
            case ACTIVATE:
            case INACTIVATE:
            case CHECKLIST:
                setVisibleForItems(false, false, false, false, false, false, false);
                break;
            case ISSUE:
                setVisibleForItems(true, true, true, true, true, true, true);
                break;
            case TRANSFER:
                setVisibleForItems(true, false, true, true, true, false, true);
                break;
            case INQUIRY:
            case ACCEPT:
            case REJECT:
            case CACHE:
                setVisibleForItems(true, false, false, true, true, false, false);
                break;
        }
    }

    private void setActivateBtnAction() {
        setGeneralBtnAction(CustomerAction.ACTIVATE);
        try {
            messageLbl.setText(SystemController.activateCustomer(onlineUserCustomerNumber));
            mainMenuPageLogger.logInfo("/Activation/ " +  LOG_MESSAGE_CONSTANT + messageLbl.getText());
        } catch (Exception exception) {
            messageLbl.setText(exception.getMessage());
            mainMenuPageLogger.logInfo("/Activation/ " + LOG_MESSAGE_CONSTANT + exception.getMessage());
        }
    }

    private void setInactivateBtnAction() {
        setGeneralBtnAction(CustomerAction.INACTIVATE);
        try {
            messageLbl.setText(SystemController.inActivateCustomer(onlineUserCustomerNumber));
            mainMenuPageLogger.logInfo("/Inactivation/ " + LOG_MESSAGE_CONSTANT + messageLbl.getText());
        } catch (Exception exception) {
            messageLbl.setText(exception.getMessage());
            mainMenuPageLogger.logInfo("/Inactivation/ " + LOG_MESSAGE_CONSTANT + exception.getMessage());
        }
    }

    private void setGeneralBtnAction(CustomerAction c) {
        customerAction = c;
        designPageBasedOnAction(customerAction);
    }

    private void setGetChecksListBtnAction() {
        setGeneralBtnAction(CustomerAction.CHECKLIST);
        try {
            ArrayList<String> checkList = new ArrayList<>(SystemController.getChecksList(onlineUserCustomerNumber));
            StringBuilder stringBuilder = new StringBuilder("<html>");
            checkList.forEach((x) -> stringBuilder.append("<br>").append(x).append("<br>----------------"));
            stringBuilder.append("</html>");
            messageLbl.setText(stringBuilder.toString());
            mainMenuPageLogger.logInfo("/CheckList/ " + LOG_MESSAGE_CONSTANT + messageLbl.getText());
        } catch (Exception exception) {
            messageLbl.setText(exception.getMessage());
            mainMenuPageLogger.logInfo("/Checklist/ " + LOG_MESSAGE_CONSTANT + exception.getMessage());
        }
    }

    private void issueTheCheck() {
        try {
            messageLbl.setText(SystemController.issueTheCheck(onlineUserCustomerNumber, Integer.parseInt(checkNumberTxt.getText()), Integer.parseInt(priceTxt.getText()), receiverNumberTxt.getText()));
            mainMenuPageLogger.logInfo("/Issue Check/ " + LOG_MESSAGE_CONSTANT + " issue the check " + checkNumberTxt.getText() + " with price " + priceTxt.getText() + " for customer " + receiverNumberTxt.getText());
        } catch (Exception exception) {
            messageLbl.setText(exception.getMessage());
            mainMenuPageLogger.logInfo("/Issue Check/ " + LOG_MESSAGE_CONSTANT + " Couldnt Issue the Check " + checkNumberTxt.getText() + " : " + exception.getMessage());
        }
    }

    private void transferTheCheck() {
        try {
            messageLbl.setText(SystemController.transferCheck(onlineUserCustomerNumber, Integer.parseInt(checkNumberTxt.getText()), receiverNumberTxt.getText()));
            mainMenuPageLogger.logInfo("/Transfer Check/ " + LOG_MESSAGE_CONSTANT + " transfer the check " + checkNumberTxt.getText() + " to customer " + receiverNumberTxt.getText());
        } catch (Exception exception) {
            messageLbl.setText(exception.getMessage());
            mainMenuPageLogger.logInfo("/Transfer Check/ " + LOG_MESSAGE_CONSTANT + " Couldnt Transfer the Check " + checkNumberTxt.getText() + " : " + exception.getMessage());
        }
    }

    private void inquiryTheCheck() {
        try {
            try {
                Cheque cheque = SystemController.inquiryCheck(Integer.parseInt(checkNumberTxt.getText()));
                List<String> receivers = new ArrayList<>();
                cheque.getReceivers().forEach((x) -> receivers.add(x.getFirstName() + " " + x.getLastName()));
                messageLbl.setText("<html>Check Number: " + cheque.getId() + "<br>Status: " + cheque.getStatus().name() + "<br>Price: " + cheque.getPrice() +
                        "<br>Exporter: " + cheque.getExporter().getCustomerNumber() + "<br>Owner: " + cheque.getOwner().getCustomerNumber() + "<br>Receivers: " + receivers.toString() + "</html>");
            } catch (Exception e1) {
                messageLbl.setText(e1.getMessage());
                mainMenuPageLogger.logInfo("/Inquiry Check/ Couldnt Show the Check Info : " + e1.getMessage());
            }
            mainMenuPageLogger.logInfo("/Inquiry Check/ " + LOG_MESSAGE_CONSTANT + " Inquiry Check" + checkNumberTxt.getText());
        } catch (Exception exception) {
            messageLbl.setText(exception.getMessage());
            mainMenuPageLogger.logInfo("/Inquiry Check/ " + LOG_MESSAGE_CONSTANT + " Couldnt Inquiry the Check " + checkNumberTxt.getText() + " : " + exception.getMessage());
        }
    }

    private void acceptTheCheck() {
        try {
            messageLbl.setText(SystemController.acceptRejectCheck(onlineUserCustomerNumber, Integer.parseInt(checkNumberTxt.getText()), true));
            mainMenuPageLogger.logInfo("/Accept Check/ " + LOG_MESSAGE_CONSTANT + " Accept the Check " + checkNumberTxt.getText());
        } catch (Exception exception) {
            messageLbl.setText(exception.getMessage());
            mainMenuPageLogger.logInfo("/Accept Check/ " + LOG_MESSAGE_CONSTANT + " Couldnt Accept the check " + checkNumberTxt.getText() + " : " + exception.getMessage());
        }
    }

    private void rejectTheCheck() {
        try {
            messageLbl.setText(SystemController.acceptRejectCheck(onlineUserCustomerNumber, Integer.parseInt(checkNumberTxt.getText()), false));
            mainMenuPageLogger.logInfo("/Reject Check/ " + LOG_MESSAGE_CONSTANT + " Reject the Check " + checkNumberTxt.getText());
        } catch (Exception exception) {
            messageLbl.setText(exception.getMessage());
            mainMenuPageLogger.logInfo("/Reject Check/ " + LOG_MESSAGE_CONSTANT + " Couldnt Reject the Check " + checkNumberTxt.getText() + " : " + messageLbl.getText());
        }
    }

    private void cacheTheCheck() {
        try {
            messageLbl.setText(SystemController.cacheTheCheck(onlineUserCustomerNumber, Integer.parseInt(checkNumberTxt.getText())));
            mainMenuPageLogger.logInfo("/Cache Check/ " + LOG_MESSAGE_CONSTANT + " Cache the Check " + checkNumberTxt.getText());
        } catch (Exception exception) {
            messageLbl.setText(exception.getMessage());
            mainMenuPageLogger.logInfo("/Cache Check/  Customer " + onlineUserCustomerNumber + " Cache the Check " + checkNumberTxt.getText() + " : " + exception.getMessage());
        }
    }

    private void setBtnActions() {

        activateCustomerBtn.addActionListener(e -> setActivateBtnAction());

        inactivateCustomerBtn.addActionListener(e -> setInactivateBtnAction());

        issueCheckBtn.addActionListener(e -> setGeneralBtnAction(CustomerAction.ISSUE));

        transferCheckBtn.addActionListener(e -> setGeneralBtnAction(CustomerAction.TRANSFER));

        inquiryCheckBtn.addActionListener(e -> setGeneralBtnAction(CustomerAction.INQUIRY));

        acceptCheckBtn.addActionListener(e -> setGeneralBtnAction(CustomerAction.ACCEPT));

        rejectCheckBtn.addActionListener(e -> setGeneralBtnAction(CustomerAction.REJECT));

        getChecksListBtn.addActionListener(e -> setGetChecksListBtnAction());

        cacheTheCheckBtn.addActionListener(e -> setGeneralBtnAction(CustomerAction.CACHE));

        logoutBtn.addActionListener(e -> {
            mainMenuPageLogger.logInfo(LOG_MESSAGE_CONSTANT + " logout");
            new Login().start();
        });

        okBtn.addActionListener(e -> {
            setVisibleForItems(false, false, false, false, false, false, false);
            if (!isTextFieldValid()) {
                deleteAllTextFields();
                return;
            }

            switch (customerAction) {
                case ISSUE:
                    issueTheCheck();
                    break;
                case TRANSFER:
                    transferTheCheck();
                    break;
                case INQUIRY:
                    inquiryTheCheck();
                    break;
                case ACCEPT:
                    acceptTheCheck();
                    break;
                case REJECT:
                    rejectTheCheck();
                    break;
                case CACHE:
                    cacheTheCheck();
                    break;
            }
            deleteAllTextFields();
        });
    }

    private boolean isTextFieldValid() {
        try {
            checkTextFields();
        } catch (Exceptions exceptions) {
            messageLbl.setText(exceptions.getMessage());
            mainMenuPageLogger.logInfo(LOG_MESSAGE_CONSTANT + " Enter Invalid Input : " + exceptions.getMessage());
            return false;
        }
        return true;
    }

    private void checkTextFields() throws Exceptions {
        switch (customerAction) {
            case ISSUE:
                ValidatingTxt.validate(checkNumberTxt.getText(), TextBoxType.CHECKNUMBER);
                ValidatingTxt.validate(receiverNumberTxt.getText(), TextBoxType.CUSTOMERNUMBER);
                ValidatingTxt.validate(priceTxt.getText(), TextBoxType.PRICE);
                break;
            case TRANSFER:
                ValidatingTxt.validate(checkNumberTxt.getText(), TextBoxType.CHECKNUMBER);
                ValidatingTxt.validate(receiverNumberTxt.getText(), TextBoxType.CUSTOMERNUMBER);
                break;
            case INQUIRY:
            case ACCEPT:
            case REJECT:
            case CACHE:
                ValidatingTxt.validate(checkNumberTxt.getText(), TextBoxType.CHECKNUMBER);
                break;
        }
    }

    private void deleteAllTextFields() {
        receiverNumberTxt.setText("");
        priceTxt.setText("");
        checkNumberTxt.setText("");
    }

    private void setVisibleForItems(boolean isCheckNumberTxt, boolean isPriceTxt, boolean isReceiverNumberTxt, boolean isOkBtn,
                                    boolean isCheckNumberLbl, boolean isPriceLbl, boolean isReceiverNumberLbl) {
        checkNumberTxt.setVisible(isCheckNumberTxt);
        priceTxt.setVisible(isPriceTxt);
        receiverNumberTxt.setVisible(isReceiverNumberTxt);
        okBtn.setVisible(isOkBtn);
        checkNumberLbl.setVisible(isCheckNumberLbl);
        priceLbl.setVisible(isPriceLbl);
        receiverNumberLbl.setVisible(isReceiverNumberLbl);
    }

    void start() {
        mainFrame.setContentPane(mainPanel);
    }

}

