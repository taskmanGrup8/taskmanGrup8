package com.grup8.taskman.app.repository.tasques;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.grup8.taskman.app.domain.tasques.Tasca;

public interface ITascaDao extends CrudRepository<Tasca, Long> {
	
	public Optional<Tasca> findByCodigo(String codigo);
	public Optional<Tasca> findByNombre(String nombre);
	public List<Tasca> findByCodigoStartsWith(String cadena);
	public List<Tasca> findByNombreStartsWith(String cadena);	

}
