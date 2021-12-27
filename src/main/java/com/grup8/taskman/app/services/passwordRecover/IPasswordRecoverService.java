package com.grup8.taskman.app.services.passwordRecover;

import com.grup8.taskman.app.domain.usuaris.PasswordRecover;

public interface IPasswordRecoverService {
	
	public PasswordRecover save(PasswordRecover passwordRecover); // Mètode per guardar passwords recovers
	public PasswordRecover findByLink(String link);

}
