package com.grup8.taskman.app.repository.tasques;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.grup8.taskman.app.domain.tasques.FaseConTiempo;

public interface IFaseConTiempoDao extends CrudRepository<FaseConTiempo, Long>{
		

	@Query(value="SELECT * FROM fases_con_tiempo u WHERE u.id_fase= :id", nativeQuery=true)
	public List<FaseConTiempo> findByFase(Long id);
}
