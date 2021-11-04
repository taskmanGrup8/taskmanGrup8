package com.grup8.taskman.app.services.rol;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grup8.taskman.app.domain.usuaris.Rol;
import com.grup8.taskman.app.repository.rol.IRolDao;

@Service
public class RolService implements IRolService {

	@Autowired
	IRolDao rolDao;
	
	@Override
	public List<Rol> findAll() {
		
		return (List<Rol>)rolDao.findAll();
	}

	@Override
	public Rol findById(Long id) {
		
		return rolDao.findById(id).orElse(null);
	}

	@Override
	public void save(Rol rol) {
		
		rolDao.save(rol);
	}

}
