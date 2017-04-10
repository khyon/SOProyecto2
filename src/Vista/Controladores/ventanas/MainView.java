/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Controladores.ventanas;

import Memoria.MejorAjuste;
import Memoria.PeorAjuste;
import Memoria.PrimerAjuste;
import UnidadesDeMemoria.Particiones;
import UnidadesDeMemoria.Proceso;
import Vista.Controladores.Tabla.TPManager;
import Vista.Controladores.Tabla.TALManager;
import Vista.Controladores.Tabla.TableProcessManager;
import Vista.Generadas.MainJPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import org.edisoncor.gui.button.*;
import org.edisoncor.gui.textField.TextField;

/**
 *
 * @author Esteban Kuh
 */
public class MainView implements ActionListener {

    public static MainJPanel mainView;

    private ArrayList<Proceso> listaProceso = new ArrayList();
    public static ArrayList<Particiones> listaTAL = new ArrayList();
    public static ArrayList<Particiones> listaTP = new ArrayList();
    private ArrayList listaProcesosTabla = new ArrayList();

    private ButtonAero acceptButton;
    private ButtonAero clearTablesButton;
    private ButtonAero extractProcessButton;
    private ButtonAeroRound acceptSizeButton;
    private ButtonAeroRound addProcessButton;
    private ButtonAeroRound selfAddProcessButton;
    private JRadioButton betterAdjustRadioButton;
    private JRadioButton firstAdjustRadioButton;
    private JRadioButton worstAdjustRadioButton;
    private JTable processTable;
    private JTable tpTable;
    private JTable talTable;
    private TextField ramField;
    private TextField kernelField;
    private TextField ramAvailableField;
    private TextField ramOccupyField;

    private TableProcessManager tableManager;
    private TPManager tpManager;
    private TALManager talManager;

    private PrimerAjuste memoriaPrimerAjuste;
    private MejorAjuste memoriaMejorAjuste;
    private PeorAjuste memoriaPeorAjuste;
    private int tamaño_memoria;
    private int tamaño_kernel;
    private int ramOccupy = 0;
    private int ramAvailable;
    private int tamanioProcesoActual = 0;

    public MainView() {
        mainView = new MainJPanel();
        initializeComponents();
        initializeManagers();
        addActionListenerToComponentsView();
        initializeTableValues();
        mainView.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object pressedButton = event.getSource();

        if (betterAdjustRadioButton.isSelected() || firstAdjustRadioButton.isSelected() || worstAdjustRadioButton.isSelected()) {
            if (acceptButton == pressedButton) {
                actionAccept();
            }

            if (acceptSizeButton == pressedButton) {
                actionAcceptSizeMemory();
            }

            if (addProcessButton == pressedButton) {
                actionAddProcess();
            }

            if (selfAddProcessButton == pressedButton) {
                actionSelfAddProcess();
            }

            if (extractProcessButton == pressedButton) {
                actionExtractProcess();
            }

            if (clearTablesButton == pressedButton) {
                actionClearElementsView();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Seleccionar el Tipo de Algoritmo de Ajuste");
        }
    }

    public void updateTALTable() {
        talManager.setModel(listaTAL);
    }

    public void updateTPTable() {
        tpManager.setModel(listaTP);
    }

    private void actionAccept() {
        if (processTable.getRowCount() > 0) {
            if (tamanioProcesoActual < listaProceso.size()) {
                if (firstAdjustRadioButton.isSelected()) {
                    memoriaPrimerAjuste.Asigna_AreaLibrePMRA(listaProceso.get(tamanioProcesoActual));
                    memoriaPrimerAjuste.imprimir();
                } else {
                    if (betterAdjustRadioButton.isSelected()) {
                        memoriaMejorAjuste.Asigna_AreaLibreMA(listaProceso.get(tamanioProcesoActual));
                        memoriaMejorAjuste.imprimir();
                    } else {
                        if (worstAdjustRadioButton.isSelected()) {
                            memoriaPeorAjuste.Asigna_AreaLibrePA(listaProceso.get(tamanioProcesoActual));
                            memoriaPeorAjuste.imprimir();
                        }
                    }
                }
                setRamAvailable(listaProceso.get(tamanioProcesoActual).getTamaño(), "-");
                setRamOccupy(listaProceso.get(tamanioProcesoActual).getTamaño(), "+");
                System.out.println("\n Nueva Linea - Action Accept\n");
                setDataMemoryPanel();
            } else {
                JOptionPane.showMessageDialog(null, "Sólo puede extraer eventos ahora");
                acceptButton.setEnabled(false);
            }
            tamanioProcesoActual++;
        }
    }

    private void actionAcceptSizeMemory() {
        if (!isSizeFieldsEmpty()) {
            try {
                tamaño_memoria = Integer.parseInt(ramField.getText());
                ramAvailable = tamaño_memoria;
                tamaño_kernel = Integer.parseInt(kernelField.getText());
                ramOccupy = tamaño_kernel;
                if (tamaño_kernel > (tamaño_memoria / 2)) {
                    JOptionPane.showMessageDialog(null, "Tamaño del Kernel muy grande. (Para fines didácticos debe ser menor que la mitad de la RAM.)");
                } else {
                    disableElements();
                    setDataMemoryPanel();
                }
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "Ingresar sólo números");
            }
        }
    }

