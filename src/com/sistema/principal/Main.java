package com.sistema.principal;

import java.io.IOException;

import com.sistema.objetos.Sistema;

public class Main {
	
	public static void main(String[] args) throws IOException {
		
		Propiedades.cargarPropiedades();
		Sistema sistema = new Sistema();
		
		sistema.inicializar();

		long inicio = System.currentTimeMillis();
		System.out.println("Iniciando simulacion");
		sistema.simular();
         
        long fin = System.currentTimeMillis();
         
        double tiempo = (double) ((fin - inicio)/1000);

		System.out.println("Fin de la simulacion.");
        System.out.println("Tiempo simulacion: " + tiempo +" segundos");
        System.exit(0);
		
	}
	
}
