package com.fonij.electronicCheck.model;


import javax.swing.*;
import java.awt.*;


public class FrameModel extends JFrame {

    private static FrameModel frameModel;


    private FrameModel(String title, int width, int height, boolean isResizable, String imgURL) throws HeadlessException {
        super(title);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(isResizable);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(FrameModel.class.getResource(imgURL)));
    }

    public static void createFrame(String title, int width, int height, boolean isResizable, String imgURL) {
        if (frameModel == null)
            frameModel = new FrameModel(title, width, height, isResizable, imgURL);
    }

    public static FrameModel getFrame() {
        return frameModel;
    }

}

