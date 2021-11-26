package com.grup8.taskman.app.services.tasques;

import java.util.List;

import com.grup8.taskman.app.domain.tasques.Fase;

public interface IFaseService {
	
	public Fase save(Fase fase);
	public List<Fase> findAll();
	public Fase findById(Long id);
	public Fase findByCodigo(String codigo);
	public Fase findByNombre(String nombre);
	public void delete(Fase fase);
	public List<Fase> findByCodigoStartsWith(String cadena);
	public List<Fase> findByNombreStartsWith(String cadena);	


}
