package com.fonij.electronicCheck;

import com.fonij.electronicCheck.controller.ReadJson;
import com.fonij.electronicCheck.model.FrameModel;
import com.fonij.electronicCheck.model.LoggerModel;
import com.fonij.electronicCheck.view.Login;

/**
 * complete
 */
public class Main {

    public static void main(String[] args) {
        LoggerModel.setConsoleAppenderConfiguration();
        FrameModel.createFrame("Electronic Check", 900, 700, false, "/images/icon.png");
        ReadJson.readJsonFiles();
        new Login().start();
    }

}
