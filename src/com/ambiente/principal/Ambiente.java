package com.ambiente.principal;

import java.util.HashMap;
import java.util.Map;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import com.observador.principal.ObservacionAmbiente;
import com.observador.principal.ObservacionPaso;
import com.sistema.constantes.Constantes;
import com.sistema.objetos.Transiciones;
import com.sistema.principal.Propiedades;

public class Ambiente {
	
	private Integer cantidad_nodos;
	private Map<String, Nodo> nodos;
	private Graph red;
	private Integer pasos = 0;
	private ObservacionAmbiente observacionAmbiente = new ObservacionAmbiente();
	//Vulnerabilidades en las empresas
	private Boolean actualizaParches;
	private Integer pasosHastaActualizacion;
	private Double pesoActualizacion;
	
	private Boolean escaneaVulnerabilidades;
	private Boolean conoceBrechas;
	
	public Ambiente(Integer id_ambiente, Integer cantidad_nodos, Integer grado_nodos){
		this.cantidad_nodos = cantidad_nodos;
		
		nodos = new HashMap<String, Nodo>();
		
		for(int i = 0; i < cantidad_nodos; i++){
			nodos.put(i + "", new Nodo(i + "", ""));
		}
		red = new SingleGraph("Red");
	    Generator gen = new RandomGenerator(grado_nodos);
	    gen.addSink(red);
	    gen.begin();
	    
	    while (red.getNodeCount()< cantidad_nodos) {
	    	gen.nextEvents();
		}
	    gen.end();
	    
	    int nodes = red.getNodeCount();
		int edgesn = red.getEdgeCount();
		System.out.println("Ambiente n: " + nodes + ", M: " + edgesn + " n/M: " + (float) edgesn / (float) nodes );
		if(id_ambiente.equals(1)){
			red.display();
		}
		observacionAmbiente.setCantidadNodos(cantidad_nodos);
		
		double random = Math.random();
		actualizaParches = random <= Double.parseDouble(Propiedades.obtenerPropiedad("porcentaje_actualiza_parches"));
		pasosHastaActualizacion = Integer.parseInt(Propiedades.obtenerPropiedad("pasos_hasta_actualizacion"));
		pesoActualizacion = Double.parseDouble(Propiedades.obtenerPropiedad("peso_actualizacion"));
		
	}
	
	private void siguientePaso(Integer pasoActual){
		Iterable<? extends Node> cadaNodo = red.getEachNode();
		ObservacionPaso observacionPaso = new ObservacionPaso(pasos);
		//Recorremos cada nodo de la red.
		for (Node node : cadaNodo) {
//			System.out.println("######################");
			
			Nodo nodoActual = nodos.get(node.getId());
			nodoActual.setVecinos_infectados(0);
			Iterable<Edge> cadaVecino = node.getEachEdge();
			//Recorremos cada vecino del nodo actual.
			for (Edge edge : cadaVecino) {
				Nodo nodoVecino = nodos.get(edge.getNode0().getId());
				if(nodoVecino.getEstado() == Constantes.ESTADO_INFECTADO){
					nodoActual.setVecinos_infectados(nodoActual.getVecinos_infectados() + 1);
				}
			}
			
			//Las acciones empresariales afectan la susceptibilidad de un nodo
			if( pasoActual == pasosHastaActualizacion && actualizaParches ) {
				nodoActual.setSuceptibilidad(nodoActual.getSuceptibilidad() * ( 1 - pesoActualizacion) );
			}
			
			
			Transiciones.getInstance().siguienteEstado(nodoActual, observacionPaso);
			
		}
		
		pasos++;
		observacionAmbiente.aumentarTotalInfecciones(observacionPaso.getNuevosInfectados());
		observacionAmbiente.aumentarTotalRecuperados(observacionPaso.getNuevosRecuperados());
		observacionAmbiente.getObservacionesPaso().add(observacionPaso);
	}

	public void ejecutar(Integer cantidad_pasos){
		for(int i = 0; i<cantidad_pasos; i++){
			siguientePaso(i);
		}
		//Utils.println(nodos);
	}

	public Integer getCantidad_nodos() {
		return cantidad_nodos;
	}

	public void setCantidad_nodos(Integer cantidad_nodos) {
		this.cantidad_nodos = cantidad_nodos;
	}

	public Map<String, Nodo> getNodos() {
		return nodos;
	}

	public void setNodos(Map<String, Nodo> nodos) {
		this.nodos = nodos;
	}

	public Graph getRed() {
		return red;
	}

	public void setRed(Graph red) {
		this.red = red;
	}

	public Integer getPasos() {
		return pasos;
	}

	public void setPasos(Integer pasos) {
		this.pasos = pasos;
	}

	public ObservacionAmbiente getObservacionAmbiente() {
		return observacionAmbiente;
	}

	public void setObservacionAmbiente(ObservacionAmbiente observacionAmbiente) {
		this.observacionAmbiente = observacionAmbiente;
	}
	
}
