/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Controladores.ventanas;

import static Test.MainTest.view;
import UnidadesDeMemoria.Proceso;
import Vista.Generadas.FormProcessJPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
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
    private ArrayList<Proceso> listaProceso;
    private int memoria_disponible;
    private int proceso_actual;

    public FormProcessView(int memoria_disponible, int proceso_actual) {
        this();
        this.memoria_disponible = memoria_disponible;
        this.proceso_actual = proceso_actual;
        listaProceso = new ArrayList();
    }

    private FormProcessView() {
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
            setEmptyFields();
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
        try {
            int size = Integer.parseInt(sizeProcessField.getText());
            if (size > 0) {
                if ((memoria_disponible - size) >= 0) {
                    listaProceso.add(new Proceso(size, proceso_actual));
                    view.setListaProceso(listaProceso);
                    proceso_actual++;
                    memoria_disponible = memoria_disponible - size;
                    view.uptadeSizePanel(size);
                    JOptionPane.showMessageDialog(null, "Proceso Agregado");
                } else {
                    JOptionPane.showMessageDialog(null, "Tamaño del proceso muy grande.\nTamaño Ingresado : " + size + "\nMemoria disponible : " + memoria_disponible);
                }
            } else {
                JOptionPane.showMessageDialog(null, "El tamaño del Proceso debe ser mayor a 0");
            }
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, "Sólo ingresar números");
        }
    }

}
