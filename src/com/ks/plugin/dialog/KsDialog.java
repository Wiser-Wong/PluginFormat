package com.ks.plugin.dialog;

import javax.swing.*;
import java.awt.*;

public class KsDialog extends JFrame{

    public KsDialog() {
        Container container=getContentPane();
        JLabel imageBackgroundLabel=new JLabel("这是一个JFrame窗体",JLabel.CENTER);
        ImageIcon icon = new ImageIcon("/Users/wangxy/wiser/ideaPjt/KsCodeFormatPlugin/src/com/ks/plugin/images/ks_view.jpeg");
        imageBackgroundLabel.setIcon(icon);
        imageBackgroundLabel.setOpaque(true);
        container.add(imageBackgroundLabel);
        setSize(500,300);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void showKsDialog(){
        new KsDialog();
    }
}
