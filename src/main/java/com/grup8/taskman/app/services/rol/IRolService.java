package com.grup8.taskman.app.services.rol;

import java.util.List;

import com.grup8.taskman.app.domain.usuaris.Rol;
/**
 * Interfície que determina les funcionalitats que tindrà la classe de servei que implementa el DAO de rol. 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 */

public interface IRolService {
	
	/**
	 * Mètode que busca tots els rols a la base de dades
	 * @return Retorna la llista amb els rols trobats
	 */
	public List<Rol> findAll();
	
	/**
	 * Mètode que buscar un rol determinat per l'id rebut per paràmetre
	 * @param id Id del rol que volem buscar
	 * @return Retorna el rol trobat o null en cas contrari
	 */
	public Rol findById(Long id);
	
	/**
	 * Mètode que guarda el rol passat per paràmetre a la base de dades
	 * @param rol Rol que volem guardar
	 */
	public void save(Rol rol);

}
