package com.grup8.taskman.app.repository.tasques;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.grup8.taskman.app.domain.tasques.Notificacion;

public interface INotificacionDao extends CrudRepository<Notificacion, Long>{

	@Query(value="SELECT * FROM Notificacion u WHERE u.fase_id= :id", nativeQuery=true)
	public List<Notificacion> findByFase(Long id);
	
	@Query(value="SELECT * FROM Notificacion u WHERE u.usuari_id= :id", nativeQuery=true)
	public List<Notificacion> findByUsuari(Long id);
	
	@Query(value="SELECT u from Notificacion WHERE((u.dataFin BETWEEN :data1 AND :data2) AND u.id_usuari= :id)" , nativeQuery=true)
	public List<Notificacion> findByUsuariEntreFechas(Long id, Date data1, Date data2);
	
}
