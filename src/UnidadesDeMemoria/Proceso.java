/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UnidadesDeMemoria;

/**
 *
 * @author A15216336
 */
public class Proceso {
    
    public int tamaño;
    public int id;
   
    public Proceso(int tamaño, int id) {
        this.tamaño = tamaño;
        this.id= id;
    }
    
   
    public int getTamaño() {
        return tamaño;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    @Override
    public String toString() {
        return "Proceso{" + "tama\u00f1o=" + tamaño + ", id=" + id + '}';
    }
    
    
}
