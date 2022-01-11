package com.grup8.taskman.app.repository.tasques;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.grup8.taskman.app.domain.tasques.Fase;

/**
 * Interficie que estén de CrudRepository i que per tant tindrà les funcionalitats que ofereix la interfície
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
public interface IFaseDao extends CrudRepository<Fase, Long> {
	
	/**
	 * Mètode que cerca a la base de dades la fase que tingui el codi rebut per paràmetre
	 * @param codigo Codi que volem cercar
	 * @return Retorna un optional amb la fase trobada
	 */
	public Optional<Fase> findByCodigo(String codigo);
	
	/**
	 * Mètode que cerca a la base de dades la fase que tingui el nom rebut per paràmetre
	 * @param nombre Nom que volem cercar
	 * @return Retorna un optional amb la fase trobada
	 */
	public Optional<Fase> findByNombre(String nombre);
	
	/**
	 * Mètode que cerca a la base de dades la llista de fases que el seu codi comença per la cadena rebuda
	 * @param cadena Cadena de text per la qual ha de començar el codi
	 * @return Retorna la llista de fases trobades
	 */
	public List<Fase> findByCodigoStartsWith(String cadena);
	
	/**
	 * Mètode que cerca a la base de dades la llista de fases que el seu nom comença per la cadena rebuda
	 * @param cadena Cadena de text per la qual ha de començar el nom
	 * @return Retorna la llista de fases trobades
	 */
	public List<Fase> findByNombreStartsWith(String cadena);	

}
