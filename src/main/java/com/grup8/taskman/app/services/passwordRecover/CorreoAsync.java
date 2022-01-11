package com.grup8.taskman.app.services.passwordRecover;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Classe dedicada a enviar mails de manera asíncrona. 
 * Està marcada com Service per poder ser injectada posteriorment.
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
@Service
public class CorreoAsync{
	
	// Injectem el bean de JavaMailSender de la classe de configuració
	@Autowired
	private JavaMailSender emailSender;
	
	/**
	 * Mètode que envia corrus electrònics de manera asíncrona
	 * @param link Link que volem enviar
	 * @param emailTo Adreça electrònica de destí
	 * @param emailFrom Adreça electrònica d'origen
	 */
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
	


