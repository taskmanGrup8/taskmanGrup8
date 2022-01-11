package com.grup8.taskman.app.repository.tasques;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.grup8.taskman.app.domain.tasques.FaseConTiempo;

/**
 * Interficie que estén de CrudRepository i que per tant tindrà les funcionalitats que ofereix la interfície
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
public interface IFaseConTiempoDao extends CrudRepository<FaseConTiempo, Long>{
		

	/**
	 * Mètode que cerca a la base de dades el llistat de fases amb temps que tenen com id_fase el rebut per paràmetre
	 * @param id Id de la fase que ha de estar associada amb la fase amb temps
	 * @return Retorna la llista trobada.
	 */
	@Query(value="SELECT * FROM fases_con_tiempo u WHERE u.id_fase= :id", nativeQuery=true)
	public List<FaseConTiempo> findByFase(Long id);
}
