package com.ambiente.principal;

import java.util.ArrayList;
import java.util.List;

import com.sistema.constantes.Constantes;

public class Nodo {
	
	private String id_nodo;
	private Integer estado;
	private String tipo;
	private Integer vecinos_infectados;
	private List<Integer> historial_estados;
	
	public Nodo(String id_nodo, String tipo) {
		this.id_nodo = id_nodo;
		this.estado = Constantes.ESTADO_SUCEPTIBLE;
		this.tipo = tipo;
		this.historial_estados = new ArrayList<Integer>();
	}

	public String getId_nodo() {
		return id_nodo;
	}

	public void setId_nodo(String id_nodo) {
		this.id_nodo = id_nodo;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.historial_estados.add(estado);
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getVecinos_infectados() {
		return vecinos_infectados;
	}

	public void setVecinos_infectados(Integer vecinos_infectados) {
		this.vecinos_infectados = vecinos_infectados;
	}

	public List<Integer> getHistorial_estados() {
		return historial_estados;
	}

	public void setHistorial_estados(List<Integer> historial_estados) {
		this.historial_estados = historial_estados;
	}
	
}
