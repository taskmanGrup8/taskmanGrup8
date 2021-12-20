package com.grup8.taskman.app.repository.passwordRecover;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.grup8.taskman.app.domain.usuaris.PasswordRecover;

public interface IPasswordRecoverDao extends CrudRepository<PasswordRecover, Long>{
	
	public Optional<PasswordRecover> findByLink(String link);

}
