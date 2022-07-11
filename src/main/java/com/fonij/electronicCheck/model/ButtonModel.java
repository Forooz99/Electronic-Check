package com.fonij.electronicCheck.model;


import javax.swing.*;
import java.awt.*;


public class ButtonModel extends JButton {

    private static PanelModel defaultPanel;


    public ButtonModel(String text,int width, int height, int x, int y, int fontSize, String color, boolean isVisible) {
        super(text);
        this.setBounds(x, y, width, height);
        this.setFont(new Font("Serif", Font.PLAIN, fontSize));
        this.setBorderPainted(false);
        this.setFocusable(false);
        this.setBackground(Color.decode(color));
        this.setVisible(isVisible);
        defaultPanel.add(this);
    }

    public static void setDefaultPanel(PanelModel defaultPanel) {
        ButtonModel.defaultPanel = defaultPanel;
    }

}

