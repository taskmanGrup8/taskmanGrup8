package com.grup8.taskman.app.services.tasques;

import java.util.Date;
import java.util.List;

import com.grup8.taskman.app.domain.tasques.Notificacion;

public interface INotificacionService {

	
	public Notificacion save(Notificacion notificacion);
	public Notificacion findById(long id);
	public List<Notificacion> findAll();
	public void delete(Notificacion notificacion);
	public List<Notificacion> findByFase(Long id);	
	public List<Notificacion> findByUsuari(Long id);	
	public List<Notificacion> findByUsuariEntreFechas(Long id, Date data1, Date data2);
}
