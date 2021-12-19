package com.grup8.taskman.app.services.tasques;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grup8.taskman.app.domain.tasques.Orden;
import com.grup8.taskman.app.repository.tasques.IOrdenDao;

@Service
public class OrdenService implements IOrdenService{

	@Autowired
	IOrdenDao ordenDao;
	
	@Override
	public Orden save(Orden orden) {
		
		return ordenDao.save(orden);
	}

	@Override
	public Orden findById(Long id) {
		
		return ordenDao.findById(id).orElse(null);
	}

	@Override
	public List<Orden> findAll() {
		
		return (List<Orden>) ordenDao.findAll();
	}

	@Override
	public List<Orden> findByNotificada(boolean notificada) {
		
		return ordenDao.findByNotificada(notificada);
	}

	@Override
	public List<Orden> findByEntreDates(Date data1, Date data2) {
		
		return ordenDao.findByEntreDates(data1, data2);
	}

}
