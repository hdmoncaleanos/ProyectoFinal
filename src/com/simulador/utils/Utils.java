package com.simulador.utils;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sistema.objetos.Resultado;

public class Utils {
	public static void println(Object o) {

		if (o == null) {
			System.out.println("null");
			return;
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		System.out.println(gson.toJson(o));
	}
	
	public static void print2D(String[][] matriz) { 
        for (String[] row : matriz){
        	for (String i : row) {
        		System.out.print(i + " ");
        	}
        	System.out.println();
        }
    }

	public static void imprimirResultados(List<Resultado> resultados) {
		System.out.println("p,\u0398(p)");
		for (Resultado resultado : resultados) {
			System.out.println(resultado.getP() + "," + resultado.getProbabilidad_percolacion());
		}
		
	}
	
}
