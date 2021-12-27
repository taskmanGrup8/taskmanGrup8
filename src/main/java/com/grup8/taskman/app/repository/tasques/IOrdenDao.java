package com.grup8.taskman.app.repository.tasques;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.grup8.taskman.app.domain.tasques.Orden;

public interface IOrdenDao extends JpaRepository<Orden, Long> {

	
	public List<Orden> findByNotificada(boolean notificada);
	

	
	public List<Orden> findAllByDataFinBetween(Date data1, Date data2);
	
	@Query(value="SELECT u FROM Orden u INNER JOIN u.tasca t WHERE t.nombre= ?1 OR t.codigo= ?1")
	public List<Orden> findByNomTasca(String nomTasca); 
	
	
	
}
