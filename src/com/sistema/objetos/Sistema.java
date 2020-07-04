package com.sistema.objetos;

import org.apache.commons.lang3.StringUtils;

import com.ambiente.principal.Ambiente;
import com.observador.principal.Observador;
import com.sistema.principal.Propiedades;

public class Sistema {
	
	private Integer n;
	private int cantidad_ambientes;
	private int cantidad_pasos;
	private Observador observador;
	private Integer cantidad_nodos;

	public void inicializar(){
		
		String propiedad_cantidad = Propiedades.obtenerPropiedad("cantidad_ambientes");
		if(propiedad_cantidad == null || !StringUtils.isNumeric(propiedad_cantidad)){
			throw new RuntimeException("No se encuentra configurada correctamente la propiedad: propiedad_cantidad");
		}
		this.cantidad_ambientes = Integer.parseInt(propiedad_cantidad);

		String propiedad_cantidad_pasos = Propiedades.obtenerPropiedad("cantidad_pasos");
		if(propiedad_cantidad == null || !StringUtils.isNumeric(propiedad_cantidad)){
			throw new RuntimeException("No se encuentra configurada correctamente la propiedad: cantidad_pasos");
		}
		this.cantidad_pasos = Integer.parseInt(propiedad_cantidad_pasos);

		String propiedad_cantidad_nodos = Propiedades.obtenerPropiedad("cantidad_nodos");
		if(propiedad_cantidad == null || !StringUtils.isNumeric(propiedad_cantidad)){
			throw new RuntimeException("No se encuentra configurada correctamente la propiedad: cantidad_nodos");
		}
		this.cantidad_nodos = Integer.parseInt(propiedad_cantidad_nodos);

	}
	
	public void simular(){
		observador = Observador.getInstance();
		
		for (int i = 1; i<= cantidad_ambientes; i++){
			System.out.println("\n########## Inicio ejecucion para ambiente " + i + " ##########\n");
			Ambiente ambiente = new Ambiente(i, cantidad_nodos);
			ambiente.ejecutar( cantidad_pasos );
			observador.getInfoAmbiente(ambiente);
			System.out.println("\n########## Fin de ejecucion para ambiente " + i + " ##########\n");
			
		}
		
		observador.generarAnalisis();
		
	}
}
