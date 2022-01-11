package com.grup8.taskman.app.services.tasques;

import java.util.Date;
import java.util.List;

import com.grup8.taskman.app.domain.tasques.Notificacion;

/**
 * Interfície que determina les funcionalitats que tindrà la classe de servei que implementa el DAO de notificacions.
 *  
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
public interface INotificacionService {

	
	public Notificacion save(Notificacion notificacion); // Mètode que guarda la notificació rebuda per paràmetre a la base de dades
	public Notificacion findById(long id); // Mètode que cerca a la base de dades la notificació amb l'id indicat
	public List<Notificacion> findAll(); // Mètode que cerca a la base de dades totes les notificacions
	public void delete(Notificacion notificacion); // Mètode que elimina la notificació rebuda per paràmetre de la base de dades
	public List<Notificacion> findByFase(Long id);	// Mètode que cerca la notificació que te associada una fase executable amb l'id rebut
	public List<Notificacion> findByUsuari(Long id);// Mètode que cerca la notificació que te associat un usuari amb l'id rebut.	
	public List<Notificacion> findByUsuariEntreFechas(Long id, Date data1, Date data2); // Mètode que cerca les notificacions compresses entre les dates indicades que pertayen a l'usuari rebut.
}
