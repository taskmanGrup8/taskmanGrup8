package com.grup8.taskman.app.repository.tasques;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.grup8.taskman.app.domain.tasques.Notificacion;

/**
 * Interficie que estén de CrudRepository i que per tant tindrà les funcionalitats que ofereix la interfície
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
public interface INotificacionDao extends CrudRepository<Notificacion, Long>{

	/**
	 * Mètode que cerca a la base de dades la llista de notificacions que l'id de la fase executable associada correspon al rebut per paràmetre.
	 * @param id Ide de la fase executable que cerquem.
	 * @return Retorna la llista de notificacions trobades.
	 */
	@Query(value="SELECT * FROM Notificacion u WHERE u.fase_id= :id", nativeQuery=true)
	public List<Notificacion> findByFase(Long id);
	
	/**
	 * Mètode que cerca a la base de dades la llista de notificacions que l'id de l'usuari associat correspon al rebut per paràmetre.
	 * @param id Id de l'usuari que cerquem
	 * @return Retorna la llista de notificacions trobades
	 */
	@Query(value="SELECT * FROM Notificacion u WHERE u.usuari_id= :id", nativeQuery=true)
	public List<Notificacion> findByUsuari(Long id);
	
	/**
	 * Mètode que cerca la llista de notificacions realitzades per un usuari entre les dates rebudes
	 * @param id Id de l'usuari que ens interessa
	 * @param data1 Data d'inici del rang
	 * @param data2 Data de fi del rang
	 * @return Retorna la llista trobada
	 */
	@Query(value="SELECT u from Notificacion WHERE((u.dataFin BETWEEN :data1 AND :data2) AND u.id_usuari= :id)" , nativeQuery=true)
	public List<Notificacion> findByUsuariEntreFechas(Long id, Date data1, Date data2);
	
}
