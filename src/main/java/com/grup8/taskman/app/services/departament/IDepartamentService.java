package com.grup8.taskman.app.services.departament;

import java.util.List;
import com.grup8.taskman.app.domain.departaments.Departament;

/**
 * Interfície que determina les funcionalitats que tindrà la classe de servei que implementa el DAO de departaments. 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */

public interface IDepartamentService {
	
	public Departament save(Departament departament); // Mètode per guardar departaments
	public List<Departament> findAll(); // Mètode per buscar tots els departaments
	public Departament findById(Long id); // Mètode per buscar un departament per Id
	public Departament findByCodi(String codi); // Mètode per buscar un departament pel camp codigo
	public Departament findByNom(String nom); // Mètode per buscar un departament pel camp codigo	
	public void delete(Departament departament); // Mètode per eliminar un departament
	public void deleteAll(); // Mètode per eliminar tots els departaments
	public List<Departament> findByCodigoStartsWith(String codigo); // Mètode per buscar la llista d'usuaris que tingui el camp codigo que comenci pel paràmetre indicat
	public List<Departament> findByNombreStartsWith(String nombre); // Mètode per buscar la llista d'usuaris que tingui el camp nombre que comenci pel paràmetre indicat
	

}
