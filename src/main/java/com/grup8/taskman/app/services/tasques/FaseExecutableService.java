package com.grup8.taskman.app.services.tasques;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup8.taskman.app.domain.tasques.FaseExecutable;
import com.grup8.taskman.app.repository.tasques.IFaseExecutableDao;

@Service
public class FaseExecutableService implements IFaseExecutableService{

	@Autowired
	IFaseExecutableDao faseExecutableDao;
	
	@Override
	@Transactional
	public FaseExecutable save(FaseExecutable fase) {
		
		return faseExecutableDao.save(fase);
	}

	@Override
	@Transactional(readOnly=true)
	public FaseExecutable findById(Long id) {
		
		return faseExecutableDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public List<FaseExecutable> findAll() {
		
		return (List<FaseExecutable>) faseExecutableDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<FaseExecutable> findByDepartament(Long idDepartament) {

		return faseExecutableDao.findByDepartament(idDepartament); 
	}

	@Override
	public void delete(FaseExecutable fase) {

		faseExecutableDao.delete(fase);
		
	}

}
