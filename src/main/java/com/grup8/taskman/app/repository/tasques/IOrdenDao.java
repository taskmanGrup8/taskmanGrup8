package com.grup8.taskman.app.repository.tasques;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.grup8.taskman.app.domain.tasques.Orden;

public interface IOrdenDao extends CrudRepository<Orden, Long> {

	
	public List<Orden> findByNotificada(boolean notificada);
	
	@Query(value="SELECT u from Orden WHERE u.dataFin BETWEEN :data1 AND :data2 ", nativeQuery=true)
	public List<Orden> findByEntreDates(Date data1, Date data2);
	
	
	
}
