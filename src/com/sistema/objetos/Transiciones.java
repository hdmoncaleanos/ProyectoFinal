package com.sistema.objetos;

import com.ambiente.principal.Nodo;
import com.observador.principal.ObservacionPaso;
import com.sistema.constantes.Constantes;
import com.sistema.principal.Propiedades;


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

		p = (1 - param_deteccion_antivirus) * ((media_clicks / vistas_correo));

		double q = 1 - (nodo.getSuceptibilidad()/10d + p);

		double norm_prob = Double.min(p + q, 1d);
		
		double random = Math.random();
		if(random < p){
			return Constantes.ESTADO_INFECTADO;
		} else if (random >= p && random < norm_prob){
			return Constantes.ESTADO_RECUPERADO;
		} else{
			return Constantes.ESTADO_LATENTE;
		}
	}

	private Integer obtenerSiguienteEstadoRecuperado(Nodo nodo) {
		double p = 0.5;

		try {
			p = Double.parseDouble(Propiedades.obtenerPropiedad("tasa_deblilitamiento"));
		}
		catch (Exception e){
			throw new RuntimeException("Propiedad tasa_deblilitamiento no definida correctamente: " + e);
		}

		double random = Math.random();
		return random > p ? Constantes.ESTADO_RECUPERADO : Constantes.ESTADO_SUCEPTIBLE;
	}

	private Integer obtenerSiguienteEstadoInfectado(Nodo nodo) {
		double p = 0.5;

		try {
			p = Double.parseDouble(Propiedades.obtenerPropiedad("tasa_recuperacion"));
		}catch (Exception e){
			throw new RuntimeException("Propiedad tasa_recuperacion no definida correctamente: " + e);
		}

		double random = Math.random();
		return random > p ? Constantes.ESTADO_INFECTADO : Constantes.ESTADO_RECUPERADO;
	}

	private Integer obtenerSiguienteEstadoSuceptible(Nodo nodo) {

		/*Acorde a https://blog.icorps.com/spam-malware-email-security
		el numero de correos maliciosos oscila entre 1 cada 244 y 1 cada 512 miembros de una organizacion
		acorde al numero de nodos de la organizacion
		* */

		int numeroDeNodosEnRed = Integer.parseInt(Propiedades.obtenerPropiedad("cantidad_nodos"));

		double p = 0.5;
		double param_sucep = nodo.getSuceptibilidad();
		double param_mails_recibidos = 1;

		param_mails_recibidos = Double.parseDouble(Propiedades.obtenerPropiedad("numero_correos_recibidos"));
		double tasa_infeccion = Double.parseDouble(Propiedades.obtenerPropiedad("tasa_infeccion"));
		double carrier = param_sucep * param_mails_recibidos / 10d; //normalizacion de suceptibilidad

		if(numeroDeNodosEnRed <= 250)
			p = Double.min((1d/376) * carrier, 1d);
		else if(numeroDeNodosEnRed <= 500)
			p = Double.min((1d/306) * carrier, 1d);
		else if(numeroDeNodosEnRed <= 1000)
			p = Double.min((1d/425) * carrier, 1d);
		else if(numeroDeNodosEnRed <= 1500)
			p = Double.min((1d/244) * carrier, 1d);
		else if(numeroDeNodosEnRed <= 2500)
			p = Double.min((1d/355) * carrier, 1d);
		else
			p = Double.min((1d/512) * carrier, 1d);
		
		
		
		double random = Math.random();
		int estadoNuevo = random > p ? Constantes.ESTADO_SUCEPTIBLE : Constantes.ESTADO_LATENTE;
		
		if(nodo.getVecinos_infectados() > 0 && estadoNuevo != Constantes.ESTADO_LATENTE){
			
			for (int i = 0; i < nodo.getVecinos_infectados(); i++) {
				double r = Math.random();
				if(r<tasa_infeccion){
					return Constantes.ESTADO_LATENTE;
				}
			}
		}
		
		return estadoNuevo;
	}
}
