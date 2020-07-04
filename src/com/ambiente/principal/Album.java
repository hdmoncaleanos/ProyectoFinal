package com.ambiente.principal;


public class Album {
	
	private boolean[] laminas;

	public Album(Integer cantidad_laminas) {
		laminas = new boolean[cantidad_laminas];
	}

	public boolean[] getLaminas() {
		return laminas;
	}

	public void setLaminas(boolean[] laminas) {
		this.laminas = laminas;
	}	
	
	public boolean tieneLamina(Integer id_lamina){
//		System.out.println(id_lamina);
		return laminas[id_lamina-1];
	}
	
	public void agregarLamina(Integer id_lamina){
		laminas[id_lamina-1] = true;
	}

	public boolean estaLleno() {
		for(int i = 0; i < laminas.length; i++){
			if(!laminas[i]){
				return false;
			}
		}
		return true;
	}
	
}
