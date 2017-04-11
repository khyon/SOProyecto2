/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Memoria;

import UnidadesDeMemoria.Particiones;
import UnidadesDeMemoria.Proceso;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Andres
 */
public class MejorAjuste extends GestionRAM {

    protected ArrayList<Particiones> Comparaciones = new ArrayList();

    public MejorAjuste(int tamaño_total, int kernel) {
        this.tamaño_total = tamaño_total;
        this.kernel = kernel;
    }

    public void fusion_contigua() {
        if (RAM.size() >= 3) {
            for (int i = 1; i < RAM.size() - 1; i++) {
                if (RAM.get(i).isEstado() == false && RAM.get(i + 1).isEstado() == false) {
                    System.out.println("Iniciando fusión");
                    System.out.println("\n...");
                    RAM.get(i).setTamaño(RAM.get(i).getTamaño() + RAM.get(i + 1).getTamaño());
                    RAM.remove(i + 1);
                    Ordena_tamañosAscendentes();
                    fusion_contigua();
                    System.out.println("\n...");
                    System.out.println("Fusión terminada");
                }
            }
        }
    }

    public void extraer_proceso(int indice) {
        for (int i = 1; i < RAM.size(); i++) {
            if (RAM.get(i).getProceso() == indice) {
                RAM.get(i).estado = false;
                fusion_contigua();
                Gestionar_cola();
                Ordena_tamañosAscendentes();
                break;
            }
        }
    }

    public void Ordena_tamañosAscendentes() {
        ArrayList<Particiones> Compare = new ArrayList();
        int y = 1;
        int cuenta_orden = 1;
        for (int n = 1; n < RAM.size(); n++) {
            if (RAM.get(n).isEstado() == false) {
                Compare.add(RAM.get(n));
            }
        }
        for (int i = 0; i < Compare.size() - 1; i++) {
            for (int j = i + 1; j < Compare.size(); j++) {
                Particiones j1 = Compare.get(i);
                Particiones j2 = Compare.get(j);
                if (j2.getTamaño() < j1.getTamaño()) {
                    Compare.set(i, j2);
                    Compare.set(j, j1);
                }
            }
        }
        for (int k = 0; k < Compare.size(); k++) {
            while (y < RAM.size()) {
                if (Compare.get(k).getId() == RAM.get(y).getId()) {
                    RAM.get(y).setOrden(cuenta_orden);
                    cuenta_orden++;
                    break;
                }
                y++;
            }
            y = 0;
        }
    }

