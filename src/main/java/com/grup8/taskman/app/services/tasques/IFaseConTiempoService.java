package com.grup8.taskman.app.services.tasques;

import java.util.List;

import com.grup8.taskman.app.domain.tasques.FaseConTiempo;
/**
 * Interfície que determina les funcionalitats que tindrà la classe de servei que implementa el DAO de fases amb temps. 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
public interface IFaseConTiempoService {
	
	public FaseConTiempo save(FaseConTiempo fase); // Mètode per guardar fases amb temps
	public List<FaseConTiempo> findAll(); // Mètode per buscar totes les fases amb temps
	public FaseConTiempo findById(Long id); // Mètode per buscar una fase amb temps per id
	public void delete(FaseConTiempo fase); // Mètode per eliminar una fase amb temps
	public List<FaseConTiempo> findByFase(Long id); // Mètode per buscar totes les fases amb temps que estiguin associades a la fase amb id indicat.

}
