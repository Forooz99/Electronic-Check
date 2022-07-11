package com.fonij.electronicCheck.model;


import javax.swing.*;
import java.awt.*;


public class LabelModel extends JLabel {

    private static PanelModel defaultPanel;


    public LabelModel(String text, int width, int height, int x, int y, int fontSize, String color, boolean isVisible, boolean isCenter) {
        super(text);
        if (isCenter)
            this.setHorizontalAlignment(SwingConstants.CENTER);
        else
            this.setHorizontalAlignment(SwingConstants.LEFT);
        this.setBounds(x, y, width, height);
        this.setFont(new Font("Serif", Font.PLAIN, fontSize));
        this.setForeground(Color.decode(color));
        this.setVisible(isVisible);
        defaultPanel.add(this);
    }

    public static void setDefaultPanel(PanelModel defaultPanel) {
        LabelModel.defaultPanel = defaultPanel;
    }

}