    private void actionAddProcess() {
        if (!isSizeFieldsEmpty()) {
            if (acceptSizeButton.isEnabled()) {
                JOptionPane.showMessageDialog(null, "Oprimir \"Aceptar\" de la sección de \"Tamaño\" para definir los tamaños");
            } else {
                disableElements();
                updateProcessTable();

            }
        }
    }

    private void actionClearElementsView() {
        enableElements();
        listaProceso.clear();
        listaProcesosTabla.clear();
        listaTAL.clear();
        listaTP.clear();
        updateTALTable();
        updateTPTable();
        updateProcessTable();

        tamaño_memoria = 0;
        tamaño_kernel = 0;
        ramOccupy = 0;
        ramAvailable = 0;
        tamanioProcesoActual=0;
        setDataMemoryPanel();
        setFields(0, 0);
        setAcceptButtonText("Agregar Evento");
    }

    private void actionBetterAdjustAlgorithm(Proceso kernel) {
        memoriaMejorAjuste = new MejorAjuste(tamaño_memoria, tamaño_kernel);
        memoriaMejorAjuste.Asigna_AreaLibreMA(kernel);
    }

    private void actionFirstAdjustAlgorithm(Proceso kernel) {
        memoriaPrimerAjuste = new PrimerAjuste(tamaño_memoria, tamaño_kernel);
        memoriaPrimerAjuste.Asigna_AreaLibrePMRA(kernel);
//        memoriaPrimerAjuste.Asigna_AreaLibrePMRA(listaProceso.get(tamanioProcesoActual));
//        memoriaPrimerAjuste.imprimir();
//        System.out.println("\n Nueva Linea \n");
    }

    private void actionWorstAdjustAlgorithm(Proceso kernel) {
        memoriaPeorAjuste = new PeorAjuste(tamaño_memoria,tamaño_kernel);
        memoriaPeorAjuste.Asigna_AreaLibrePA(kernel);
    }
    
