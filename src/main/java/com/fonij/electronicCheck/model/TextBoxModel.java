package com.fonij.electronicCheck.model;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;


public class TextBoxModel extends JTextField {

    private static PanelModel defaultPanel;


    public TextBoxModel(int width, int height, int x, int y, int fontSize, String color, boolean isVisible) {
        super();
        this.setBounds(x, y, width, height);
        this.setFont(new Font("Serif", Font.PLAIN, fontSize));
        this.setForeground(Color.BLACK);
        this.setBorder(new LineBorder(Color.decode(color), 0x0));
        this.setVisible(isVisible);
        defaultPanel.add(this);
    }

    public static void setDefaultPanel(PanelModel defaultPanel) {
        TextBoxModel.defaultPanel = defaultPanel;
    }

}

