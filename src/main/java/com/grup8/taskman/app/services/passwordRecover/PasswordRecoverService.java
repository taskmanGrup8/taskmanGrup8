package com.grup8.taskman.app.services.passwordRecover;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup8.taskman.app.domain.usuaris.PasswordRecover;
import com.grup8.taskman.app.repository.passwordRecover.IPasswordRecoverDao;

/**
* Classe que implementa la interficie IPasswordRecoverService i que retorna els resultats del DAO.
* Està marcada com Service per poder ser injectada posteriorment. Els diferents mètodes estan
* anotats amb @Transactional per indicar que fan accions sobre la base de dades. Les que tenen 
* l'atribut readOnly=true indiquen que nomès són de consulta.
* 
* @author Sergio Esteban Gutiérrez
* @version 1.0.0
*
*/

@Service
public class PasswordRecoverService implements IPasswordRecoverService{

	// Injectem el servei IPasswordRecoverDao per tenir accés a la base de dades
	@Autowired
	IPasswordRecoverDao passwordRecoverDao;
	
	/**
	 * Mètode que guarda el PasswordRecover rebut per paràmetre
	 * @param passwordRecover Instància de passwordRecover que volem guardar
	 * @return Retorna el passwordRecover guardat. 
	 */
	@Override
	@Transactional
	public PasswordRecover save(PasswordRecover passwordRecover) {
		
		return passwordRecoverDao.save(passwordRecover); 
	}

	/**
	 * Mètode que cerca el passwordRecover que el seu camp link correspon amb el link passat per paràmetre
	 * @param link Link del passwordRecover que cerquem.
	 * @return Retorna el passwordRecover trobat o null si no en troba cap.
	 */
	@Override
	@Transactional(readOnly=true)
	public PasswordRecover findByLink(String link) {
		
		return passwordRecoverDao.findByLink(link).orElse(null);
	}

}
