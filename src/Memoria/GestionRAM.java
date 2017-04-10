/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Memoria;

import static Test.MainTest.view;
import static Vista.Controladores.ventanas.MainView.listaTAL;
import static Vista.Controladores.ventanas.MainView.listaTP;
import UnidadesDeMemoria.Particiones;
import UnidadesDeMemoria.Proceso;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Andres
 */
public abstract class GestionRAM {
    protected ArrayList<Particiones> RAM = new ArrayList();
    protected int tamaño_total;
    protected int etiqueta = 1;
    protected int kernel;
    protected Queue<Proceso> ColaEspera = new LinkedList<Proceso>();
    
     public int calcula_localidad(int i) {
        int localidad=0;
        for(int j=0;j<i; j++){
            localidad+=RAM.get(j).getTamaño();
        }
        return localidad;
    }
      public void imprimir() {
          listaTP.clear();
          listaTAL.clear();
        for (int i = 1; i < RAM.size(); i++) {
            System.out.println(RAM.get(i));
//            System.out.println(RAM + "\n***** NUEVO DE RAM ******\n");
            
            if(RAM.get(i).estado){
                listaTP.add(RAM.get(i));
                
            }else{
                listaTAL.add(RAM.get(i));
                
            }
            view.updateTPTable();
            view.updateTALTable();
            
        }
    }

    public ArrayList getRAM() {
        return RAM;
    }

    public void setRAM(ArrayList RAM) {
        this.RAM = RAM;
    }

    public Queue getColaEspera() {
        return ColaEspera;
    }

    public void setColaEspera(Queue ColaEspera) {
        this.ColaEspera = ColaEspera;
    }
   
    
}
