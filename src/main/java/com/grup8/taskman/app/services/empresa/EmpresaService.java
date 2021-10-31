package com.grup8.taskman.app.services.empresa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grup8.taskman.app.domain.empreses.Empresa;
import com.grup8.taskman.app.repository.empresa.IEmpresaDao;

@Service
public class EmpresaService implements IEmpresaService{
	
	@Autowired
	IEmpresaDao empresaDao;

	@Override
	public Empresa findById(Integer id) {
		
		return empresaDao.findById(id).orElse(null);
	}

	@Override
	public void save(Empresa empresa) {
		
		empresaDao.save(empresa);
		
	}

}
