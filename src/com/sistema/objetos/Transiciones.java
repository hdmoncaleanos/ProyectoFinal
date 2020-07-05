package com.sistema.objetos;

import com.ambiente.principal.Nodo;
import com.observador.principal.ObservacionPaso;
import com.sistema.constantes.Constantes;


public class Transiciones {
	
	private static Transiciones instance;
	
	public static Transiciones getInstance() {
		
		if( instance == null ) {
			instance = new Transiciones();
		}
		
		return instance;
	}
	
	public void siguienteEstado(Nodo nodo, ObservacionPaso observacionPaso){
		Integer ultimoEstado = nodo.getEstado();
		Integer nuevoEstado = ultimoEstado;
		switch (ultimoEstado) {
		case Constantes.ESTADO_SUCEPTIBLE:
			nuevoEstado = obtenerSiguienteEstadoSuceptible(nodo);
			nodo.setEstado(nuevoEstado );
			break;
		case Constantes.ESTADO_INFECTADO:
			nuevoEstado = obtenerSiguienteEstadoInfectado(nodo);
			break;
		case Constantes.ESTADO_RECUPERADO:
			nuevoEstado = obtenerSiguienteEstadoRecuperado(nodo);
			break;
		case Constantes.ESTADO_LATENTE:
			nuevoEstado = obtenerSiguienteEstadoLatente(nodo);
			break;
		default:
			break;
		}
		
		nodo.setEstado( nuevoEstado );
	}

	private Integer obtenerSiguienteEstadoLatente(Nodo nodo) {
		// TODO Implementar funcion de probabilidad.
		double p = 0.3;
		double q = 0.3;
		
		double random = Math.random();
		if(random < p){
			return Constantes.ESTADO_LATENTE;
		} else if (random >= p && random < p + q){
			return Constantes.ESTADO_INFECTADO;
		} else{
			return Constantes.ESTADO_RECUPERADO;
		}
	}

	private Integer obtenerSiguienteEstadoRecuperado(Nodo nodo) {
		// TODO Implementar funcion de probabilidad.
		double p = 0.5;
		double random = Math.random();
		return random > p ? Constantes.ESTADO_RECUPERADO : Constantes.ESTADO_SUCEPTIBLE;
	}

	private Integer obtenerSiguienteEstadoInfectado(Nodo nodo) {
		// TODO Implementar funcion de probabilidad.
		double p = 0.5;
		double random = Math.random();
		return random > p ? Constantes.ESTADO_INFECTADO : Constantes.ESTADO_RECUPERADO;
	}

	private Integer obtenerSiguienteEstadoSuceptible(Nodo nodo) {
		// TODO Implementar funcion de probabilidad.
		double p = 0.5;
		double random = Math.random();
		return random > p ? Constantes.ESTADO_SUCEPTIBLE : Constantes.ESTADO_LATENTE;
	}
}
