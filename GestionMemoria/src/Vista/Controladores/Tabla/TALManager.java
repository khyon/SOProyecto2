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
public class TALManager extends TableManager {

    private final String[] COLUMN_NAMES = {"ID Partición", "Estado", "Localidad", "Tam. Disponible", "Orden"};

    public TALManager(JTable table) {
        super(table);
        createTALModel();
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
            partition.getOrden()
        };
        return partitionRow;
    }

    private void createTALModel() {
        createTableModel(COLUMN_NAMES);
    }
}
