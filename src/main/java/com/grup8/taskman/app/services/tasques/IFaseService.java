package com.grup8.taskman.app.services.tasques;

import java.util.List;

import com.grup8.taskman.app.domain.tasques.Fase;

/**
 * Interfície que determina les funcionalitats que tindrà la classe de servei que implementa el DAO de fases. 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
public interface IFaseService {
	
	public Fase save(Fase fase); // Mètode per guardar fases
	public List<Fase> findAll(); // Mètode per buscar totes les fases
	public Fase findById(Long id); // Mètode per buscar una fase per id
	public Fase findByCodigo(String codigo); // Mètode per buscar una fase pel camp codigo
	public Fase findByNombre(String nombre); // Mètode per buscar una fase pel camp nombre
	public void delete(Fase fase); // Mètode per eliminar una fase
	public List<Fase> findByCodigoStartsWith(String cadena); // Mètode per buscar la llista de fases que tinguin el camp codigo que comenci pel paràmetre indicat
	public List<Fase> findByNombreStartsWith(String cadena); // Mètode per buscar la llista de fases que tinguin el camp nombre que comenci pel paràmetre indicat


}
