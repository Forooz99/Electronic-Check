package com.fonij.electronicCheck.model;


import javax.swing.*;
import java.awt.*;


public class PanelModel extends JPanel {

    public PanelModel(int width, int height, int x, int y, String color, LayoutManager layoutManager) {
        super(layoutManager);
        this.setBounds(x, y, width, height);
        this.setBackground(Color.decode(color));
    }

    public void addComponentToBorderLayout(Component component, String borderLayout) {
        this.add(component, borderLayout);
    }

    public void addComponent(Component component) {
        this.add(component);
    }

}

