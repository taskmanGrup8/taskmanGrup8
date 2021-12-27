package com.grup8.taskman.app.services.tasques;

import java.util.List;

import com.grup8.taskman.app.domain.tasques.FaseExecutable;

public interface IFaseExecutableService {
	
	public FaseExecutable save(FaseExecutable fase);
	public FaseExecutable findById(Long id);
	public List<FaseExecutable> findAll();
	public List<FaseExecutable> findByDepartament(Long idDepartament);
	public void delete(FaseExecutable fase);

}
