package com.grup8.taskman.app.repository.empresa;

import org.springframework.data.repository.CrudRepository;

import com.grup8.taskman.app.domain.empreses.Empresa;

public interface IEmpresaDao extends CrudRepository<Empresa, Integer> {

}
