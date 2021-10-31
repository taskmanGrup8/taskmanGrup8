package com.grup8.taskman.app.repository.departament;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.grup8.taskman.app.domain.departaments.Departament;

public interface IDepartamentDao extends CrudRepository<Departament, Long> {
	
	public Optional<Departament> findByCodigo(String codigo);
	public Optional<Departament> findByNombre(String nombre);
	public List<Departament> findByCodigoStartsWith(String codigo);
	public List<Departament> findByNombreStartsWith(String nombre);
	

}