    public boolean compactacion(Proceso proceso, ArrayList<Particiones> IndiceFragmentaciones) {

        if (IndiceFragmentaciones.size() > 0) {
            int suma_fragmentaciones = 0;
            for (int i = 0; i < IndiceFragmentaciones.size(); i++) {
                suma_fragmentaciones = suma_fragmentaciones + IndiceFragmentaciones.get(i).getTamaño();
            }
            if (proceso.getTamaño() <= suma_fragmentaciones) {
                System.out.println("Iniciando desfragmentación externa...");
                System.out.println("\n...");
                for (int i = 1; i < RAM.size() - 1; i++) {
                    for (int j = i + 1; j < RAM.size(); j++) {
                        Particiones j1 = RAM.get(i);
                        Particiones j2 = RAM.get(j);
                        if (j1.isEstado() == false && j2.isEstado() == true) {
                            RAM.set(i, j2);
                            RAM.set(j, j1);
                            RAM.get(i).setLocalidad(calcula_localidad(i));
                            RAM.get(j).setLocalidad(calcula_localidad(j));
                            fusion_contigua();
                            Ordena_tamañosAscendentes();
                            if (Asigna_AreaLibreMA(proceso) == true) {
                                System.out.println("\n...");
                                imprimir();
                                System.out.println("Desfragmentación terminada");
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void ObtenerMejorAjuste(Proceso proceso) {
        for (int k = 0; k < Comparaciones.size(); k++) {
            Comparaciones.get(k).setAjuste(Math.abs(Comparaciones.get(k).getTamaño() - proceso.getTamaño()));
        }
        for (int i = 0; i < Comparaciones.size() - 1; i++) {
            for (int j = i + 1; j < Comparaciones.size(); j++) {
                Particiones j1 = Comparaciones.get(i);
                Particiones j2 = Comparaciones.get(j);
                if (j1.getAjuste() > j2.getAjuste()) {
                    Comparaciones.set(i, j2);
                    Comparaciones.set(j, j1);
                }
            }
        }
    }

    public boolean Asigna_AreaLibreMA(Proceso proceso) {

        Comparaciones.clear();
        if (proceso.getId() == 0) {
            Particiones particion = new Particiones(true, proceso.getTamaño(), 0);
            RAM.add(particion);
            Particiones separacion = new Particiones(false, Math.abs(tamaño_total - proceso.getTamaño()), proceso.getTamaño(), 0, 0);
            RAM.add(separacion);
        } else {
            for (int n = 1; n < RAM.size(); n++) {
                if (RAM.get(n).isEstado() == false) {
                    Comparaciones.add(RAM.get(n));
                }
            }
            if (Comparaciones.size() > 0) {
                ObtenerMejorAjuste(proceso);
                for (int z = 0; z < Comparaciones.size(); z++) {
                    Particiones MejorAjustado = Comparaciones.get(z);
                    if (MejorAjustado.getTamaño() >= proceso.getTamaño()) {
                        for (int i = 0; i < RAM.size(); i++) {
                            if (MejorAjustado.getAjuste() == RAM.get(i).getAjuste()) {
                                RAM.get(i).setEstado(true);
                                if (proceso.getTamaño() == RAM.get(i).getTamaño()) {
                                    RAM.get(i).setTamaño(proceso.getTamaño());
                                    RAM.get(i).setProceso(proceso.getId());
                                    return true;
                                }
                                if (RAM.get(i).getTamaño() > proceso.getTamaño()) {
                                    RAM.get(i).setEstado(true);
                                    Particiones separacion = new Particiones(false, Math.abs(proceso.getTamaño() - RAM.get(i).getTamaño()), 0, 0, etiqueta);
                                    etiqueta++;
                                    RAM.get(i).setTamaño(proceso.getTamaño());
                                    RAM.get(i).setProceso(proceso.getId());
                                    RAM.add(i+1,separacion);
                                    RAM.get(i + 1).setLocalidad(calcula_localidad(i + 1));
                                    Ordena_tamañosAscendentes();
                                    fusion_contigua();
                                    return true;
                                }
                            }
                        }
                    } 
                }
            }
            if (compactacion(proceso, Comparaciones) == false) {
                System.out.println("El proceso entra en cola");
                ColaEspera.add(proceso);
            }
        }

        return true;
    }

    public boolean desencolar(Proceso proceso) {
        ArrayList<Particiones> IndiceFragmentaciones = new ArrayList();
        for (int i = 1; i < RAM.size(); i++) {

            if (RAM.get(i).isEstado() == false && proceso.getTamaño() <= RAM.get(i).getTamaño()) {
                RAM.get(i).setEstado(true);
                if (proceso.getTamaño() == RAM.get(i).getTamaño()) {
                    RAM.get(i).setTamaño(proceso.getTamaño());
                    RAM.get(i).setProceso(proceso.getId());
                    return true;
                }
                Particiones separacion = new Particiones(false, Math.abs(proceso.getTamaño() - RAM.get(i).getTamaño()), 0, 0, etiqueta);
                etiqueta++;
                RAM.get(i).setTamaño(proceso.getTamaño());
                RAM.get(i).setProceso(proceso.getId());
                RAM.add(separacion);
                RAM.get(i + 1).setLocalidad(calcula_localidad(i + 1));
                Ordena_tamañosAscendentes();
                fusion_contigua();
                return true;
            }
            if (RAM.get(i).isEstado() == false) {
                IndiceFragmentaciones.add(RAM.get(i));
            }
        }
        if (compactacion(proceso, IndiceFragmentaciones) == true) {
            return true;
        } else {
            return false;
        }
    }

    public void Gestionar_cola() {
        if (ColaEspera.size() > 0) {

            if (desencolar(ColaEspera.peek()) == true) {
                ColaEspera.poll();
            }
        }
    }

}
