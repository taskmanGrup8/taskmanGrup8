package com.grup8.taskman.app.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Classe per utilitats varies.
 * @author Sergio Esteban Gutiérrez
 * @Version 1.0.0
 *
 */
public class Utilidades {
	
	/**
	 * Mètode que combina dues llistes sense repetir cap element.
	 * @param <T> Indica que pot ser de qualsevol classe
	 * @param listaA Primera llista de les dues a combinar
	 * @param listaB Segona llista de les dues a combinar
	 * @return Retorna la combinació de la listaA i listaB sense repetir elements
	 */
	public static <T> List<T> combinarListasSinRepeticion(List<T> listaA, List<T> listaB){
		/**
		 * NOTA IMPORTANT: És necessari fer override dels mètodes hashCode i equals de les
		 * classes que voldrem posar a les llistes perquè quan facin les combinacions no
		 * repeteixi dades ja que si tenim dues instancies amb els mateixos valors dels atributs,
		 * com són diferents equals donarà fals i per tant apareixeran tots dos a la combinació.
		 */
		Set<T> combinacion=new HashSet<>(listaA); // Creem el HashSet de la llista A
		combinacion.addAll(listaB); // Afegim la llista B
		return new ArrayList<>(combinacion); // Retornem un nou ArrayList amb la combinació.		
		
	}
	
	/**
	 * Mètode que combina una llista de llistes en una sola sense repetir cap element
	 * @param <T> Indica que pot ser de qualsevol clase
	 * @param listas Rep com paràmetre una llista de llistes de qualsevol classe.
	 * @return Retorna la combinació de totes les llistes sense repetició.
	 */
	public static <T> List<T> combinarVariasListasSinRepeticion(List<List<T>> listas){
		
		List<T> resultado=new ArrayList<>();
		
		// Fem un foreach cridant a combinarListasSinRepeticion i passem com paràmetres el resultat actual
		// i el nou element de listas que és una llista. Assignem el resultat a la variable resultado.
		for(List<T> lista: listas) {
			
			resultado=combinarListasSinRepeticion(resultado, lista);			
			
		}
		
		return resultado; // Retornem la llista combinada.
		
	}
	
	public static long restarFechas(Date fecha1, Date fecha2) {
		
		long diferencia=fecha1.getTime()-fecha2.getTime();
		return diferencia;
	}
	
	public static long restarFechasEnMinutos(Date fecha1, Date fecha2) {
				
		long minutos=TimeUnit.MILLISECONDS.toMinutes(restarFechas(fecha1, fecha2));
		return minutos;
	}
	
	public static long restarFechasEnDias(Date fecha1, Date fecha2) {
		
		long dias=TimeUnit.MILLISECONDS.toDays(restarFechas(fecha1, fecha2));
		return dias;
	}
	
	

}
