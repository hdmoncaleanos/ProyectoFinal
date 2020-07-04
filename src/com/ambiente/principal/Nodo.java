package com.ambiente.principal;

import com.sistema.constantes.Constantes;

public class Nodo {
	
	private String id_nodo;
	private String estado;
	private String tipo;
	
	public Nodo(String id_nodo, String tipo) {
		this.id_nodo = id_nodo;
		this.estado = Constantes.ESTADO_SUCEPTIBLE;
		this.tipo = tipo;
	}

	public String getId_nodo() {
		return id_nodo;
	}

	public void setId_nodo(String id_nodo) {
		this.id_nodo = id_nodo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
