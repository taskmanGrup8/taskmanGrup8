package com.grup8.taskman.app.services.tasques;

import java.util.List;

import com.grup8.taskman.app.domain.tasques.Tasca;

public interface ITascaService {
	
	public Tasca save(Tasca tasca);
	public List<Tasca> findAll();
	public Tasca findById(Long id);
	public Tasca findByCodigo(String codigo);
	public Tasca findByNombre(String nombre);
	public void delete(Tasca tasca);
	public List<Tasca> findByCodigoStartsWith(String cadena);
	public List<Tasca> findByNombreStartsWith(String cadena);	


}
