package com.grup8.taskman.app.services.empresa;

import com.grup8.taskman.app.domain.empreses.Empresa;

public interface IEmpresaService {
	
	public Empresa findById(Integer id);
	public void save(Empresa empresa);

}
