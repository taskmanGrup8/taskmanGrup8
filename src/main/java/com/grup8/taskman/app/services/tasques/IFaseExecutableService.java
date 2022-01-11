package com.grup8.taskman.app.services.tasques;

import java.util.List;

import com.grup8.taskman.app.domain.tasques.FaseExecutable;

public interface IFaseExecutableService {
	
	public FaseExecutable save(FaseExecutable fase); // Métode que guarda a la base de dades la fase rebuda per paràmetre
	public FaseExecutable findById(Long id); // Mètode que cerca a la base de dades la fase executable amb l'id indicat
	public List<FaseExecutable> findAll(); // Mètode que cerca a la base de dades totes les fases executables
	public List<FaseExecutable> findByDepartament(Long idDepartament); // Mètode que cerca a la base de dades les fases executables associades al departament pasat
	public void delete(FaseExecutable fase); // Mètode que elimina una fase executable de la base de dades
	public List<FaseExecutable> findByNotificada(boolean notificada); // Mètode que cerca les fases executables que el seu camp notificada coincideix amb el passat per paràmetre

}
