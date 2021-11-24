package com.grup8.taskman.app.security;

import javax.validation.constraints.NotBlank;

/**
 * Classe que farem servir per realitzar els canvis de contrasenya.
 * Nomes conté els camps id per l'id de l'usuari, oldPassword on l'usuari indicara l'antic password
 * i newPassword amb el nou password que vol guardar.
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */

public class PasswordChange {
	
	private Long id;
	@NotBlank
	private String oldPassword;
	@NotBlank
	private String newPassword;
	public PasswordChange() {
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}	

}
