package com.grup8.taskman.app.services.rol;

import java.util.List;

import com.grup8.taskman.app.domain.usuaris.Rol;

public interface IRolService {
	
	public List<Rol> findAll();
	public Rol findById(Long id);
	public void save(Rol rol);

}
