package com.sistema.objetos;

import com.ambiente.principal.Nodo;
import com.observador.principal.ObservacionPaso;
import com.sistema.constantes.Constantes;
import com.sistema.principal.Propiedades;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;


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

		double param_deteccion_antivirus = 0.1;
		double media_clicks = 1;
		double vistas_correo = 1;

		try {
			param_deteccion_antivirus = Double.parseDouble(Propiedades.obtenerPropiedad("porcentaje_protecciojn_antivirus"));
			media_clicks = Double.parseDouble(Propiedades.obtenerPropiedad("media_clicks"));
			vistas_correo = Double.parseDouble(Propiedades.obtenerPropiedad("visitas_a_correo"));
		}catch (Exception e){
			throw new RuntimeException("Propiedades param_deteccion_antivirus, media_clicks o vistas_correo no definidas correctamente: " + e);
		}

		/*Acorde a http://www.csis.pace.edu/~ctappert/dps/2013EISIC/EISIC2013/5062a038.pdf
		probabilidad de contagio por email*/

		p = (1 - param_deteccion_antivirus) * ((double) (media_clicks / vistas_correo));

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

		/*Acorde a https://blog.icorps.com/spam-malware-email-security
		el numero de correos maliciosos oscila entre 1 cada 244 y 1 cada 512 miembros de una organizacion
		acorde al numero de nodos de la organizacion
		* */

		int n_nodos = Integer.getInteger(Propiedades.obtenerPropiedad("cantidad_nodos"));

		double p = 0.5;
		double param_sucep = nodo.getSucseptibilidad();
		double param_mails_recibidos = 1;

		double carrier = param_sucep * param_mails_recibidos;

		if(n_nodos <= 250)
			p = Double.min((1/376) * carrier, 1d);
		else if(n_nodos <= 500)
			p = Double.min((1/306) * carrier, 1d);
		else if(n_nodos <= 1000)
			p = Double.min((1/425) * carrier, 1d);
		else if(n_nodos <= 1500)
			p = Double.min((1/244) * carrier, 1d);
		else if(n_nodos <= 2500)
			p = Double.min((1/355) * carrier, 1d);
		else
			p = Double.min((1/512) * carrier, 1d);

		double random = Math.random();
		return random > p ? Constantes.ESTADO_SUCEPTIBLE : Constantes.ESTADO_LATENTE;
	}
}
