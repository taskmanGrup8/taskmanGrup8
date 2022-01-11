package com.grup8.taskman.app.services.tasques;

import java.util.Date;
import java.util.List;

import com.grup8.taskman.app.domain.tasques.Orden;

/**
* Interfície que determina les funcionalitats que tindrà la classe de servei que implementa el DAO de d'ordres. 
* @author Sergio Esteban Gutiérrez
* @version 1.0.0
*
*/
public interface IOrdenService {

	public Orden save(Orden orden); // Mètode que guarda/actualitza els elements a la base de dades.
	public Orden findById(Long id); // Mètode que busca l'ordre amb l'id indicat
	public List<Orden> findAll(); // Mètode que busca la llista completa d'elements de la taula ordres
	public List<Orden> findByNotificada(boolean notificada); // Mètode que cerca la llista d'ordres que el seu camp notificada coincideix al el paràmetre passat
	public List<Orden> findByEntreDates(Date data1, Date data2); // Mètode que cerca totes les ordres que el seu camp dataFin estigui comprés al rang de dates passades per paràmetre.
	public void delete(Orden orden); // Mètode que elimina l'ordre passada per paràmetre
	public List<Orden> findByNomTasca(String nomTasca); // Mètode que busca l'ordre que te una tasca amb el nom indicat
}
