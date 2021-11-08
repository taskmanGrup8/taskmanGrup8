package com.grup8.taskman.app.repository.rol;

import org.springframework.data.repository.CrudRepository;

import com.grup8.taskman.app.domain.usuaris.Rol;

/**
 * Interficie que estén de CrudRepository i que per tant tindrà les funcionalitats que aquesta ofereix.
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
public interface IRolDao extends CrudRepository<Rol, Long>{
		
		
}
