package com.grup8.taskman.app.services.tasques;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.grup8.taskman.app.domain.tasques.FaseConTiempo;
import com.grup8.taskman.app.repository.tasques.IFaseConTiempoDao;

@Service
public class FaseConTiempoService implements IFaseConTiempoService{

	@Autowired
	IFaseConTiempoDao faseDao;
	
	@Override
	@Transactional
	public FaseConTiempo save(FaseConTiempo fase) {
		return faseDao.save(fase);
	}

	@Override
	@Transactional(readOnly=true)
	public List<FaseConTiempo> findAll() {
		
		return (List<FaseConTiempo>) faseDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public FaseConTiempo findById(Long id) {
		
		return faseDao.findById(id).orElse(null);
	}	

	@Override
	@Transactional
	public void delete(FaseConTiempo fase) {
		
		faseDao.delete(fase);
		
	}

	@Override
	@Transactional
	public List<FaseConTiempo> findByFase(Long id) {
		return (List<FaseConTiempo>) faseDao.findByFase(id);
	}	

}
