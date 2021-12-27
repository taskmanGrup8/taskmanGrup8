package com.grup8.taskman.app.services.tasques;

import java.util.Date;
import java.util.List;

import com.grup8.taskman.app.domain.tasques.Orden;

public interface IOrdenService {

	public Orden save(Orden orden);
	public Orden findById(Long id);
	public List<Orden> findAll();
	public List<Orden> findByNotificada(boolean notificada);
	public List<Orden> findByEntreDates(Date data1, Date data2);
	public void delete(Orden orden);
	public List<Orden> findByNomTasca(String nomTasca); 
}
