package com.observador.principal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ambiente.principal.Ambiente;
import com.ambiente.principal.Nodo;

public class Observador {
	
	private static Observador observador;
	private List<Observacion> observacionesPorAmbiente; 
	
	private Observador() {
		this.observacionesPorAmbiente = new ArrayList<>();
	}
	
	public static Observador getInstance() {
		
		if( observador == null ) {
			observador = new Observador();
		}
		
		return observador;
	}

	public List<Observacion> getAlbumesLlenosPorAmbiente() {
		return observacionesPorAmbiente;
	}

	public void setAlbumesLlenosPorAmbiente(List<Observacion> albumesLlenosPorAmbiente) {
		this.observacionesPorAmbiente = albumesLlenosPorAmbiente;
	}

	public void getInfoAmbiente(Ambiente ambiente) {
		
		Map<String, Nodo> estudiantesAmbiente = ambiente.getNodos();
		Observacion observacion = new Observacion();
		
		for (Nodo estudiante : estudiantesAmbiente.values()) {

			observacion.cantidadNodos = ambiente.getCantidad_nodos();
			estudiante.getEstado();

//			if(estudiante.tieneAlbumLleno()) {
//				observacion.albumesLlenosAmbiente++;
//				observacion.laminasCompradasAlbumesLlenos += estudiante.getLaminas_compradas();
//				observacion.cantidadIntercambiosAlbumesLlenos += estudiante.getLaminas_cambiadas();
//			}
			
//			observacion.totalLaminasCompradasAmbiente += estudiante.getLaminas_compradas();
//			observacion.cantidadIntercambiosAmbiente += estudiante.getLaminas_cambiadas();
			
		}
		
		observacion.cantidadIntercambiosAmbiente = observacion.cantidadIntercambiosAmbiente / 2; 
		
		observacion.cantidadAmistades = ambiente.getRed().getEdgeCount();
		
		observacionesPorAmbiente.add(observacion);
		
	}
	
	public void generarAnalisis() {
		
		 try (PrintWriter writer = new PrintWriter(new File("./log/test.csv"))) {

		      StringBuilder sb = new StringBuilder();
		      sb.append("Iteracion,"
		      		+ "Cantidad estudiantes,"
		      		+ "Cantidad de amistades, "
		      		+ "Albumes llenos ambiente,"
		      		+ "Total compradas en ambiente,"
		      		+ "Total compradas para albumes llenos,"
		      		+ "Promedio compradas por estudiante,"
		      		+ "Promedio compradas para albumes llenos,"
		      		+ "Promedio intercambios ambiente,"
		      		+ "Promedio intercambio albumes llenos"
		      		+"\n");
		      
		  	
				for (Observacion observacion : observacionesPorAmbiente) {
					System.out.println(observacion);
					sb.append( observacionesPorAmbiente.indexOf(observacion)  + 
							"," + observacion.getCSVLine());
					
				}
		      
		      writer.write(sb.toString());

		      System.out.println("done!");

		    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }

	}

}
