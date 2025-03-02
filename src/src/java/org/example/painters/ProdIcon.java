package org.example.painters;

import javax.swing.*;

public class ProdIcon {
    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    String production;
    ImageIcon imageIcon;

    public ProdIcon(String production, ImageIcon imageIcon) {
        this.production = production;
        this.imageIcon = imageIcon;
    }
}
