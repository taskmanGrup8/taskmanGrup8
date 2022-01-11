package com.grup8.taskman.app.repository.tasques;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.grup8.taskman.app.domain.tasques.Tasca;

/**
 * Interficie que estén de CrudRepository i que per tant tindrà les funcionalitats que ofereix la interfície
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
public interface ITascaDao extends CrudRepository<Tasca, Long> {
	
	/**
	 * Mètode que cerca a la base de dades la tasca que el seu codi coincideixi amb el rebut per paràmetre
	 * @param codigo Codi que volem que contingui la tasca
	 * @return Retorna un optional a la tasca trobada
	 */
	public Optional<Tasca> findByCodigo(String codigo);
	
	/**
	 * Mètode que cerca a la base de dades la tasca que el seu nom coincideixi amb el rebut per paràmetre
	 * @param nombre Nom que volem que contingui la tasca
	 * @return Retorna un optional a la tasca trobada
	 */
	public Optional<Tasca> findByNombre(String nombre);
	
	/**
	 * Mètode que cerca a la base de dades les tasques que el seu codi comenci per la cadena rebuda per paràmetre.
	 * @param cadena Cadena per la qual ha de començar el codi
	 * @return Retorna la llista de tasques trobada
	 */
	public List<Tasca> findByCodigoStartsWith(String cadena);
	
	/**
	 * Mètode que cerca a la base de dades les tasques que el seu nom comenci per la cadena rebuda per paràmetre.
	 * @param cadena Cadena per la qual ha de començar el nom
	 * @return Retorna la llista de tasques trobada
	 */
	public List<Tasca> findByNombreStartsWith(String cadena);
	
	/**
	 * Mètode que cerca a la base de dades les tasques que el seu camp cíclica coincideixi amb el rebut per paràmetre
	 * @param ciclica Estat del camp cíclica que cerquem a les tasques
	 * @return Retorna la llista de tasques trobada.
	 */
	public List<Tasca> findByCiclica(boolean ciclica);

}
