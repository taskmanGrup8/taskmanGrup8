package com.grup8.taskman.app.services.tasques;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup8.taskman.app.domain.tasques.Fase;
import com.grup8.taskman.app.repository.tasques.IFaseDao;

/**
 * Classe que implementa la interficie IFaseService i que retorna els resultats del DAO.
 * Està marcada com Service per poder ser injectada posteriorment. Els diferents mètodes estan
 * anotats amb @Transactional per indicar que fan accions sobre la base de dades. Les que tenen 
 * l'atribut readOnly=true indiquen que nomès són de consulta.
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */

@Service
public class FaseService implements IFaseService{

	// Injectem la interfície del repository
	@Autowired
	IFaseDao faseDao;
	
	
	/**
	 * Mètode que guarda/actualitza els elements a la base de dades.
	 * @param fase Fase que volem guardar
	 * @return Retorna l'element guardat o null si no ho ha pogut realitzar.
	 */
	@Override
	@Transactional
	public Fase save(Fase fase) {
		return faseDao.save(fase);
	}

	/**
	 * Mètode que busca la llista completa d'elements de la taula fases
	 * @return Retorna la llista trobada
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Fase> findAll() {
		
		return (List<Fase>) faseDao.findAll();
	}

	/**
	 * Mètode que busca la fase amb l'id indicat
	 * @param id Id de la fase que volem trobar
	 * @return Retorna la fase trobada o null si no ha trobat cap
	 */
	@Override
	@Transactional(readOnly=true)
	public Fase findById(Long id) {
		
		return faseDao.findById(id).orElse(null);
	}

	/**
	 * Mètode que busca la fase amb el codi indicat
	 * @param codigo Codi de la fase que volem trobar
	 * @return Retorna la fase trobada o null si no ha trobat cap
	 */
	@Override
	@Transactional(readOnly=true)
	public Fase findByCodigo(String codigo) {
		
		return faseDao.findByCodigo(codigo).orElse(null);
	}

	/**
	 * Mètode que busca la fase amb el nom indicat
	 * @param nombre Nom de la fase que volem trobar
	 * @return Retorna la fase trobada o null si no ha trobat cap
	 */
	@Override
	@Transactional(readOnly=true)
	public Fase findByNombre(String nombre) {
		return faseDao.findByNombre(nombre).orElse(null);
	}

	/**
	 * Mètode que elimina la fase passada per paràmetre
	 * @param fase Fase que volem eliminar.
	 */
	@Override
	@Transactional
	public void delete(Fase fase) {
		
		faseDao.delete(fase);
		
	}

	/**
	 * Mètode que busca una llista de les fases que el seu camp codi comenci pel paràmetre passat.
	 * @param cadena Cadena per la qual ha de començar el codi de la fase.
	 * @return Retorna la llista trobada
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Fase> findByCodigoStartsWith(String cadena) {
		
		return faseDao.findByCodigoStartsWith(cadena);
	}

	/**
	 * Mètode que busca una llista de les fases que el seu camp nom comenci pel paràmetre passat.
	 * @param cadena Cadena per la qual ha de començar el nom de la fase.
	 * @return Retorna la llista trobada
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Fase> findByNombreStartsWith(String cadena) {
		
		return faseDao.findByNombreStartsWith(cadena);
	}

}
