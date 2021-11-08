package com.grup8.taskman.app.services.departament;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup8.taskman.app.domain.departaments.Departament;
import com.grup8.taskman.app.repository.departament.IDepartamentDao;

/**
 * Classe que implementa la interficie IDepartamentService i que retorna els resultats del DAO.
 * Està marcada com Service per poder ser injectada posteriorment. Els diferents mètodes estan
 * anotats amb @Transactional per indicar que fan accions sobre la base de dades. Les que tenen 
 * l'atribut readOnly=true indiquen que nomès són de consulta.
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */

@Service
public class DepartamentService implements IDepartamentService {

	// Injectem la interfície del repository
	@Autowired
	IDepartamentDao seccioDao;

	/**
	 * Mètode que guarda/actualitza els elements a la base de dades.
	 * @return Retorna l'element guardat o null si no ho ha pogut realitzar.
	 */
	@Override
	@Transactional
	public Departament save(Departament departament) {
		
		Departament result=seccioDao.save(departament);
		// El mètode save mai retorna null, en canvi si no ho ha pogut guarda l'id que retorna si que és null
		// Si l'id és null llavors retornem null
		if(result.getId()==null)return null;
		// Retornem el departament guardat.
		return result;

	}

	/**
	 * Mètode que busca la llista completa d'elements de la taula departaments
	 * @return Retorna la llista trobada
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Departament> findAll() {
		
		return (List<Departament>) seccioDao.findAll();
	}

	/**
	 * Mètode que busca el departament amb l'id indicat
	 * @return Retorna el departament trobat o null si no ha trobat cap
	 */
	@Override
	@Transactional(readOnly=true)
	public Departament findById(Long id) {
		return seccioDao.findById(id).orElse(null);
	}

	
	/**
	 * Mètode que busca el departament amb el codi indicat
	 * @return Retorna el departament trobat o null si no ha trobat cap
	 */
	@Override
	@Transactional(readOnly=true)
	public Departament findByCodi(String codi) {
		
		return seccioDao.findByCodigo(codi).orElse(null);
	}

	/**
	 * Mètode que busca el departament amb el nom indicat
	 * @return Retorna el departament trobat o null si no ha trobat cap
	 */
	@Override
	@Transactional(readOnly=true)
	public Departament findByNom(String nom) {
		return seccioDao.findByNombre(nom).orElse(null);
	}
	

	/**
	 * Mètode que elimina el departament passat per paràmetre
	 */
	@Override
	@Transactional
	public void delete(Departament departament) {
		
		seccioDao.delete(departament);

	}

	/**
	 * Mètode que elimina tots els departaments de la base de dades
	 */
	@Override
	@Transactional
	public void deleteAll() {
		
		seccioDao.deleteAll();
		
	}

	/**
	 * Mètode que busca una llista dels departaments que el seu camp codi comenci pel paràmetre passat.
	 * @return Retorna la llista trobada
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Departament> findByCodigoStartsWith(String codigo) {
		
		return seccioDao.findByCodigoStartsWith(codigo);
	}

	
	/**
	 * Mètode que busca una llista dels departaments que el seu camp nom comenci pel paràmetre passat.
	 * @return Retorna la llista trobada
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Departament> findByNombreStartsWith(String nombre) {
		return seccioDao.findByNombreStartsWith(nombre);
	}
	

}
