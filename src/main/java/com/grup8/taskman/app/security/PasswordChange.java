package com.grup8.taskman.app.security;

import javax.validation.constraints.NotBlank;

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
