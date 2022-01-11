package com.grup8.taskman.app.services.tasques;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup8.taskman.app.domain.tasques.FaseExecutable;
import com.grup8.taskman.app.repository.tasques.IFaseExecutableDao;

/**
 * Classe que implementa la interficie IFaseExecutableService i que retorna els resultats del DAO.
 * Està marcada com Service per poder ser injectada posteriorment. Els diferents mètodes estan
 * anotats amb @Transactional per indicar que fan accions sobre la base de dades. Les que tenen 
 * l'atribut readOnly=true indiquen que nomès són de consulta.
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
@Service
public class FaseExecutableService implements IFaseExecutableService{

	// Injectem la interfície del repository
	@Autowired
	IFaseExecutableDao faseExecutableDao;
	
	/**
	 * Mètode que guarda/actualitza els elements a la base de dades.
	 * @param fase Fase que volem guardar
	 * @return Retorna l'element guardat o null si no ho ha pogut realitzar.
	 */
	@Override
	@Transactional
	public FaseExecutable save(FaseExecutable fase) {
		
		return faseExecutableDao.save(fase);
	}

	/**
	 * Mètode que busca la fase executable  amb l'id indicat
	 * @param id Id de la fase executable que volem trobar
	 * @return Retorna la fase trobada o null si no ha trobat cap
	 */
	@Override
	@Transactional(readOnly=true)
	public FaseExecutable findById(Long id) {
		
		return faseExecutableDao.findById(id).orElse(null);
	}

	/**
	 * Mètode que busca la llista completa d'elements de la taula fases executables
	 * @return Retorna la llista trobada
	 */
	@Override
	@Transactional(readOnly=true)
	public List<FaseExecutable> findAll() {
		
		return (List<FaseExecutable>) faseExecutableDao.findAll();
	}

	/**
	 * Mètode que cerca la llista de fasesExecutables relacionades amb un determinat departament
	 * @param idDepartament Id del departament pel qual volem filtrar
	 * @return Retorna la llista trobada
	 */
	@Override
	@Transactional(readOnly=true)
	public List<FaseExecutable> findByDepartament(Long idDepartament) {

		return faseExecutableDao.findByDepartament(idDepartament); 
	}

	/**
	 * Mètode que elimina la fase amb temps passada per paràmetre
	 * @param fase Fase executable que volem eliminar
	 */
	@Override
	@Transactional
	public void delete(FaseExecutable fase) {

		faseExecutableDao.delete(fase);
		
	}

	/**
	 * Mètode que cerca la llista de fasesExecutables que el seu camp notificada coincideix al el paràmetre passat
	 * @param notificada Estat del camp notificada de la fase executable.
	 * @return Retorna la llista trobada
	 */
	@Override
	@Transactional(readOnly=true)
	public List<FaseExecutable> findByNotificada(boolean notificada) {
		return faseExecutableDao.findByNotificada(notificada);
	}

}
