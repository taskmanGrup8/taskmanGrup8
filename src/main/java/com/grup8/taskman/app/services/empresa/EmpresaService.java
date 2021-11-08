package com.grup8.taskman.app.services.empresa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup8.taskman.app.domain.empreses.Empresa;
import com.grup8.taskman.app.repository.empresa.IEmpresaDao;

/**
 * Classe que implementa la interficie IEmpresaService i que retorna els resultats del DAO.
 * Està marcada com Service per poder ser injectada posteriorment. Els diferents mètodes estan
 * anotats amb @Transactional per indicar que fan accions sobre la base de dades. Les que tenen 
 * l'atribut readOnly=true indiquen que nomès són de consulta.
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */

@Service
public class EmpresaService implements IEmpresaService{
	
	// Injectem la interfície que implementa el DAO d'empresa
	@Autowired
	IEmpresaDao empresaDao;

	/**
	 * Mètode que busca a la base de dades una empresa a partir de l'id passat per paràmetre
	 * @param id Id de l'empresa que volem trobar
	 * @return Retorna la empresa trobada. Si no l'ha trobat torna null.
	 */
	@Override
	@Transactional(readOnly=true)
	public Empresa findById(Integer id) {
		
		return empresaDao.findById(id).orElse(null);
	}

	/**
	 * Mètode que guarda/actualitza una empresa a la base de dades.
	 * @return Retorna l'element guardat o null si no ho ha pogut realitzar.
	 */
	@Override
	@Transactional
	public Empresa save(Empresa empresa) {
		
		Empresa result=empresaDao.save(empresa);
		if(result.getId()==null)return null;
		return result;
		
	}

}
