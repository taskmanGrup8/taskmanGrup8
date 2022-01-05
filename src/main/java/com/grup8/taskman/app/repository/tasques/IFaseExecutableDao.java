package com.grup8.taskman.app.repository.tasques;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.grup8.taskman.app.domain.tasques.FaseExecutable;

public interface IFaseExecutableDao extends CrudRepository<FaseExecutable, Long> {

	@Query(value="SELECT u FROM FaseExecutable u INNER JOIN u.fase ft INNER JOIN ft.fase f INNER JOIN f.departament d where d.id= ?1")
	public List<FaseExecutable> findByDepartament(Long id);
	
	public List<FaseExecutable> findByNotificada(boolean notificada);

}
// select fase_executable.id, bloqueada, notificada, fase_id, orden_id from fase_executable inner join fases_con_tiempo on fase_executable.fase_id=fases_con_tiempo.id inner join fases on fases_con_tiempo.id_fase=fases.id inner join departaments on fases_id_departament=departaments.id where departaments.id=3