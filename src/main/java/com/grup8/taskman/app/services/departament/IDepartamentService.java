package com.grup8.taskman.app.services.departament;

import java.util.List;

import com.grup8.taskman.app.domain.departaments.Departament;

public interface IDepartamentService {
	
	public void save(Departament departament);
	public List<Departament> findAll();
	public Departament findById(Long id);
	public Departament findByCodi(String codi);
	public Departament findByNom(String nom);	
	public void delete(Departament departament);
	public void deleteAll();
	public List<Departament> findByCodigoStartsWith(String codigo);
	public List<Departament> findByNombreStartsWith(String nombre);
	

}
