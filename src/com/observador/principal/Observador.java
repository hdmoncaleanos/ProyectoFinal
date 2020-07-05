package com.observador.principal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ambiente.principal.Ambiente;
import com.ambiente.principal.Nodo;
import com.sistema.constantes.Constantes;

public class Observador {
	
	private static Observador observador;
	private List<ObservacionAmbiente> observacionesPorAmbiente; 
	
	private Observador() {
		this.observacionesPorAmbiente = new ArrayList<>();
	}
	
	public static Observador getInstance() {
		
		if( observador == null ) {
			observador = new Observador();
		}
		
		return observador;
	}

	public List<ObservacionAmbiente> getAlbumesLlenosPorAmbiente() {
		return observacionesPorAmbiente;
	}

	public void setAlbumesLlenosPorAmbiente(List<ObservacionAmbiente> albumesLlenosPorAmbiente) {
		this.observacionesPorAmbiente = albumesLlenosPorAmbiente;
	}

	public void getInfoAmbiente(Ambiente ambiente) {
		
		Map<String, Nodo> nodosAmbiente = ambiente.getNodos();
		ObservacionAmbiente observacion = new ObservacionAmbiente();
		
		for (Nodo nodo : nodosAmbiente.values()) {

			observacion.cantidadNodos = ambiente.getCantidad_nodos();
			nodo.getEstado();
			
			
			
		}
		
		observacionesPorAmbiente.add(observacion);
		
	}
	
	public void generarAnalisis() {
		
//		 try (PrintWriter writer = new PrintWriter(new File("./log/test.csv"))) {
//
//		      StringBuilder sb = new StringBuilder();
//		      sb.append("Iteracion,"
//		      		+ "Cantidad estudiantes,"
//		      		+ "Cantidad de amistades, "
//		      		+ "Albumes llenos ambiente,"
//		      		+ "Total compradas en ambiente,"
//		      		+ "Total compradas para albumes llenos,"
//		      		+ "Promedio compradas por estudiante,"
//		      		+ "Promedio compradas para albumes llenos,"
//		      		+ "Promedio intercambios ambiente,"
//		      		+ "Promedio intercambio albumes llenos"
//		      		+"\n");
//		      
//		  	
//				for (Observacion observacion : observacionesPorAmbiente) {
//					System.out.println(observacion);
//					sb.append( observacionesPorAmbiente.indexOf(observacion)  );
//					
//				}
//		      
//		      writer.write(sb.toString());
//
//		      System.out.println("done!");
//
//		    } catch (FileNotFoundException e) {
//		      System.out.println(e.getMessage());
//		    }

	}

}
