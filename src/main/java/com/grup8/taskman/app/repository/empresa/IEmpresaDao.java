package com.grup8.taskman.app.repository.empresa;

import org.springframework.data.repository.CrudRepository;

import com.grup8.taskman.app.domain.empreses.Empresa;

/**
 * Interficie que estén de CrudRepository i que per tant tindrà les funcionalitats que ofereix la interfície
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
public interface IEmpresaDao extends CrudRepository<Empresa, Integer> {

}
