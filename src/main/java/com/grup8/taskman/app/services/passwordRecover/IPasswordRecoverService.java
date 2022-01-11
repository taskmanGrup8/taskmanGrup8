package com.grup8.taskman.app.services.passwordRecover;

import com.grup8.taskman.app.domain.usuaris.PasswordRecover;

/**
 * Interfície que determina les funcionalitats que tindrà la classe de servei que implementa el DAO de passwordRecover. 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 */
public interface IPasswordRecoverService {
	
	public PasswordRecover save(PasswordRecover passwordRecover); // Mètode per guardar passwords recovers
	public PasswordRecover findByLink(String link); // Mètode que cerca el passwordRecover amb el link indicat.

}
