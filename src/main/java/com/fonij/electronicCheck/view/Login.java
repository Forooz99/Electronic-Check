package com.fonij.electronicCheck.view;

import com.fonij.electronicCheck.controller.Exceptions;
import com.fonij.electronicCheck.controller.FileResourcesUtils;
import com.fonij.electronicCheck.controller.ValidatingTxt;
import com.fonij.electronicCheck.controller.XMLHandler;
import com.fonij.electronicCheck.model.*;
import com.fonij.electronicCheck.model.ButtonModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;


public class Login {

    private FrameModel loginFrame;
    private PanelModel loginPanel;
    private ButtonModel loginBtn;
    private ButtonModel registerBtn;
    private ButtonModel customerFileChooserBtn;
    private ButtonModel checkFileChooserBtn;
    private TextBoxModel customerNumberTxt;
    private LabelModel messageLbl;
    private LoggerModel loginPageLogger;
    private static boolean isXMLRead = false;

    public Login() {
        loginFrame = FrameModel.getFrame();
        loginPanel = new PanelModel(900, 700, 0, 0, "#14204a", null);

        initializingButtons();
        initializingTextFields();
        initializingLabels();

        loginPageLogger = new LoggerModel(Login.class);
        loginPageLogger.logInfo("Enter in Login Page");

        setActions();
    }

    private void initializingButtons() {
        ButtonModel.setDefaultPanel(loginPanel);
        loginBtn = new ButtonModel("Sign in", 100, 30, 400, 300, 20, "#fed038", true);
        registerBtn = new ButtonModel("Sign up", 100, 30, 400, 350, 20, "#fed038", true);
        customerFileChooserBtn = new ButtonModel("Select customer XML File", 250, 30, 325, 480, 20, "#fed038", true);
        checkFileChooserBtn = new ButtonModel("Select check XML File", 250, 30, 325, 520, 20, "#fed038", true);
    }

    private void initializingTextFields() {
        TextBoxModel.setDefaultPanel(loginPanel);
        customerNumberTxt = new TextBoxModel(150, 30, 375, 200, 20, "#fed038", true);
    }

    private void initializingLabels() {
        LabelModel.setDefaultPanel(loginPanel);
        new LabelModel("Customer Number", 150, 30, 220, 200, 20, "#fed038", true, false);
        messageLbl = new LabelModel("", 400, 30, 250, 430, 20, "#fed038", true, true);
    }

    private void setLoginBtnAction() {
        try {
            if (isTextFieldValid()) {
                messageLbl.setText(com.fonij.electronicCheck.controller.Login.login(customerNumberTxt.getText()));
                loginPageLogger.logInfo("Customer " + customerNumberTxt.getText() + " " + messageLbl.getText());
                MainMenu mainMenuView = new MainMenu(customerNumberTxt.getText());
                mainMenuView.start();
            }
        } catch (Exception ee) {
            messageLbl.setText(ee.getMessage());
            loginPageLogger.logInfo("Customer " + customerNumberTxt.getText() + " doesnt exist");
        }
        customerNumberTxt.setText("");
    }

    private void setFileChooserAction(ButtonModel button, boolean isCustomer) {
        button.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setDialogTitle("Select the XML");
            jfc.setDragEnabled(true);
            jfc.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");
            jfc.addChoosableFileFilter(filter);
            loginPanel.add(jfc);
            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                if (isCustomer)
                    XMLFile.setCustomerXML(selectedFile);
                else
                    XMLFile.setCheckXML(selectedFile);
            }
        });
    }

    private void setActions() {
        registerBtn.addActionListener(e -> new Register().start());
        loginBtn.addActionListener(e -> setLoginBtnAction());
        setFileChooserAction(customerFileChooserBtn, true);
        setFileChooserAction(checkFileChooserBtn, false);
    }

    private boolean isTextFieldValid() {
        try {
            ValidatingTxt.validate(customerNumberTxt.getText(), TextBoxType.CUSTOMERNUMBER);
            return true;
        } catch (Exceptions exceptions) {
            messageLbl.setText(exceptions.getMessage());
            loginPageLogger.logInfo("Invalid Input : " + exceptions.getMessage());
            return false;
        }
    }

    public void start() {
        loginFrame.setContentPane(loginPanel);
        if (!isXMLRead) {
            isXMLRead = true;
            xmlSetUp();
        }
    }

    private void xmlSetUp() {
        XMLHandler customerHandler = new XMLHandler(XMLFile.getCustomerXML("xml/customers.xml").getFile(), FileResourcesUtils.getFileFromResource("xsd/customers.xsd"), true);
        if (customerHandler.validateXML())
            customerHandler.readXML();
        else {
            messageLbl.setText("Invalid customers.xml File");
            disableButtons();
        }

        XMLHandler checkHandler = new XMLHandler(XMLFile.getCheckXML("xml/chequesTreasury.xml").getFile(), FileResourcesUtils.getFileFromResource("xsd/chequesTreasury.xsd"), false);
        if (checkHandler.validateXML())
            checkHandler.readXML();
        else {
            messageLbl.setText("Invalid chequesTreasury.xml File");
            disableButtons();
        }
    }

    private void disableButtons() {
        loginBtn.setEnabled(false);
        registerBtn.setEnabled(false);
    }

}
