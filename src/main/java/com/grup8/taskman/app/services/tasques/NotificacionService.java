package com.grup8.taskman.app.services.tasques;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup8.taskman.app.domain.tasques.Notificacion;
import com.grup8.taskman.app.repository.tasques.INotificacionDao;

/**
 * Classe que implementa la interficie INotificacionService i que retorna els resultats del DAO.
 * Està marcada com Service per poder ser injectada posteriorment. Els diferents mètodes estan
 * anotats amb @Transactional per indicar que fan accions sobre la base de dades. Les que tenen 
 * l'atribut readOnly=true indiquen que nomès són de consulta.
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
@Service
public class NotificacionService implements INotificacionService{

	// Injectem la interfície del repository
	@Autowired
	INotificacionDao notificacionDao;
	
	/**
	 * Mètode que guarda la notificació rebuda per paràmetre a la base de dades
	 * @param notificacion Notificació que volem guardar
	 * @return Retorna la notificació guardada
	 */
	@Override
	@Transactional
	public Notificacion save(Notificacion notificacion) {
		
		return notificacionDao.save(notificacion);
	}

	/**
	 * Mètode que cerca a la base de dades la notificació amb l'id indicat
	 * @param id Id de la notificació que volem cercar
	 * @return Retorna la notificació trobada o null en cas de no trobar cap
	 */
	@Override
	@Transactional(readOnly=true)
	public Notificacion findById(long id) {
		
		return notificacionDao.findById(id).orElse(null);
	}

	/**
	 * Mètode que busca la llista completa d'elements de la taula notificacions
	 * @return Retorna la llista trobada
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Notificacion> findAll() {
		
		return (List<Notificacion>) notificacionDao.findAll();
	}

	/**
	 * Mètode que elimina la notificació passada per paràmetre
	 * @param notificacion Notificacio que volem eliminar
	 */
	@Override
	@Transactional
	public void delete(Notificacion notificacion) {
		
		notificacionDao.delete(notificacion);
		
	}

	/**
	 * Mètode que cerca la notificació que te associada una fase executable amb l'id rebut
	 * @param id Id de la fase executable que volem que estigui associada a la notificació
	 * @return Retorna les notificacions trobades
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Notificacion> findByFase(Long id) {
		
		return notificacionDao.findByFase(id);
	}

	/**
	 * Mètode que cerca la notificació que te associat un usuari amb l'id rebut.	
	 * @param id Id de l'usuari que volem que estigui associat a la notificació
	 * @return Retorna les notificacions trobades
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Notificacion> findByUsuari(Long id) {
		
		return notificacionDao.findByUsuari(id);
	}

	/**
	 * Mètode que cerca les notificacions compresses entre les dates indicades que pertayen a l'usuari rebut.
	 * @param id Identificador de l'usuari a qui volem que pertanyn les notificacions
	 * @param data1 Data d'inici del rang de dates
	 * @param data2 Data de fi del rang de dates
	 * @return Retorna les notificacions trobades
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Notificacion> findByUsuariEntreFechas(Long id, Date data1, Date data2) {
		
		return notificacionDao.findByUsuariEntreFechas(id, data1, data2);
	}

}
