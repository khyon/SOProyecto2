/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Memoria;

import UnidadesDeMemoria.Particiones;
import UnidadesDeMemoria.Proceso;
import java.util.ArrayList;

/**
 *
 * @author Andres
 */
public class PrimerAjuste extends GestionRAM {

    public PrimerAjuste(int tamaño_total, int kernel) {
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
                    Ordena_localidades();
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
                Ordena_localidades();
                break;
            }
        }
    }    
  public void Ordena_localidades() {
        
        int y = 1;
        int cuenta_orden = 1;
        for (int n = 1; n < RAM.size(); n++) {
           if(RAM.get(n).isEstado()==false){
               RAM.get(n).setOrden(cuenta_orden);
               cuenta_orden++;
        }
        
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
                            Ordena_localidades();
                            if (Asigna_AreaLibrePMRA(proceso) == true) {
                                System.out.println("\n...");
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

    public boolean Asigna_AreaLibrePMRA(Proceso proceso) {
        ArrayList<Particiones> IndiceFragmentaciones = new ArrayList();
        if (proceso.getId() == 0) {
            Particiones particion = new Particiones(true, proceso.getTamaño(), 0);
            RAM.add(particion);
            Particiones separacion = new Particiones(false, Math.abs(tamaño_total - proceso.getTamaño()), proceso.getTamaño(), 0, 0);
            RAM.add(separacion);
        } else {
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
                    RAM.add(i+1,separacion);
                    RAM.get(i + 1).setLocalidad(calcula_localidad(i + 1));
                    Ordena_localidades();
                    fusion_contigua();
                    return true;
                }
                if (RAM.get(i).isEstado() == false) {
                    IndiceFragmentaciones.add(RAM.get(i));
                }
            }
            if (compactacion(proceso, IndiceFragmentaciones) == false) {
                ColaEspera.add(proceso);
            }
        }
        return true;
    }

    public boolean desencolar(Proceso proceso) {
        ArrayList<Particiones> IndiceFragmentaciones = new ArrayList();
        for (int i = 1; i < RAM.size(); i++) {
            int mira = RAM.get(i).getTamaño();
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
                Ordena_localidades();
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
