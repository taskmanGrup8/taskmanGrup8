package com.grup8.taskman.app.services.departament;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup8.taskman.app.domain.departaments.Departament;
import com.grup8.taskman.app.repository.departament.IDepartamentDao;

@Service
public class DepartamentService implements IDepartamentService {

	@Autowired
	IDepartamentDao seccioDao;

	@Override
	@Transactional
	public void save(Departament departament) {
		
		seccioDao.save(departament);

	}

	@Override
	@Transactional(readOnly=true)
	public List<Departament> findAll() {
		
		return (List<Departament>) seccioDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Departament findById(Long id) {
		return seccioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Departament findByCodi(String codi) {
		
		return seccioDao.findByCodigo(codi).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Departament findByNom(String nom) {
		return seccioDao.findByNombre(nom).orElse(null);
	}
	

	@Override
	@Transactional
	public void delete(Departament departament) {
		
		seccioDao.delete(departament);

	}

	@Override
	@Transactional
	public void deleteAll() {
		
		seccioDao.deleteAll();
		
	}

	@Override
	@Transactional(readOnly=true)
	public List<Departament> findByCodigoStartsWith(String codigo) {
		
		return seccioDao.findByCodigoStartsWith(codigo);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Departament> findByNombreStartsWith(String nombre) {
		return seccioDao.findByNombreStartsWith(nombre);
	}
	

}
