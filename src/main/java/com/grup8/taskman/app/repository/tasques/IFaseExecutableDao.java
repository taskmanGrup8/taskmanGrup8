package com.grup8.taskman.app.repository.tasques;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.grup8.taskman.app.domain.tasques.FaseExecutable;

/**
 * Interficie que estén de CrudRepository i que per tant tindrà les funcionalitats que ofereix la interfície
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
public interface IFaseExecutableDao extends CrudRepository<FaseExecutable, Long> {

	/**
	 * Mètode que cerca a la base de dades la llista de fases executables que el departament associat a la fase que té coincideixi amb el paràmetre rebut.
	 * @param id Id del departament al qual han de pertanyer les fases
	 * @return Retorna la llista de fases executables trobada
	 */
	@Query(value="SELECT u FROM FaseExecutable u INNER JOIN u.fase ft INNER JOIN ft.fase f INNER JOIN f.departament d where d.id= ?1")
	public List<FaseExecutable> findByDepartament(Long id);
	
	/**
	 * Mètode que cerca a la base de dades la llista de fases executables que el seu camp notificada coincideixi amb el paràmetre rebut.
	 * @param notificada Variable booleana que té que coincidir amb el camp notificada
	 * @return Retorna la llista trobada.
	 */
	public List<FaseExecutable> findByNotificada(boolean notificada);

}
