package com.grup8.taskman.app.repository.tasques;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.grup8.taskman.app.domain.tasques.Orden;

/**
 * Interficie que estén de JpaRepository i que per tant tindrà les funcionalitats que ofereix la interfície
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
public interface IOrdenDao extends JpaRepository<Orden, Long> {

	/**
	 * Mètode que cerca a la base de dades la llista d'ordres que el seu camp notificada correspon amb el rebut per paràmetre
	 * @param notificada Estat en que volem que estigui el camp notificada
	 * @return Retorna el llistat d'ordres rebudes
	 */
	public List<Orden> findByNotificada(boolean notificada);
	
	/**
	 * Mètode que cerca totes les ordres que el seu camp dataFin estigui comprés al rang de dates passades per paràmetre. 
	 * @param data1 Data inici del rang
	 * @param data2 Data fi del rang
	 * @return Retorna el llistat d'ordres trobat
	 */
	public List<Orden> findAllByDataFinBetween(Date data1, Date data2);
	
	/**
	 * Mètode que cerca a la base de dades les ordres que el nom de la seva tasca associada correspongui amb el rebut per paràmetre
	 * @param nomTasca Nom de la tasca que volem que coincideixe
	 * @return Retorna la llista d'ordres trobada.
	 */
	@Query(value="SELECT u FROM Orden u INNER JOIN u.tasca t WHERE t.nombre= ?1 OR t.codigo= ?1")
	public List<Orden> findByNomTasca(String nomTasca); 
	
	
	
}
