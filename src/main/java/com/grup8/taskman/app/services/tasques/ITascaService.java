package com.grup8.taskman.app.services.tasques;

import java.util.List;

import com.grup8.taskman.app.domain.tasques.Tasca;

/**
 * Interfície que determina les funcionalitats que tindrà la classe de servei que implementa el DAO de tasques. 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
public interface ITascaService {
	
	public Tasca save(Tasca tasca); // Mètode per guardar tasques
	public List<Tasca> findAll(); // Mètode per buscar totes les tasques
	public Tasca findById(Long id); // Mètode per buscar una tasca per id
	public Tasca findByCodigo(String codigo); // Mètode per buscar una tasca pel camp codigo
	public Tasca findByNombre(String nombre); // Mètode per buscar una tasca pel camp nombre
	public void delete(Tasca tasca); // Mètode per eliminar una tasca
	public List<Tasca> findByCodigoStartsWith(String cadena); // Mètode per buscar la llista de tasques que tinguin el camp codigo que comenci pel paràmetre indicat
	public List<Tasca> findByNombreStartsWith(String cadena); // Mètode per buscar la llista de tasques que tinguin el camp nombre que comenci pel paràmetre indicat
	public List<Tasca> findByCiclica(boolean ciclica); // Mètode per buscar la llista de tasques que el seu camp ciclica correspongui amb el paràmetre indicat.


}
