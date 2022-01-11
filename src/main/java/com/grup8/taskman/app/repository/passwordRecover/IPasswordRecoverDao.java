package com.grup8.taskman.app.repository.passwordRecover;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.grup8.taskman.app.domain.usuaris.PasswordRecover;
/**
 * Interfície que estén de CrudRepository i que per tant tindrà les funcionalitats que ofereix la interfície
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
public interface IPasswordRecoverDao extends CrudRepository<PasswordRecover, Long>{
	
	/**
	 * Funció que busca a la base de dades el PasswordRecover que el seu camp link sigui igual al passat per paràmetre
	 * @param link Link que volem cercar
	 * @return Retorna un Optional amb l'element trobat.
	 */
	public Optional<PasswordRecover> findByLink(String link);

}
