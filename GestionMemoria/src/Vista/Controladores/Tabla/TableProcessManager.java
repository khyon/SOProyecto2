/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Controladores.Tabla;

import UnidadesDeMemoria.Proceso;
import java.util.ArrayList;
import javax.swing.JTable;

/**
 *
 * @author Esteban Kuh
 */
public class TableProcessManager extends TableManager {

    private final String[] COLUMN_NAMES = {"Proceso", "Tamaño"};

    public TableProcessManager(JTable table) {
        super(table);
        createTableProcessModel();
    }

    private void createTableProcessModel() {
        createTableModel(COLUMN_NAMES);
    }

    public void setModel(ArrayList<Proceso> processes) {
        clearModel();
        for (Proceso process : processes) {
            addProcessRow(process);
        }
        setModel();
    }

    private void addProcessRow(Proceso process) {
        Object[] processRow = createProcessRow(process);
        addRow(processRow);
    }

    private Object[] createProcessRow(Proceso process) {
        Object[] processRow = new Object[]{
            process.getId(),
            process.getTamaño()
        };
        return processRow;
    }

}
