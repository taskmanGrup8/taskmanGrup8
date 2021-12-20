package com.grup8.taskman.app.services.passwordRecover;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grup8.taskman.app.domain.usuaris.PasswordRecover;
import com.grup8.taskman.app.repository.passwordRecover.IPasswordRecoverDao;

@Service
public class PasswordRecoverService implements IPasswordRecoverService{

	@Autowired
	IPasswordRecoverDao passwordRecoverDao;
	
	@Override
	public PasswordRecover save(PasswordRecover passwordRecover) {
		
		return passwordRecoverDao.save(passwordRecover); 
	}

	@Override
	public PasswordRecover findByLink(String link) {
		
		return passwordRecoverDao.findByLink(link).orElse(null);
	}

}
