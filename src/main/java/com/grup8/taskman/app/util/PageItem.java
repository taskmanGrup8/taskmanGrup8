package com.grup8.taskman.app.util;

/**
 * classe que és una representació de cadascuna de les pàgines del paginador
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 */
public class PageItem {
	
	// ATRIBUTS
	
	private int numero; // Número de pàgina
	private boolean actual; // Indiquem si és la pàgina actual o no
	
	// CONSTRUCTOR
	public PageItem(int numero, boolean actual) {
		
		this.numero = numero;
		this.actual = actual;
	}
	
	// GETTERS
	
	public int getNumero() {
		return numero;
	}
	public boolean isActual() {
		return actual;
	}
	
	
	
	

}
