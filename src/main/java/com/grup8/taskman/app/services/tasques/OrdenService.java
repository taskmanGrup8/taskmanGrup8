package com.grup8.taskman.app.services.tasques;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup8.taskman.app.domain.tasques.Orden;
import com.grup8.taskman.app.repository.tasques.IOrdenDao;

@Service
public class OrdenService implements IOrdenService{

	@Autowired
	
	IOrdenDao ordenDao;
	
	@Override
	@Transactional
	public Orden save(Orden orden) {
		
		return ordenDao.save(orden);
	}

	@Override
	@Transactional(readOnly=true)
	public Orden findById(Long id) {
		
		return ordenDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Orden> findAll() {
		
		return (List<Orden>) ordenDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<Orden> findByNotificada(boolean notificada) {
		
		return ordenDao.findByNotificada(notificada);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Orden> findByEntreDates(Date data1, Date data2) {
		
		return ordenDao.findAllByDataFinBetween(data1, data2);
	}

	@Override
	@Transactional
	public void delete(Orden orden) {
		
		ordenDao.delete(orden);
		
	}

	@Override
	@Transactional(readOnly=true)
	public List<Orden> findByNomTasca(String nomTasca) {

		return ordenDao.findByNomTasca(nomTasca);
	}
	
	

}
