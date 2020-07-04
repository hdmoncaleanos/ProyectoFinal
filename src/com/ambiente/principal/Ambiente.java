package com.ambiente.principal;

import java.util.HashMap;
import java.util.Map;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import com.simulador.utils.Utils;

public class Ambiente {
	
	private Integer cantidad_nodos;
	private Map<String, Nodo> nodos;
	private Graph red;
	private Integer pasos = 0;
	
	public Ambiente(Integer id_ambiente, Integer cantidad_nodos){
		this.cantidad_nodos = cantidad_nodos;
		
		nodos = new HashMap<String, Nodo>();
		
		for(int i = 0; i < cantidad_nodos; i++){
			nodos.put(i + "", new Nodo(i + "", ""));
		}
		red = new SingleGraph("Red");
	    Generator gen = new RandomGenerator(2);
	    gen.addSink(red);
	    gen.begin();
	    
	    for(int i=0; i<cantidad_nodos - 3; i++)
	        gen.nextEvents();
	    gen.end();
	    
	    int nodes = red.getNodeCount();
		int edgesn = red.getEdgeCount();
		System.out.println("Ambiente n: " + nodes + ", M: " + edgesn + " n/M: " + (float) edgesn / (float) nodes );
		if(id_ambiente.equals(1)){
			red.display();
		}
	}
	
	private void siguientePaso(){
		Iterable<? extends Node> cadaNodo = red.getEachNode();
		//Recorremos cada nodo de la red.
		for (Node node : cadaNodo) {
			
			Iterable<Edge> cadaVecino = node.getEachEdge();
			//Recorremos cada vecino del nodo actual.
			for (Edge edge : cadaVecino) {
				
			}
			
			//TODO hacer la logica de cambio de estado
		}
		pasos++;
	}

	public void ejecutar(Integer cantidad_pasos){
		for(int i = 0; i<cantidad_pasos; i++){
			siguientePaso();
		}
		Utils.println(nodos);
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
	
}
