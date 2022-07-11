package com.fonij.electronicCheck.view;


import com.fonij.electronicCheck.controller.Exceptions;
import com.fonij.electronicCheck.controller.ValidatingTxt;
import com.fonij.electronicCheck.model.*;
import com.fonij.electronicCheck.controller.Login;
import com.fonij.electronicCheck.model.ButtonModel;

import javax.swing.*;



class Register {

    private JFrame registerFrame;
    private PanelModel registerPanel;
    private ButtonModel loginBtn;
    private ButtonModel registerBtn;
    private TextBoxModel firstNameTxt;
    private TextBoxModel lastNameTxt;
    private TextBoxModel nationalCodeTxt;
    private LabelModel messageLbl;
    private LoggerModel registerPageLogger;


    Register() {
        registerFrame = FrameModel.getFrame();
        registerPanel = new PanelModel(900, 700, 0, 0, "#14204a", null);

        initializingButtons();
        initializingTextFields();
        initializingLabels();

        registerPageLogger = new LoggerModel(Register.class);
        registerPageLogger.logInfo("Enter in Register Page");

        setBtnActions();
    }

    private void initializingButtons() {
        ButtonModel.setDefaultPanel(registerPanel);
        loginBtn = new ButtonModel("I have account", 200, 30, 375, 400, 20, "#fed038", true);
        registerBtn = new ButtonModel("Sign up", 120, 30, 415, 350, 20, "#fed038", true);
    }

    private void initializingTextFields() {
        TextBoxModel.setDefaultPanel(registerPanel);
        firstNameTxt = new TextBoxModel(150, 30, 400, 200, 20, "#fed038", true);
        lastNameTxt = new TextBoxModel(150, 30, 400, 240, 20, "#fed038", true);
        nationalCodeTxt = new TextBoxModel(150, 30, 400, 280, 20, "#fed038", true);
    }

    private void initializingLabels() {
        LabelModel.setDefaultPanel(registerPanel);
        new LabelModel("First name", 100, 30, 250, 200, 20, "#fed038", true, false);
        new LabelModel("Last name", 100, 30, 250, 240, 20, "#fed038", true, false);
        new LabelModel("National code", 150, 30, 250, 280, 20, "#fed038", true, false);
        messageLbl = new LabelModel("", 300, 50, 325, 460, 20, "#fed038", true, true);
    }

    private void setRegisterBtnAction() {
        try {
            if (isTextFieldValid()) {
                messageLbl.setText(Login.register(firstNameTxt.getText(), lastNameTxt.getText(), nationalCodeTxt.getText()));
                registerPageLogger.logInfo(firstNameTxt.getText() + " " + lastNameTxt.getText() + " " + messageLbl.getText());
            }
        } catch (Exception exception) {
            messageLbl.setText(exception.getMessage());
            registerPageLogger.logInfo(exception.getMessage());
        }
        deleteAllTextFields();
    }

    private void setBtnActions() {
        loginBtn.addActionListener(e -> new com.fonij.electronicCheck.view.Login().start());
        registerBtn.addActionListener(e -> setRegisterBtnAction());
    }

    private boolean isTextFieldValid() {
        try {
            ValidatingTxt.validate(firstNameTxt.getText(), TextBoxType.FIRSTNAME);
            ValidatingTxt.validate(lastNameTxt.getText(), TextBoxType.LASTNAME);
            ValidatingTxt.validate(nationalCodeTxt.getText(), TextBoxType.NATIONALCODE);
            return true;
        } catch (Exceptions exceptions) {
            messageLbl.setText(exceptions.getMessage());
            registerPageLogger.logInfo("Invalid Input : " + exceptions.getMessage());
            return false;
        }
    }

    private void deleteAllTextFields() {
        firstNameTxt.setText("");
        lastNameTxt.setText("");
        nationalCodeTxt.setText("");
    }

    void start() {
        registerFrame.setContentPane(registerPanel);
    }

}

