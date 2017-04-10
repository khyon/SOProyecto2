/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UnidadesDeMemoria;

import static Test.MainTest.view;

/**
 *
 * @author Mario
 */
public class Particiones {
 public boolean estado;
 protected int tamaño;
 protected int localidad;
 protected int orden;
 protected int id;
 protected int proceso;
 protected int ajuste;
 
    public Particiones(boolean estado, int tamaño, int localidad) {
        this.estado = estado;
        this.tamaño = tamaño;
        this.localidad = localidad;
        this.orden = orden;
    }
     
      public Particiones(boolean estado, int tamaño, int localidad, int orden, int id) {
        this.estado = estado;
        this.tamaño = tamaño;
        this.localidad = localidad;
        this.orden = orden;
        this.id=id;
    }
      public Particiones(boolean estado, int tamaño, int localidad, int orden, int id, int proceso) {
        this.estado = estado;
        this.tamaño = tamaño;
        this.localidad = localidad;
        this.orden = orden;
        this.proceso=proceso;
        
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public int getAjuste() {
        return ajuste;
    }

    public void setAjuste(int ajuste) {
        this.ajuste = ajuste;
    }

    public int getLocalidad() {
        return localidad;
    }

    public void setLocalidad(int localidad) {
        this.localidad = localidad;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProceso() {
        return proceso;
    }

    public void setProceso(int proceso) {
        this.proceso = proceso;
    }

    @Override
    public String toString() {

        String imprimir = " \n ID particion = " + id
                + "\n Estado = " + estado
                + "\n Localidad = " + localidad
                + "\n Tamaño = " + tamaño;
        if (estado == false) {
            //view.updateTALTable();

            return imprimir
                    + "\n Orden =" + orden;
        } else {
            //view.updateTPTable();
            return imprimir
                    + "\n Proceso =" + "P" + proceso;

        }
    }

}
