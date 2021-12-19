package com.grup8.taskman.app.controllers;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.grup8.taskman.app.domain.empreses.Empresa;
import com.grup8.taskman.app.domain.usuaris.PasswordRecover;
import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.services.empresa.IEmpresaService;
import com.grup8.taskman.app.services.passwordRecover.IPasswordRecoverService;
import com.grup8.taskman.app.services.usuari.IUsuariService;
import com.grup8.taskman.app.util.Utilidades;

@Controller
@RequestMapping("/recover")
@SessionAttributes("usuari")
public class RecoverController {
	
	public static final int DESTINATARIOSINMAIL=1;
	public static final int REMITENTESINMAIL=2;
	public static final int ENVIOCORRECTO=0;
	
	@Autowired
	IUsuariService usuariService;
	
	@Autowired
	IEmpresaService empresaService;
	
	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
	private IPasswordRecoverService passwordRecoverService;
	
	// Injectem el bean per l'encriptació
		@Autowired
		private BCryptPasswordEncoder passwordEncoder;
	
	Empresa empresa;
	
	
	@PostMapping("/sendPasswordLink")
	public String enviarMail(@RequestParam(name="recover") String recover, Model model, RedirectAttributes flash) {
		
		if(recover== null || recover.isEmpty()) {
			System.out.println("Es buit");
			flash.addAttribute("error", "L'username no pot estar buit");
		}else {
		
			Usuari usuari=usuariService.findByUsername(recover);
			if(usuari==null){
				
				model.addAttribute("error", "L'username no existeix");
			}else {
				
								
				String link;
				try {
					link = generarLink(usuari);
					int result=enviarMensaje(usuari, link);
					if(result==DESTINATARIOSINMAIL) {
						
						model.addAttribute("error", "No es pot enviar el missatge perquè l'usuari no té mail");
					}else if(result==REMITENTESINMAIL) {
						
						model.addAttribute("error", "No es pot enviar el missatge perquè l'empresa no té mail");
					}else {
						
						model.addAttribute("success", "Missatge enviat correctament");
					}
					
				} catch (Exception e) {
					
					model.addAttribute("error", "No s'ha pogut generar el link");
				}
			}
		}
		
		return "login";
	}
	
	@GetMapping("/recoverPassword/{link}")
	public String recuperarPassword(@PathVariable(value = "link") String link, Model model) {
		
		PasswordRecover passwordRecover=passwordRecoverService.findByLink(link);
		if(passwordRecover==null) {
			
			model.addAttribute("error", "Error, link inexistente");
			return "login";
		}
		
		System.out.println(Utilidades.restarFechasEnDias(new Date(), passwordRecover.getData()));
		
		if(Utilidades.restarFechasEnDias(new Date(), passwordRecover.getData())>2){
			
			model.addAttribute("error", "Error, el link ya no está activo");
			return "login";
		}
		
		empresa= empresaService.findById(1);
		Usuari usuari=passwordRecover.getUsuari();
		model.addAttribute("usuari", usuari);
		model.addAttribute("empresa", empresa);
		
		return "crearPassword";
		
	}	
	
	@PostMapping("/savePassword")
	public String savePassword(Usuari usuari, Model model, RedirectAttributes flash, SessionStatus status ) {
		
		String passwordEncriptado=passwordEncoder.encode(usuari.getPassword());
		usuari.setPassword(passwordEncriptado);	
		// Guardem l'usuari
		usuariService.save(usuari);		
		status.setComplete();
		
		return "/login";
	}
	
	public int enviarMensaje(Usuari usuari, String link) {		
		
		Empresa empresa= empresaService.findById(1);
		String emailTo=usuari.getEmail();
		String emailFrom=empresa.getEmail();
		
		if(emailTo==null)return DESTINATARIOSINMAIL;
		if(emailFrom==null)return REMITENTESINMAIL;
		
		String mensaje="Haga click en el siguiente Link: " + link;
		SimpleMailMessage smm=new SimpleMailMessage();
		
		smm.setTo(emailTo);				
		smm.setFrom(emailFrom);
		smm.setSubject("Recuperar contrasenya");
		smm.setText(mensaje);		
		emailSender.send(smm);
		
		return ENVIOCORRECTO;
	}
	
	public String generarLink(Usuari usuari) throws Exception {
		
		PasswordRecover recover=new PasswordRecover();		
		String link=usuari.getUsername()+UUID.randomUUID().toString();
		recover.setData(new Date());
		recover.setUsuari(usuari);
		recover.setLink(link);
		passwordRecoverService.save(recover);		
		return PasswordRecover.LINKMAIL+link;
	}

}
