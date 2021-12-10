package com.grup8.taskman.app.services.tasques;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup8.taskman.app.domain.tasques.Tasca;
import com.grup8.taskman.app.repository.tasques.ITascaDao;

/**
 * Classe que implementa la interficie ITascaService i que retorna els resultats del DAO.
 * Està marcada com Service per poder ser injectada posteriorment. Els diferents mètodes estan
 * anotats amb @Transactional per indicar que fan accions sobre la base de dades. Les que tenen 
 * l'atribut readOnly=true indiquen que nomès són de consulta.
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
@Service
public class TascaService implements ITascaService{

	// Injectem la interfície del repository
	@Autowired
	ITascaDao tascaDao;
	
	/**
	 * Mètode que guarda/actualitza els elements a la base de dades.
	 * @return Retorna l'element guardat o null si no ho ha pogut realitzar.
	 */
	@Override
	@Transactional
	public Tasca save(Tasca tasca) {
		return tascaDao.save(tasca);
	}

	/**
	 * Mètode que busca la llista completa d'elements de la taula tasques
	 * @return Retorna la llista trobada
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Tasca> findAll() {
		
		return (List<Tasca>) tascaDao.findAll();
	}

	/**
	 * Mètode que busca la tasca amb l'id indicat
	 * @return Retorna la tasca trobada o null si no ha trobat cap
	 */
	@Override
	@Transactional(readOnly=true)
	public Tasca findById(Long id) {
		
		return tascaDao.findById(id).orElse(null);
	}

	/**
	 * Mètode que busca la tasca amb el codi indicat
	 * @return Retorna la tasca trobada o null si no ha trobat cap
	 */
	@Override
	@Transactional(readOnly=true)
	public Tasca findByCodigo(String codigo) {
		
		return tascaDao.findByCodigo(codigo).orElse(null);
	}

	/**
	 * Mètode que busca la tasca amb el nom indicat
	 * @return Retorna la tasca trobada o null si no ha trobat cap
	 */
	@Override
	@Transactional(readOnly=true)
	public Tasca findByNombre(String nombre) {
		return tascaDao.findByNombre(nombre).orElse(null);
	}

	/**
	 * Mètode que elimina la tasca passada per paràmetre
	 */
	@Override
	@Transactional
	public void delete(Tasca tasca) {
		
		tascaDao.delete(tasca);
		
	}

	/**
	 * Mètode que busca una llista de les tasques que el seu camp codi comenci pel paràmetre passat.
	 * @return Retorna la llista trobada
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Tasca> findByCodigoStartsWith(String cadena) {
		
		return tascaDao.findByCodigoStartsWith(cadena);
	}

	/**
	 * Mètode que busca una llista de les tasques que el seu camp nom comenci pel paràmetre passat.
	 * @return Retorna la llista trobada
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Tasca> findByNombreStartsWith(String cadena) {
		
		return tascaDao.findByNombreStartsWith(cadena);
	}

}
