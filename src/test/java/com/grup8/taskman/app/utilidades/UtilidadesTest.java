package com.grup8.taskman.app.utilidades;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.grup8.taskman.app.domain.departaments.Departament;
import com.grup8.taskman.app.util.Utilidades;

class UtilidadesTest {

	@Test
	void combinarListasSinRepeticionTest() {
		
		// En primer lugar creo dos listas vacías, en este caso probaré con Departament.
		 
		List<Departament>lista1=new ArrayList<Departament>();
		List<Departament>lista2=new ArrayList<Departament>();
		
		// Ahora creo dos instancias de Departament
		
		Departament departamento1=new Departament();
		departamento1.setCodigo("INF");
		departamento1.setId(new Long(1));
		departamento1.setNombre("Informática");		
	
		
		Departament departamento2=new Departament();
		departamento2.setCodigo("CON");
		departamento2.setId(new Long(1));
		departamento2.setNombre("Contabilidad");
		
		Departament departamento3=new Departament();
		departamento3.setCodigo("INF");
		departamento3.setId(new Long(1));
		departamento3.setNombre("Informática");	
		
		// Ahora añado la primera instancia a la primera lista y todas a la segunda
		
		lista1.add(departamento1);
		lista2.add(departamento2);
		lista2.add(departamento3);
		
		// En este caso la combinación de las dos listas debería resultar una lista de dos departamentos
		
		List<Departament> combinacion=Utilidades.combinarListasSinRepeticion(lista1, lista2);		
		assertEquals(2, combinacion.size());
		
		
	}
	
	@Test
	public void combinarVariasListasSinRepeticionTest() {
		
		// En primer lugar creo tres listas vacías, en este caso probaré con Departament.
		 
		List<Departament>lista1=new ArrayList<Departament>();
		List<Departament>lista2=new ArrayList<Departament>();
		List<Departament>lista3=new ArrayList<Departament>();
		
		// Creo tres instancias de Departament diferentes pero con el mismo contenido (apuntan a diferentes lugares de la memoria)
		// y añado 1 a cada lista (de la combinación de las tres sólo quiero que me devuelva 1).
		
		Departament departamento1=new Departament();
		departamento1.setCodigo("INF");
		departamento1.setId(new Long(1));
		departamento1.setNombre("Informática");	
		
		Departament departamento2=new Departament();
		departamento2.setCodigo("INF");
		departamento2.setId(new Long(1));
		departamento2.setNombre("Informática");	
		
		Departament departamento3=new Departament();
		departamento3.setCodigo("INF");
		departamento3.setId(new Long(1));
		departamento3.setNombre("Informática");
		
		lista1.add(departamento1);
		lista2.add(departamento2);
		lista3.add(departamento3);
	
		
		// Ahora creo 2 nuevas iguales y las añado a dos de las listas
		
		Departament departamento4=new Departament();
		departamento4.setCodigo("CON");
		departamento4.setId(new Long(2));
		departamento4.setNombre("Contabilidad");	
	
		
		Departament departamento5=new Departament();
		departamento5.setCodigo("CON");
		departamento5.setId(new Long(2));
		departamento5.setNombre("Contabilidad");
		
		lista1.add(departamento4);
		lista2.add(departamento5);
		
		// Ahora en la combinación de las tres debería tener 2 elementos
		
		// Creo otro más independiente y lo añado a una de las listas
		
		Departament departamento6=new Departament();
		departamento6.setCodigo("PRD");
		departamento6.setId(new Long(3));
		departamento6.setNombre("Producción");
		
		lista3.add(departamento6);
		
		// Al final tendrá que devolver 3 elementos diferentes
		// Creo una lista de las listas para enviarla a la función y añado las tres listas
		
		List<List<Departament>> listas=new ArrayList<>();
		listas.add(lista1);
		listas.add(lista2);
		listas.add(lista3);
		
		// Creamos la lista combinación de las tres
		
		List<Departament> combinacion=Utilidades.combinarVariasListasSinRepeticion(listas);
		
		// finalmente comprobamos que el tamaño es 3.
		
		assertEquals(3, combinacion.size());
		
		
		
	}

}
