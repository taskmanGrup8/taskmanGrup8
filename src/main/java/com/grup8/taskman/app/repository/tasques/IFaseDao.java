package com.grup8.taskman.app.repository.tasques;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.grup8.taskman.app.domain.tasques.Fase;

public interface IFaseDao extends CrudRepository<Fase, Long> {
	
	public Optional<Fase> findByCodigo(String codigo);
	public Optional<Fase> findByNombre(String nombre);
	
	public List<Fase> findByCodigoStartsWith(String cadena);
	public List<Fase> findByNombreStartsWith(String cadena);	

}
