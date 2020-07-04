package com.sistema.objetos;

public class Resultado {
	
	private float p;
	private float probabilidad_percolacion;
	
	public Resultado(float p, float probabilidad_percolacion) {
		super();
		this.p = p;
		this.probabilidad_percolacion = probabilidad_percolacion;
	}
	
	public float getP() {
		return p;
	}
	public void setP(float p) {
		this.p = p;
	}
	public float getProbabilidad_percolacion() {
		return probabilidad_percolacion;
	}
	public void setProbabilidad_percolacion(float probabilidad_percolacion) {
		this.probabilidad_percolacion = probabilidad_percolacion;
	}
}
