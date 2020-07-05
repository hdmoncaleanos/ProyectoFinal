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
		Integer estado_anterior = nodo.getEstado();
		Integer nuevo_estado = null;
		
		switch (estado_anterior) {
		case Constantes.ESTADO_SUCEPTIBLE:
			nuevo_estado = obtenerSiguienteEstadoSuceptible(nodo);
			break;
		case Constantes.ESTADO_INFECTADO:
			nuevo_estado = obtenerSiguienteEstadoInfectado(nodo);
			break;
		case Constantes.ESTADO_RECUPERADO:
			nuevo_estado = obtenerSiguienteEstadoRecuperado(nodo);
			break;
		case Constantes.ESTADO_LATENTE:
			nuevo_estado = obtenerSiguienteEstadoLatente(nodo);
			break;
		default:
			break;
		}
		
		if(nuevo_estado != estado_anterior){
			switch (nuevo_estado) {
			case Constantes.ESTADO_SUCEPTIBLE:
				observacionPaso.incrementarNuevosSuceptibles();
				observacionPaso.incrementarSuceptibles();
				break;
			case Constantes.ESTADO_INFECTADO:
				observacionPaso.incrementarNuevosInfectados();
				observacionPaso.incrementarInfectados();
				break;
			case Constantes.ESTADO_RECUPERADO:
				observacionPaso.incrementarNuevosRecuperados();
				observacionPaso.incrementarRecuperados();
				break;
			case Constantes.ESTADO_LATENTE:
				observacionPaso.incrementarNuevosLatentes();
				observacionPaso.incrementarLatentes();
				break;
			default:
				break;
			}
		}else{
			switch (nuevo_estado) {
			case Constantes.ESTADO_SUCEPTIBLE:
				observacionPaso.incrementarSuceptibles();
				break;
			case Constantes.ESTADO_INFECTADO:
				observacionPaso.incrementarInfectados();
				break;
			case Constantes.ESTADO_RECUPERADO:
				observacionPaso.incrementarRecuperados();
				break;
			case Constantes.ESTADO_LATENTE:
				observacionPaso.incrementarLatentes();
				break;
			default:
				break;
			}
		}
		
		nodo.setEstado( nuevo_estado );
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
