package com.grup8.taskman.app.services.tasques;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup8.taskman.app.domain.tasques.Fase;
import com.grup8.taskman.app.repository.tasques.IFaseDao;

@Service
public class FaseService implements IFaseService{

	@Autowired
	IFaseDao faseDao;
	
	@Override
	@Transactional
	public Fase save(Fase fase) {
		return faseDao.save(fase);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Fase> findAll() {
		
		return (List<Fase>) faseDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Fase findById(Long id) {
		
		return faseDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Fase findByCodigo(String codigo) {
		
		return faseDao.findByCodigo(codigo).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Fase findByNombre(String nombre) {
		return faseDao.findByNombre(nombre).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Fase fase) {
		
		faseDao.delete(fase);
		
	}

	@Override
	@Transactional(readOnly=true)
	public List<Fase> findByCodigoStartsWith(String cadena) {
		
		return faseDao.findByCodigoStartsWith(cadena);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Fase> findByNombreStartsWith(String cadena) {
		
		return faseDao.findByNombreStartsWith(cadena);
	}

}
