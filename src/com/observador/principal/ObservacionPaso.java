package com.observador.principal;

public class ObservacionPaso {
	private Integer paso;
	private Integer suceptible;
	private Integer latente;
	private Integer infectado;
	private Integer recuperado;
	private Integer nuevosInfectados;
	private Integer nuevosRecuperados;
	private Integer nuevosSuceptibles;
	private Integer nuevosLatentes;
	
	//Se inicializa todos los contadores en 0 correspondiente a la observacion del paso numero #paso#
	public ObservacionPaso(Integer paso){
		super();
		this.paso = paso;
		this.suceptible = 0;
		this.latente = 0;
		this.infectado = 0;
		this.recuperado = 0;
		this.nuevosInfectados = 0;
		this.nuevosRecuperados = 0;
		this.nuevosLatentes = 0;
		this.nuevosSuceptibles = 0;
	}
	
	public Integer getPaso() {
		return paso;
	}

	public void setPaso(Integer paso) {
		this.paso = paso;
	}

	public Integer getSuceptible() {
		return suceptible;
	}
	public void setSuceptible(Integer suceptible) {
		this.suceptible = suceptible;
	}
	public Integer getLatente() {
		return latente;
	}
	public void setLatente(Integer latente) {
		this.latente = latente;
	}
	public Integer getInfectado() {
		return infectado;
	}
	public void setInfectado(Integer infectado) {
		this.infectado = infectado;
	}
	public Integer getRecuperado() {
		return recuperado;
	}
	public void setRecuperado(Integer recuperado) {
		this.recuperado = recuperado;
	}
	public Integer getNuevosInfectados() {
		return nuevosInfectados;
	}
	public void setNuevosInfectados(Integer nuevosInfectados) {
		this.nuevosInfectados = nuevosInfectados;
	}
	public Integer getNuevosRecuperados() {
		return nuevosRecuperados;
	}
	public void setNuevosRecuperados(Integer nuevosRecuperados) {
		this.nuevosRecuperados = nuevosRecuperados;
	}

	public Integer getNuevosSuceptibles() {
		return nuevosSuceptibles;
	}
	public void setNuevosSuceptibles(Integer nuevosSuceptibles) {
		this.nuevosSuceptibles = nuevosSuceptibles;
	}
	public Integer getNuevosLatentes() {
		return nuevosLatentes;
	}
	public void setNuevosLatentes(Integer nuevosLatentes) {
		this.nuevosLatentes = nuevosLatentes;
	}
	public void incrementarNuevosInfectados(){
		nuevosInfectados++;
	}

	public void incrementarNuevosSuceptibles(){
		nuevosSuceptibles++;
	}

	public void incrementarNuevosLatentes(){
		nuevosLatentes++;
	}

	public void incrementarNuevosRecuperados(){
		nuevosRecuperados++;
	}
	public void incrementarInfectados(){
		infectado++;
	}

	public void incrementarSuceptibles(){
		suceptible++;
	}

	public void incrementarLatentes(){
		latente++;
	}

	public void incrementarRecuperados(){
		recuperado++;
	}
	
}