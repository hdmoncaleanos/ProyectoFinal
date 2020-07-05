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

public class Ambiente {
	
	private Integer cantidad_nodos;
	private Map<String, Nodo> nodos;
	private Graph red;
	private Integer pasos = 0;
	private ObservacionAmbiente observacionAmbiente = new ObservacionAmbiente();
	
	public Ambiente(Integer id_ambiente, Integer cantidad_nodos){
		this.cantidad_nodos = cantidad_nodos;
		
		nodos = new HashMap<String, Nodo>();
		
		for(int i = 0; i < cantidad_nodos; i++){
			nodos.put(i + "", new Nodo(i + "", ""));
		}
		red = new SingleGraph("Red");
	    Generator gen = new RandomGenerator(4);
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
	}
	
	private void siguientePaso(){
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
			
			Transiciones.getInstance().siguienteEstado(nodoActual, observacionPaso);
			
		}
		
		pasos++;
		observacionAmbiente.aumentarTotalInfecciones(observacionPaso.getNuevosInfectados());
		observacionAmbiente.aumentarTotalRecuperados(observacionPaso.getNuevosRecuperados());
		observacionAmbiente.getObservacionesPaso().add(observacionPaso);
	}

	public void ejecutar(Integer cantidad_pasos){
		for(int i = 0; i<cantidad_pasos; i++){
			siguientePaso();
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
