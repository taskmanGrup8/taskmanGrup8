package com.grup8.taskman.app.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utilidades {
	
	public static <T> List<T> combinarListasSinRepeticion(List<T> listaA, List<T> listaB){
		
		Set<T> combinacion=new HashSet<>(listaA); 
		combinacion.addAll(listaB);
		return new ArrayList<>(combinacion);		
		
	}
	
	public static <T> List<T> combinarVariasListasSinRepeticion(List<List<T>> listas){
		
		List<T> resultado=new ArrayList<>();
		
		for(List<T> lista: listas) {
			
			resultado=combinarListasSinRepeticion(resultado, lista);			
			
		}
		
		return resultado;
		
	}

}
