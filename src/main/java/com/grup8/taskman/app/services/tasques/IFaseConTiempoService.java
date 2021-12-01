package com.grup8.taskman.app.services.tasques;

import java.util.List;

import com.grup8.taskman.app.domain.tasques.FaseConTiempo;

public interface IFaseConTiempoService {
	
	public FaseConTiempo save(FaseConTiempo fase);
	public List<FaseConTiempo> findAll();
	public FaseConTiempo findById(Long id);
	public void delete(FaseConTiempo fase);
	public List<FaseConTiempo> findByFase(Long id);

}
