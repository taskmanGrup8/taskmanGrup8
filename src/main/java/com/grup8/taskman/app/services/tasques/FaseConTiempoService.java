package com.grup8.taskman.app.services.tasques;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.grup8.taskman.app.domain.tasques.FaseConTiempo;
import com.grup8.taskman.app.repository.tasques.IFaseConTiempoDao;

/**
 * Classe que implementa la interficie IFaseConTiempoService i que retorna els resultats del DAO.
 * Està marcada com Service per poder ser injectada posteriorment. Els diferents mètodes estan
 * anotats amb @Transactional per indicar que fan accions sobre la base de dades. Les que tenen 
 * l'atribut readOnly=true indiquen que nomès són de consulta.
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
@Service
public class FaseConTiempoService implements IFaseConTiempoService{

	// Injectem la interfície del repository
	@Autowired
	IFaseConTiempoDao faseDao;
	
	/**
	 * Mètode que guarda/actualitza els elements a la base de dades.
	 * @param fase Fase que volem guardar
	 * @return Retorna l'element guardat o null si no ho ha pogut realitzar.
	 */
	@Override
	@Transactional
	public FaseConTiempo save(FaseConTiempo fase) {
		return faseDao.save(fase);
	}

	/**
	 * Mètode que busca la llista completa d'elements de la taula fases amb temps
	 * @return Retorna la llista trobada
	 */
	@Override
	@Transactional(readOnly=true)
	public List<FaseConTiempo> findAll() {
		
		return (List<FaseConTiempo>) faseDao.findAll();
	}

	/**
	 * Mètode que busca la fase amb temps  amb l'id indicat
	 * @param id Id de la fase amb temps que volem trobar
	 * @return Retorna la fase trobada o null si no ha trobat cap
	 */
	@Override
	@Transactional(readOnly=true)
	public FaseConTiempo findById(Long id) {
		
		return faseDao.findById(id).orElse(null);
	}	

	/**
	 * Mètode que elimina la fase amb temps passada per paràmetre
	 * @param fase Fase amb temps que volem eliminar
	 */
	@Override
	@Transactional
	public void delete(FaseConTiempo fase) {
		
		faseDao.delete(fase);
		
	}

	/**
	 * Mètode que busca la llista de fases amb temps que l'id de la fase associada coincideixi amb l'indicat
	 * @param id Id que ha de tenir la fase associada amb la fase amb temps
	 * @return Retorna la llista de fases amb temps trobades.
	 */
	@Override
	@Transactional
	public List<FaseConTiempo> findByFase(Long id) {
		return (List<FaseConTiempo>) faseDao.findByFase(id);
	}	

}
