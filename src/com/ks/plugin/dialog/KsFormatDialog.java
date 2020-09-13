package com.ks.plugin.dialog;

import com.intellij.openapi.actionSystem.AnActionEvent;

import javax.swing.*;
import java.awt.event.*;

public class KsFormatDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JCheckBox generateALayoutFileCheckBox;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JCheckBox generateViewModelFileCheckBox;
    private JTextField textField3;
    private JComboBox comboBox2;
    private JCheckBox generateAAdapterFileCheckBox;
    private JTextField textField4;

    public KsFormatDialog(AnActionEvent event) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void showKsFormatDialog(AnActionEvent event) {
        KsFormatDialog dialog = new KsFormatDialog(event);
        // dialog.setSize(340, 220);
        dialog.setTitle("New KsFrame Format");
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
