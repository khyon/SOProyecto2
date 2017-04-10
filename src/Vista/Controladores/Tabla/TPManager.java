/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Controladores.Tabla;

import UnidadesDeMemoria.Particiones;
import java.util.ArrayList;
import javax.swing.JTable;

/**
 *
 * @author Mario
 */
public class TPManager extends TableManager {

    private final String[] COLUMN_NAMES = {"ID Partición", "Estado", "Localidad", "Tamaño", "Proceso"};

    public TPManager(JTable table) {
        super(table);
        createTPModel();
    }

    public void setModel(ArrayList<Particiones> partitions) {
        clearModel();
        for (Particiones partition : partitions) {
            addPartitionRow(partition);
        }
        setModel();
    }

    private void addPartitionRow(Particiones partition) {
        Object[] partitionRow = createPartitionRow(partition);
        addRow(partitionRow);
    }

    private Object[] createPartitionRow(Particiones partition) {
        Object[] partitionRow = new Object[]{
            partition.getId(),
            partition.estado,
            partition.getLocalidad(),
            partition.getTamaño(),
            "P" + partition.getProceso()
        };
        return partitionRow;
    }

    private void createTPModel() {
        createTableModel(COLUMN_NAMES);
    }

}
