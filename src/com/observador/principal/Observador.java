package com.observador.principal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ambiente.principal.Ambiente;
import com.ambiente.principal.Nodo;
import com.simulador.utils.Utils;
import com.sistema.principal.Propiedades;

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
		
		observacionesPorAmbiente.add(ambiente.getObservacionAmbiente());
		
	}
	
	public void generarAnalisis() {
//		Utils.println(observacionesPorAmbiente);
		System.out.println("Iniciando generacion de analisis de datos de la simulacion.");
		//Creamos una nueva observaci�n para encapsular el c�lculo de los promedios
		//de todos los ambientes
		
		ObservacionAmbiente promedioSimulaciones = new ObservacionAmbiente();
		promedioSimulaciones.setCantidadNodos(Integer.valueOf(Propiedades.obtenerPropiedad("cantidad_nodos")));
		
		//Instanciamos cada paso en los cuales se almacenaran los valores promedio por paso i
		
		Integer cantidad_pasos = Integer.valueOf(Propiedades.obtenerPropiedad("cantidad_pasos"));
		Integer cantidad_ambientes = Integer.valueOf(Propiedades.obtenerPropiedad("cantidad_ambientes"));
		
		for (int i = 0; i < cantidad_pasos; i++) {
			promedioSimulaciones.getObservacionesPaso().add(new ObservacionPaso(i));
		}
		
		for (ObservacionAmbiente observacionAmbiente : observacionesPorAmbiente) {

			// System.out.println(observacion);

			List<ObservacionPaso> pasosAmbiente = observacionAmbiente.getObservacionesPaso();

			for (ObservacionPaso observacionPaso : pasosAmbiente) {
				
				
				int index = pasosAmbiente.indexOf(observacionPaso);
				ObservacionPaso observacionPasoPromedio = promedioSimulaciones.getObservacionesPaso().get(index);
				
				observacionPasoPromedio.incrementarSuceptibles(observacionPaso.getSuceptible());
				observacionPasoPromedio.incrementarLatentes(observacionPaso.getLatente());
				observacionPasoPromedio.incrementarInfectados(observacionPaso.getInfectado());
				observacionPasoPromedio.incrementarRecuperados(observacionPaso.getRecuperado());
				observacionPasoPromedio.incrementarNuevosSuceptibles(observacionPaso.getNuevosSuceptibles());
				observacionPasoPromedio.incrementarNuevosLatentes(observacionPaso.getNuevosLatentes());
				observacionPasoPromedio.incrementarNuevosInfectados(observacionPaso.getNuevosInfectados());
				observacionPasoPromedio.incrementarNuevosRecuperados(observacionPaso.getNuevosRecuperados());
				
			}

		}
		
		for (ObservacionPaso observacionPaso : promedioSimulaciones.getObservacionesPaso()) {
			observacionPaso.setSuceptible(observacionPaso.getSuceptible() / cantidad_ambientes);
			observacionPaso.setLatente(observacionPaso.getLatente() / cantidad_ambientes);
			observacionPaso.setInfectado(observacionPaso.getInfectado() / cantidad_ambientes);
			observacionPaso.setRecuperado(observacionPaso.getRecuperado() / cantidad_ambientes);
			observacionPaso.setNuevosSuceptibles(observacionPaso.getNuevosSuceptibles() / cantidad_ambientes);
			observacionPaso.setNuevosLatentes(observacionPaso.getNuevosLatentes() / cantidad_ambientes);
			observacionPaso.setNuevosInfectados(observacionPaso.getNuevosInfectados() / cantidad_ambientes);
			observacionPaso.setNuevosRecuperados(observacionPaso.getNuevosRecuperados() / cantidad_ambientes);
		}
		
		try (PrintWriter writer = new PrintWriter(new File("./log/test.csv"))) {

			StringBuilder sb = new StringBuilder();
			sb.append("Iteracion," 
						+ "Paso," 
						+ "Susceptibles," 
						+ "Latentes, " 
						+ "Infectados," 
						+ "Recuperados,"
						+ "Nuevo Susceptibles," 
						+ "Nuevos Latentes, " 
						+ "Nuevos Infectados," 
						+ "Nuevos Recuperados,"
						+ "\n");
			

			
				sb.append( "Promedio," 
							+ "0,"
							+ promedioSimulaciones.getCantidadNodos() 
							+ "," 
							+ "0, " 
							+ "0," 
							+ "0," 
							+ "0,"
							+ "0, " 
							+ "0,"
							+ "0"
							+ "\n");

				List<ObservacionPaso> pasosAmbiente = promedioSimulaciones.getObservacionesPaso();

				for (ObservacionPaso observacionPaso : pasosAmbiente) {
					sb.append("Promedio," 
							+ observacionPaso.getCSVString());
				}

			writer.write(sb.toString());

			System.out.println("Fin de generacion de analisis de datos de la simulacion.");

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}

}
