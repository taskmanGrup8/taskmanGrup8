package com.grup8.taskman.app.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

/**
 * Classe implementada perquè el paginador tingui un número acotat de pàgines en hores de tenir-les totes
 * @author Sergio Esteban Gutiérre
 * @version 1.0.0
 * @param <T> Indica que el pageRender pot fer-se amb qualsevol tipus de classe.
 */
public class PageRender<T> {
	
	// ATRIBUTS
	
	private String url;
	private Page<T> page; // Llistat que volem paginar
	private int totalPaginas; //Element que utilitzarem per fer els càlculs
	private int numElementosPorPagina; // Element que utilitzarem per fer els càlculs
	private int paginaActual;// Element que utilitzarem per realitzar els càlculs
	private List<PageItem> paginas; // Cadascuna de les pàgines del paginador
	
	// CONSTRUCTOR
	
	public PageRender(String url, Page<T> page) {
		
		// Assignem les dades als atrubuts
		this.url = url;
		this.page = page;
		this.paginas=new ArrayList<PageItem>(); // Inicialitzem la llista de pàgines
		numElementosPorPagina=page.getSize(); // Agafem el número d'elemens per pàgina del page rebut per paràmetre
		totalPaginas=page.getTotalPages(); // Agafem el número total de pagines del page rebut per paràmetre
		paginaActual=page.getNumber()+1; // Page comença per cero però nosaltres volem que el paginador comenci des de 1.
		
		// Càlculs
		int desde, hasta; // Variables que utilitzarem per coneixer el rang de pàgines que tenim. Desde és la pàgina
		// inicial del rang i hasta el número de elements que es mostren
		
		
		// Si el total de pàgines és menor que els elements per pàgina vol dir que la llista no és gaire gran,
		// per tant mostrarem totes les pàgines
		if(totalPaginas<=numElementosPorPagina) {
			
			// La primera pàgina serà la 1 i la darrera serà el total de pàgines
			desde=1;
			hasta=totalPaginas;
			
		}else {
			// Si la pàgina actual es troba per sota del número d'elements per pàgina / 2 mostrarem desde la pàgina 1
			// fins a la pàgina igual al número d'elements per pàgina. Per exemple si tenim un llistat de 500 elements
			// i mostrem 10 per pàgina, si la pàgina actual és menor o igual a 5 volem que mostri de l'1 al 10
			if(paginaActual<=numElementosPorPagina/2) {
				// La primera serà la 1 i la darrera el número d'elements per pàgina
				desde=1;
				hasta=numElementosPorPagina;
			
			// Si la pàgina actual es troba per sobre del total de pàgines menys el número de elements per pàgina/2 llavors
			// vol dir que ens trobem als darrers elements per tant són els que volem mostrar. Mostrarem des del total de
			// pagines - el número d'elements per pàgina fins la darrera pàgina. Seguint amb l'exemple anterior si la pàgina 
			// actual és la 45 serà igual al total de pàgines (50) - el número d'elements per pàgina/2 (5). En aquest cas
			// volem mostrar desde la 41 fins la 50.
			}else if(paginaActual>= totalPaginas - numElementosPorPagina/2){
				
				desde= totalPaginas-numElementosPorPagina+1;
				hasta=numElementosPorPagina;
			// En cas que estiguem entremig d'aquests dos rangs llavors mostrarem tantes pàgines com número d'elements
			// hi han per pàgina com estem fent amb tots els casos a excepció del primer. Aquest cop començarem per
			// pagina actual/2 i acabarem amb desde + numElementsPerPàgina-1. Continuant amb l'exemple si la pàgina
			// actual és la 7 llavors mostrem desde la pàgina 2 fins a la pàgina 11.
			}else {
				
				desde=paginaActual-numElementosPorPagina/2;
				hasta=numElementosPorPagina;
			}
		}
		
		// Creem totes lès pàgines des de l'inici fins el final del rang calculat
		for(int i=0; i<hasta;i++) {
			// El número de pàgina serà desde + i, per exemple si és la primera desde serà igual a 1 i "i" serà igual a o
			// per tant la primera serà la 1. Al segon parametrè preguntem si la pàgina actual correspon al número de pàgina
			// indicat.
			paginas.add(new PageItem(desde + i, paginaActual==desde+i));
		}
	}
	
	// GETTERS
	
	public String getUrl() {
		return url;
	}
	public Page<T> getPage() {
		return page;
	}
	public int getTotalPaginas() {
		return totalPaginas;
	}
	public int getNumElementosPorPagina() {
		return numElementosPorPagina;
	}
	public int getPaginaActual() {
		return paginaActual;
	}
	public List<PageItem> getPaginas() {
		return paginas;
	}
	
	// Mètodes
	
	/**
	 * 	
	 * @return Retorna si la és la primera pàgina
	 */
	public boolean isFirst() {
		
		return page.isFirst();
	}
	
	/**
	 * 
	 * @return Retorna si la pàgina és la darrera
	 */
	public boolean isLast() {
		
		return page.isLast();
	}
	
	/**
	 * 
	 * @return Retorna si la pàgina té una següent
	 */
	public boolean isHasNext() {
		
		return page.hasNext();
	}
	
	/**
	 * 
	 * @return Retorna si la pàgina té una anterior
	 */
	public boolean isHasPrevious() {
		
		return page.hasPrevious();
	}
	

}
