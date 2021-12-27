package com.grup8.taskman.app.services.passwordRecover;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CorreoAsync{
	
	@Autowired
	private JavaMailSender emailSender;
	
	@Async
	public void enviarCorreo(String link, String emailTo, String emailFrom) {		
		
		
		String mensaje="Haga click en el siguiente Link: " + link;
		SimpleMailMessage smm=new SimpleMailMessage();		
		smm.setTo(emailTo);				
		smm.setFrom(emailFrom);
		smm.setSubject("Recuperar contrasenya");
		smm.setText(mensaje);		
		emailSender.send(smm);
		
		return;
	}
}
	