    private void actionExtractProcess() {
        if (tableManager.isRowSelected()) {
            int filaSeleccionada = tableManager.getIdOfSelectedRow();

            if (!listaProcesosTabla.contains(filaSeleccionada)) {
                setRamAvailable(listaProceso.get(filaSeleccionada - 1).getTamaño(), "+");
                setRamOccupy(listaProceso.get(filaSeleccionada - 1).getTamaño(), "-");
                setDataMemoryPanel();

                if (firstAdjustRadioButton.isSelected()) {
                    memoriaPrimerAjuste.extraer_proceso(filaSeleccionada);
                    memoriaPrimerAjuste.imprimir();
                } else {
                    if (betterAdjustRadioButton.isSelected()) {
                        memoriaMejorAjuste.extraer_proceso(filaSeleccionada);
                        memoriaMejorAjuste.imprimir();
                        
                    } else {
                        memoriaPeorAjuste.extraer_proceso(filaSeleccionada);
                        memoriaPeorAjuste.imprimir();
                    }
                }

                listaProcesosTabla.add(filaSeleccionada);
            } else {
                JOptionPane.showMessageDialog(null, "Ya se ha extraido ese Proceso");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Fila NO Seleccionada");
        }
    }

    private void actionSelfAddProcess() {
        setMemory();

        disableElements();
        disableElementAddProcessButton();
        selfAddProcess();
        updateProcessTable();
        setFields(tamaño_memoria, tamaño_kernel);

        setDataMemoryPanel();

        Proceso kernel = new Proceso(tamaño_kernel, 0);

        if (firstAdjustRadioButton.isSelected()) {
            actionFirstAdjustAlgorithm(kernel);
        } else {
            if (betterAdjustRadioButton.isSelected()) {
                actionBetterAdjustAlgorithm(kernel);
            } else {
                if (worstAdjustRadioButton.isSelected()) {
                    actionWorstAdjustAlgorithm(kernel);
                }
            }
        }
    }

    private void addActionListenerToComponentsView() {
        acceptButton.addActionListener(this);
        acceptSizeButton.addActionListener(this);
        clearTablesButton.addActionListener(this);
        extractProcessButton.addActionListener(this);
        addProcessButton.addActionListener(this);
        selfAddProcessButton.addActionListener(this);
        betterAdjustRadioButton.addActionListener(this);
        firstAdjustRadioButton.addActionListener(this);
        worstAdjustRadioButton.addActionListener(this);
    }

    private void disableElements() {
        acceptSizeButton.setEnabled(false);
        ramField.setEditable(false);
        kernelField.setEditable(false);
        selfAddProcessButton.setEnabled(false);
        betterAdjustRadioButton.setEnabled(false);
        firstAdjustRadioButton.setEnabled(false);
        worstAdjustRadioButton.setEnabled(false);
    }

    private void disableElementAddProcessButton() {
        addProcessButton.setEnabled(false);
    }

    private void enableElements() {
        acceptSizeButton.setEnabled(true);
        acceptButton.setEnabled(true);
        ramField.setEditable(true);
        kernelField.setEditable(true);
        addProcessButton.setEnabled(true);
        selfAddProcessButton.setEnabled(true);
        betterAdjustRadioButton.setEnabled(true);
        firstAdjustRadioButton.setEnabled(true);
        worstAdjustRadioButton.setEnabled(true);
    }

    private int getRamAvailable() {
        return ramAvailable;
    }

    private int getRamOccupy() {
        return ramOccupy;
    }

    private void initializeComponents() {
        this.acceptButton = mainView.acceptButton;
        this.clearTablesButton = mainView.clearTablesButton;
        this.extractProcessButton = mainView.extractProcessButton;
        this.acceptSizeButton = mainView.acceptSizeButton;
        this.addProcessButton = mainView.addButton;
        this.selfAddProcessButton = mainView.selfAddButton;
        this.betterAdjustRadioButton = mainView.betterRadioButton;
        this.firstAdjustRadioButton = mainView.firstRadioButton;
        this.worstAdjustRadioButton = mainView.worstRadioButton;
        this.talTable = mainView.processTALTable;
        this.tpTable = mainView.processTPTable;
        this.processTable = mainView.processTable;
        this.ramField = mainView.ramField;
        this.kernelField = mainView.kernelField;
        this.ramAvailableField = mainView.ramAvailableField;
        this.ramOccupyField = mainView.memoryOccupyField;
    }

    private void initializeManagers() {
        tableManager = new TableProcessManager(processTable);
        talManager = new TALManager(talTable);
        tpManager = new TPManager(tpTable);
    }

    private void initializeTableValues() {
        tableManager.setModel(listaProceso);
        talManager.setModel(listaTAL);
        tpManager.setModel(listaTP);
    }

    private boolean isSizeFieldsEmpty() {
        boolean emptyFields = false;

        if (ramField.getText().isEmpty() || kernelField.getText().isEmpty()) {
            emptyFields = true;
            JOptionPane.showMessageDialog(null, "LLenar todos los campos de la sección de \"Tamaño\"");
        }

        return emptyFields;
    }

    public void selfAddProcess() {
        Proceso proceso1 = new Proceso(40, 1);
        Proceso proceso2 = new Proceso(100, 2);
        Proceso proceso3 = new Proceso(200, 3);
        Proceso proceso4 = new Proceso(60, 4);
        Proceso proceso5 = new Proceso(62, 5);
        Proceso proceso6 = new Proceso(222, 6);
        Proceso proceso7 = new Proceso(240, 7);
        listaProceso.add(proceso1);
        listaProceso.add(proceso2);
        listaProceso.add(proceso3);
        listaProceso.add(proceso4);
        listaProceso.add(proceso5);
    }

    private void setAcceptButtonText(String text) {
        acceptButton.setText(text);
    }

    private void setDataMemoryPanel() {
        ramAvailableField.setText("" + getRamAvailable());
        ramOccupyField.setText("" + getRamOccupy());
    }

    private void setFields(int tamaño_memoria, int tamaño_kernel) {
        ramField.setText("" + tamaño_memoria);
        kernelField.setText("" + tamaño_kernel);
        setAcceptButtonText("Agregar Siguiente Evento");
    }

    private void setMemory() {
        tamaño_memoria = 512;
        ramAvailable = tamaño_memoria;
        tamaño_kernel = 50;
        ramOccupy = tamaño_kernel;

//        setRamOccupy(tamaño_kernel, "+");
        setRamAvailable(tamaño_kernel, "-");
    }

    private void setRamAvailable(int restarTamaño, String operador) {
        if ("-".equals(operador)) {
            ramAvailable = ramAvailable - restarTamaño;
        } else {
            ramAvailable = ramAvailable + restarTamaño;
        }
    }

    private void setRamOccupy(int agregarTamaño, String operador) {
        if ("+".equals(operador)) {
            ramOccupy = ramOccupy + agregarTamaño;
        } else {
            ramOccupy = ramOccupy - agregarTamaño;
        }

    }

    private void updateProcessTable() {
        tableManager.setModel(listaProceso);
    }

}
