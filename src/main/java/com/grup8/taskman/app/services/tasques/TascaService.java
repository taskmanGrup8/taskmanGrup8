package com.grup8.taskman.app.services.tasques;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup8.taskman.app.domain.tasques.Tasca;
import com.grup8.taskman.app.repository.tasques.ITascaDao;

@Service
public class TascaService implements ITascaService{

	@Autowired
	ITascaDao tascaDao;
	
	@Override
	@Transactional
	public Tasca save(Tasca tasca) {
		return tascaDao.save(tasca);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Tasca> findAll() {
		
		return (List<Tasca>) tascaDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Tasca findById(Long id) {
		
		return tascaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Tasca findByCodigo(String codigo) {
		
		return tascaDao.findByCodigo(codigo).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Tasca findByNombre(String nombre) {
		return tascaDao.findByNombre(nombre).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Tasca tasca) {
		
		tascaDao.delete(tasca);
		
	}

	@Override
	@Transactional(readOnly=true)
	public List<Tasca> findByCodigoStartsWith(String cadena) {
		
		return tascaDao.findByCodigoStartsWith(cadena);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Tasca> findByNombreStartsWith(String cadena) {
		
		return tascaDao.findByNombreStartsWith(cadena);
	}

}
