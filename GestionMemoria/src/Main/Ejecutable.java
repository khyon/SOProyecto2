/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Memoria.MejorAjuste;
import Memoria.PeorAjuste;
import Memoria.PrimerAjuste;
import UnidadesDeMemoria.Particiones;
import UnidadesDeMemoria.Proceso;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author Andres
 */
public class Ejecutable {
    
    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       Scanner sb = new Scanner(System.in);
       Scanner ss = new Scanner(System.in);
       boolean decision;
        int algoritmo;
        int tamaño_memoria;
        int t_kernel;
        System.out.println("¿Utilizar valores predeterminados?");
        decision= sb.nextBoolean();
       
        if(decision==true){
        ArrayList <Proceso> listaPre = new ArrayList();    
        //Declaracion de procesos
       
        Proceso proceso1 = new Proceso(40,1);
        Proceso proceso2 = new Proceso(100,2);
        Proceso proceso3 = new Proceso(200,3);
        Proceso proceso4 = new Proceso(60,4);
        Proceso proceso5 = new Proceso(62,5);  
        Proceso proceso6 = new Proceso(222,6);  
        Proceso proceso7=   new Proceso(240,7);
        listaPre.add(proceso1);
        listaPre.add(proceso2);
        listaPre.add(proceso3);
        listaPre.add(proceso4);
        listaPre.add(proceso5);
        tamaño_memoria=512;
        t_kernel=50;
        Proceso kernel = new Proceso(t_kernel,0);
        System.out.println("Elegir un algoritmo de ajuste (Primer ajuste: 1, Mejor ajuste: 2, "
                    + "Peor ajuste: 3)");
        
        algoritmo=sc.nextInt();
        
        switch(algoritmo){
           case 1:
               PrimerAjuste memoria = new PrimerAjuste(tamaño_memoria,t_kernel );
               memoria.Asigna_AreaLibrePMRA(kernel);
               for(int k=0;k<listaPre.size();k++){
                   memoria.Asigna_AreaLibrePMRA(listaPre.get(k));
                   memoria.imprimir();
                   System.out.println("Introduzca cualquier valor para ver el siguiente evento");
                   ss.nextLine();
               }
             memoria.extraer_proceso(2);
             memoria.imprimir();
             System.out.println("Introduzca cualquier valor para ver el siguiente evento");
             ss.nextLine();
             memoria.extraer_proceso(4);
             memoria.imprimir();
             System.out.println("Introduzca cualquier valor para ver el siguiente evento");
             ss.nextLine();
             memoria.extraer_proceso(5);
             memoria.imprimir();
             System.out.println("Introduzca cualquier valor para ver el siguiente evento");
             ss.nextLine();
             memoria.Asigna_AreaLibrePMRA(proceso6);
               break;
           case 2:
               MejorAjuste memoria1 = new MejorAjuste(tamaño_memoria,t_kernel );
               memoria1.Asigna_AreaLibreMA(kernel);
               for(int k=0;k<listaPre.size();k++){
                   memoria1.Asigna_AreaLibreMA(listaPre.get(k));
                   memoria1.imprimir();
                   System.out.println("Introduzca cualquier valor para ver el siguiente evento");
                   ss.nextLine();
               }
             memoria1.extraer_proceso(2);
             memoria1.imprimir();
             System.out.println("Introduzca cualquier valor para ver el siguiente evento");
             ss.nextLine();
             memoria1.extraer_proceso(4);
             memoria1.imprimir();
             System.out.println("Introduzca cualquier valor para ver el siguiente evento");
             ss.nextLine();
             memoria1.extraer_proceso(5);
             memoria1.imprimir();
             System.out.println("Introduzca cualquier valor para ver el siguiente evento");
             ss.nextLine();
             memoria1.Asigna_AreaLibreMA(proceso6);
               
               
               break;
               
           case 3:
               PeorAjuste memoria2 = new PeorAjuste(tamaño_memoria,t_kernel );
               memoria2.Asigna_AreaLibrePA(kernel);
               for(int k=0;k<listaPre.size();k++){
                   memoria2.Asigna_AreaLibrePA(listaPre.get(k));
                   memoria2.imprimir();
                   System.out.println("Introduzca cualquier valor para ver el siguiente evento");
                   ss.nextLine();
               }
             memoria2.extraer_proceso(2);
             memoria2.imprimir();
             System.out.println("Introduzca cualquier valor para ver el siguiente evento");
             ss.nextLine();
             memoria2.extraer_proceso(4);
             memoria2.imprimir();
             System.out.println("Introduzca cualquier valor para ver el siguiente evento");
             ss.nextLine();
             memoria2.extraer_proceso(5);
             memoria2.imprimir();
             System.out.println("Introduzca cualquier valor para ver el siguiente evento");
             ss.nextLine();
             memoria2.Asigna_AreaLibrePA(proceso6);
             
               break;
       }
       
        }
        else{
            int indice;
            int id_proceso=1;
            int tamaño_proceso;
            System.out.println("Elegir tamaño de memoria principal");
            tamaño_memoria=sc.nextInt();
            System.out.println("Elegir tamaño de kernel");
            t_kernel=sc.nextInt();
             System.out.println("Elegir un algoritmo de ajuste (Primer ajuste: 1, Mejor ajuste: 2, "
                    + "Peor ajuste: 3)");
        
            algoritmo=sc.nextInt();
            Proceso kernel = new Proceso(t_kernel,0);
            
            switch(algoritmo){
           case 1:
               PrimerAjuste memoria = new PrimerAjuste(tamaño_memoria,t_kernel );
               memoria.Asigna_AreaLibrePMRA(kernel);
                do{
                 System.out.println("¿Evento de entrada o de salida (1 o 0)?"); 
                 algoritmo=sc.nextInt();
                 if(algoritmo==0){
                     System.out.println("Introducir el indice del proceso que se va a extraer");
                     indice= sc.nextInt();
                     memoria.extraer_proceso(indice);
                     memoria.imprimir();
                 }
                 else{
                     System.out.println("Introducir el tamaño de proceso");
                     tamaño_proceso=sc.nextInt();
                     Proceso proceso= new Proceso(tamaño_proceso,id_proceso);
                     memoria.Asigna_AreaLibrePMRA(proceso);
                     memoria.imprimir();
                     id_proceso++;
                 }
                 System.out.println("¿Desea introducir otro evento?");
                 decision=sb.nextBoolean();
                 }while(decision);
               break;
           case 2:
               MejorAjuste memoria1 = new MejorAjuste(tamaño_memoria,t_kernel );
               memoria1.Asigna_AreaLibreMA(kernel);
                 do{
                 System.out.println("¿Evento de entrada o de salida (1 o 0)?"); 
                 algoritmo=sc.nextInt();
                 if(algoritmo==0){
                     System.out.println("Introducir el indice del proceso que se va a extraer");
                     indice= sc.nextInt();
                     memoria1.extraer_proceso(indice);
                     memoria1.imprimir();
                 }
                 else{
                     System.out.println("Introducir el tamaño de proceso");
                     tamaño_proceso=sc.nextInt();
                     Proceso proceso= new Proceso(tamaño_proceso,id_proceso);
                     memoria1.Asigna_AreaLibreMA(proceso);
                     memoria1.imprimir();
                     id_proceso++;
                 }
                 System.out.println("¿Desea introducir otro evento?");
                 decision=sb.nextBoolean();
                 }while(decision);
               
               break;
               
           case 3:
               PeorAjuste memoria2 = new PeorAjuste(tamaño_memoria,t_kernel );
               memoria2.Asigna_AreaLibrePA(kernel);
               do{
                 System.out.println("¿Evento de entrada o de salida (1 o 0)?"); 
                 algoritmo=sc.nextInt();
                 if(algoritmo==0){
                     System.out.println("Introducir el indice del proceso que se va a extraer");
                     indice= sc.nextInt();
                     memoria2.extraer_proceso(indice);
                     memoria2.imprimir();
                 }
                 else{
                     System.out.println("Introducir el tamaño de proceso");
                     tamaño_proceso=sc.nextInt();
                     Proceso proceso= new Proceso(tamaño_proceso,id_proceso);
                     memoria2.Asigna_AreaLibrePA(proceso);
                     memoria2.imprimir();
                     id_proceso++;
                 }
                 System.out.println("¿Desea introducir otro evento?");
                 decision=sb.nextBoolean();
                 }while(decision);
               break;
       }
           
        }
     
    }
}
