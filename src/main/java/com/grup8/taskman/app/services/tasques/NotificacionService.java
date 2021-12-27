package com.grup8.taskman.app.services.tasques;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup8.taskman.app.domain.tasques.Notificacion;
import com.grup8.taskman.app.repository.tasques.INotificacionDao;

@Service
public class NotificacionService implements INotificacionService{

	@Autowired
	INotificacionDao notificacionDao;
	
	@Override
	@Transactional
	public Notificacion save(Notificacion notificacion) {
		
		return notificacionDao.save(notificacion);
	}

	@Override
	@Transactional(readOnly=true)
	public Notificacion findById(long id) {
		
		return notificacionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Notificacion> findAll() {
		
		return (List<Notificacion>) notificacionDao.findAll();
	}

	@Override
	@Transactional
	public void delete(Notificacion notificacion) {
		
		notificacionDao.delete(notificacion);
		
	}

	@Override
	@Transactional(readOnly=true)
	public List<Notificacion> findByFase(Long id) {
		
		return notificacionDao.findByFase(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Notificacion> findByUsuari(Long id) {
		
		return notificacionDao.findByUsuari(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Notificacion> findByUsuariEntreFechas(Long id, Date data1, Date data2) {
		
		return notificacionDao.findByUsuariEntreFechas(id, data1, data2);
	}

}
