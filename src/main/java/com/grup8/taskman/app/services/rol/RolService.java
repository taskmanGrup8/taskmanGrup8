package com.grup8.taskman.app.services.rol;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup8.taskman.app.domain.usuaris.Rol;
import com.grup8.taskman.app.repository.rol.IRolDao;

/**
 * Classe que implementa la interficie IRolService i que retorna els resultats del DAO.
 * Està marcada com Service per poder ser injectada posteriorment. Els diferents mètodes estan
 * anotats amb @Transactional per indicar que fan accions sobre la base de dades. Les que tenen 
 * l'atribut readOnly=true indiquen que nomès són de consulta.
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */

@Service
public class RolService implements IRolService {

	// Injectem la interfície que implementa el DAO de Rol
	@Autowired
	IRolDao rolDao;
	
	/**
	 * Mètode que busca tots els rols existents a la base de dades
	 * @return Retorna una llista amb els rols trobats.
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Rol> findAll() {
		
		return (List<Rol>)rolDao.findAll();
	}

	/**
	 * Mètode que busca a la base de dades un rol a partir de l'id passat per paràmetre
	 * @param id Id del rol que volem trobar
	 * @return Retorna el rol trobat. Si no l'ha trobat torna null.
	 */
	@Override
	@Transactional(readOnly=true)
	public Rol findById(Long id) {
		
		return rolDao.findById(id).orElse(null);
	}

	/**
	 * Mètode que guarda/actualitza un rol a la base de dades.
	 * @param Rol que volem guardar a la base de dades.
	 */
	@Override
	@Transactional
	public void save(Rol rol) {
		
		rolDao.save(rol);
	}

}
