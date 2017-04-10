/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Controladores.ventanas;

import Vista.Generadas.FormProcessJPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.edisoncor.gui.button.ButtonAero;
import org.edisoncor.gui.textField.TextField;

/**
 *
 * @author Esteban Kuh
 */
public class FormProcessView implements ActionListener {

    private FormProcessJPanel formProcessView;
    private ButtonAero acceptButton;
    private ButtonAero cancelButton;
    private TextField sizeProcessField;

    public FormProcessView() {
        formProcessView = new FormProcessJPanel();
        initializeComponentsView();
        addActionListenerToComponentsView();
        formProcessView.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object pressedButton = event.getSource();

        if (acceptButton == pressedButton) {
            addProcess();
        }

        if (cancelButton == pressedButton) {
            showPreviousView();
        }
    }

    private void initializeComponentsView() {
        this.acceptButton = formProcessView.acceptButton;
        this.cancelButton = formProcessView.cancelButton;
        this.sizeProcessField = formProcessView.sizeProcessField;
    }

    private void addActionListenerToComponentsView() {
        acceptButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    private void setEmptyFields() {
        sizeProcessField.setText("");
    }

    private void showPreviousView() {
        formProcessView.dispose();
    }

    private void addProcess() {
        
    }

}
