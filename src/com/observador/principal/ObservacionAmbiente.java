package com.observador.principal;

import java.util.ArrayList;
import java.util.List;

public class ObservacionAmbiente {
	
	Integer cantidadNodos;
	List<ObservacionPaso> observacionesPaso;
	Integer totalInfecciones;
	Integer totalRecuperados;
	
	public ObservacionAmbiente() {
		this.cantidadNodos = 0;
		this.observacionesPaso = new ArrayList<>();
		this.totalInfecciones = 0;
		this.totalRecuperados = 0;
	}

	public Integer getCantidadNodos() {
		return cantidadNodos;
	}

	public void setCantidadNodos(Integer cantidadNodos) {
		this.cantidadNodos = cantidadNodos;
	}

	public List<ObservacionPaso> getObservacionesPaso() {
		return observacionesPaso;
	}

	public void setObservacionesPaso(List<ObservacionPaso> observacionesPaso) {
		this.observacionesPaso = observacionesPaso;
	}

	public Integer getTotalInfecciones() {
		return totalInfecciones;
	}

	public void setTotalInfecciones(Integer totalInfecciones) {
		this.totalInfecciones = totalInfecciones;
	}

	public Integer getTotalRecuperados() {
		return totalRecuperados;
	}

	public void setTotalRecuperados(Integer totalRecuperados) {
		this.totalRecuperados = totalRecuperados;
	}
	
}
