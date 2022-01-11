package com.grup8.taskman.app.services.tasques;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup8.taskman.app.domain.tasques.Orden;
import com.grup8.taskman.app.repository.tasques.IOrdenDao;

/**
 * Classe que implementa la interficie IOrdenService i que retorna els resultats del DAO.
 * Està marcada com Service per poder ser injectada posteriorment. Els diferents mètodes estan
 * anotats amb @Transactional per indicar que fan accions sobre la base de dades. Les que tenen 
 * l'atribut readOnly=true indiquen que nomès són de consulta.
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
@Service
public class OrdenService implements IOrdenService{

	// Injectem la interfície del repository
	@Autowired	
	IOrdenDao ordenDao;
	
	/**
	 * Mètode que guarda/actualitza els elements a la base de dades.
	 * @param orden Ordre que volem guardar
	 * @return Retorna l'element guardat o null si no ho ha pogut realitzar.
	 */
	@Override
	@Transactional
	public Orden save(Orden orden) {
		
		return ordenDao.save(orden);
	}

	/**
	 * Mètode que busca l'ordre amb l'id indicat
	 * @param id Id de l'ordre que volem cercar
	 * @return Retorna l'ordre trobada o null si no ha trobat cap
	 */
	@Override
	@Transactional(readOnly=true)
	public Orden findById(Long id) {
		
		return ordenDao.findById(id).orElse(null);
	}

	/**
	 * Mètode que busca la llista completa d'elements de la taula ordres
	 * @return Retorna la llista trobada
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Orden> findAll() {
		
		return (List<Orden>) ordenDao.findAll();
	}

	/**
	 * Mètode que cerca la llista d'ordres que el seu camp notificada coincideix al el paràmetre passat
	 * @param notificada Estat del camp notificada de l'ordre.
	 * @return Retorna la llista trobada
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Orden> findByNotificada(boolean notificada) {
		
		return ordenDao.findByNotificada(notificada);
	}

	/**
	 * Mètode que cerca totes les ordres que el seu camp dataFin estigui comprés al rang de dates passades per paràmetre. 
	 * @param data1 Data inici del rang
	 * @param data2 Data fi del rang
	 * @return Retorna el llistat d'ordres trobat
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Orden> findByEntreDates(Date data1, Date data2) {
		
		return ordenDao.findAllByDataFinBetween(data1, data2);
	}

	/**
	 * Mètode que elimina l'ordre passada per paràmetre
	 * @param orden Ordre que volem eliminar
	 */
	@Override
	@Transactional
	public void delete(Orden orden) {
		
		ordenDao.delete(orden);
		
	}

	/**
	 * Mètode que busca l'ordre que te una tasca amb el nom indicat
	 * @param nomTasca Nom que ha de tenir la tasca associada a l'ordre
	 * @return Retorna la llista trobada.
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Orden> findByNomTasca(String nomTasca) {

		return ordenDao.findByNomTasca(nomTasca);
	}	

}
