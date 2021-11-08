package com.grup8.taskman.app.services.empresa;

import com.grup8.taskman.app.domain.empreses.Empresa;

/**
 * Interfície que determina les funcionalitats que tindrà la classe de servei que implementa el DAO de empresa. 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 */

public interface IEmpresaService {
	
	/**
	 * Mètode que buscarà una empresa mitjançant el seu id
	 * @param id Id de l'empresa buscada
	 * @return Retorna l'empresa trobada.
	 */
	public Empresa findById(Integer id);
	
	/**
	 * Mètode que guardarà una empresa a la base de dades
	 * @param empresa Empresa que volem guardar
	 * @return Retorna una instància de l'empresa.
	 */
	public Empresa save(Empresa empresa);

}
