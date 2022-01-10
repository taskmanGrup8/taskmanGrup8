package com.grup8.taskman.app.controllers;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.grup8.taskman.app.services.passwordRecover.CorreoAsync;
import com.grup8.taskman.app.services.passwordRecover.IPasswordRecoverService;
import com.grup8.taskman.app.services.usuari.IUsuariService;
import com.grup8.taskman.app.util.Utilidades;

/**
 * Classe controladora mapejada a la url "/recover" que s'encarrega de la gestió de recuperació de contrasenya.
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping("/recover")
@SessionAttributes("usuari")
public class RecoverController {
	
	// CONSTANTS
	
	// Definim constants per les possibles solucions a l'enviament del mail
	
	public static final int DESTINATARIOSINMAIL=1;
	public static final int REMITENTESINMAIL=2;
	public static final int ENVIOCORRECTO=0;
	
	// ATRIBUTS
	
	// Injectem el servei asíncron CorreoAsync que s'encarrega d'enviar correus
	@Autowired
	CorreoAsync correoAsync;
	
	// Injectem el servei IUsuariService per tenir accés a la taula usuaris de la base de dades	
	@Autowired
	IUsuariService usuariService;
	
	// Injectem el servei IEmpresaService per tenir accés a la taula empreses de la base de dades
	@Autowired
	IEmpresaService empresaService;	
	
	// Injectem el servei IPasswordRecoverService per tenir accés a la taula password_recover de la base de dades
	@Autowired
	private IPasswordRecoverService passwordRecoverService;
	
	// Injectem el bean per l'encriptació
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// Necessitem l'empresa per obtenir les dades per enviar el mail
	Empresa empresa;
	
	/**
	 * Mètode de tipus post mapejat a la url "/sendPasswordLink" que s'encarrega d'enviar un missatge de correu a l'usuari
	 * que demana recuperar la contrasenya
	 * @param recover Paràmetre que rep l'username de l'usuari que vol recuperar la contrasenya
	 * @param model Model de la vista.
	 * @param flash Instància que ens permet afegir missatges a la vista.
	 * @return Carrega la pàgina login.
	 */
	@PostMapping("/sendPasswordLink")
	public String enviarMail(@RequestParam(name="recover") String recover, Model model, RedirectAttributes flash) {
		
		// Si l'username és null o està buit enviem un missatge d'error
		if(recover== null || recover.isEmpty()) {			
			flash.addAttribute("error", "L'username no pot estar buit");
		}else {
		
			// Cerquem l'usuari a la base de dades
			Usuari usuari=usuariService.findByUsername(recover);
			// Si no trobem l'usuari informen de l'error
			if(usuari==null){
				
				model.addAttribute("error", "L'username no existeix");
			}else {
				
				// Carreguem l'empresa
				Empresa empresa= empresaService.findById(1);
				// Agafem les dades d'origen i destí i comporvem les adreces elecòniques
				String emailTo=usuari.getEmail();
				String emailFrom=empresa.getEmail();
				int result=comprobarMails(emailTo, emailFrom);
				
				// Si el resultat es que el destinatari no té mail informem de l'error
				if(result==DESTINATARIOSINMAIL) {
					
					model.addAttribute("error", "No es pot enviar el missatge perquè l'usuari no té mail");
					return "login";
					
				// Si la empresa no té mail informem de l'error	
				}else if(result==REMITENTESINMAIL) {
					
					model.addAttribute("error", "No es pot enviar el missatge perquè l'empresa no té mail");
					return "login";
				}
				
				// En aquest punt les adreces són correctes
				String link;
				// Generem el link i enviem el correu
				try {
					link = generarLink(usuari);					
					correoAsync.enviarCorreo(link, emailTo, emailFrom);		
					model.addAttribute("success", "En breu rebràs el link de recuperació al teu correu");
					
				} catch (Exception e) {
					
					// Si hi ha cap error informem de l'error
					model.addAttribute("error", "No s'ha pogut generar el link");
				}
			}
		}
		
		return "login";
	}
	
	/**
	 * Mètode de tipus GET que està mapejat a la url "/recoverPassword" i que rep per paràmetre un string amb el link per recuperar el password.	 * 
	 * @param link Link posat per l'usuari per poder recuperar la seva contrasenya
	 * @param model Model de la vista
	 * @return Si el link existeix i encara està actiu llavors carrega la pàgina crearPassword, en cas contrari carrega la pàgina login.
	 */
	@GetMapping("/recoverPassword/{link}")
	public String recuperarPassword(@PathVariable(value = "link") String link, Model model, RedirectAttributes flash) {
		
		PasswordRecover passwordRecover=passwordRecoverService.findByLink(link);
		if(passwordRecover==null) {			
		
			model.addAttribute("error", "Error, link inexistente");
			return "login";
		}
			
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
	
	/**
	 * Mètode de tipus POST que està mapejat a la url "/savePAssword" i que s'encarrega de guardar el nou password creat per
	 * l'usuari que recupera la contrasenya.
	 * @param usuari Usuari que recupera la contrasenya i que conté el nou password.
	 * @param model Model de la vista
	 * @param flash Instància per poder afegir missatges a la vista
	 * @param status Variable que controla l'estat en aquest cas de la variable usuari.
	 * @return Carrega la pàgina login
	 */
	@PostMapping("/savePassword")
	public String savePassword(Usuari usuari, Model model, RedirectAttributes flash, SessionStatus status ) {
		
		// Encriptem el password afegit per l'usuari i el sustituim a usuari
		String passwordEncriptado=passwordEncoder.encode(usuari.getPassword());
		usuari.setPassword(passwordEncriptado);	
		// Guardem l'usuari
		usuariService.save(usuari);		
		// Completem l'estat
		status.setComplete();
		
		return "/login";
	}
	
	/**
	 * Mètodes de tipus GET que només carrega la vista recuperarPassword
	 * @param model Model que enviem a la vista
	 * @return Carrega la pàgina recuperarPassword
	 */
	@GetMapping("/getUsername")
	public String IntroducirUsername(Model model) {
		
		return "recuperarPassword";
		
	}
	
	/**
	 * Métode que crea un link per l'usuari rebut per pàrametre, el guarda a la base de dades i el retorna.
	 * @param usuari Usuari que ens demana el link
	 * @return Link generat
	 * @throws Exception Tenim en compte que podría haver un error de repetició en grabar a la base de dades i com conseqüència
	 * ocurrís un error.
	 */
	public String generarLink(Usuari usuari) throws Exception {
		
		// Creem una nova instància de PasswordRecover
		PasswordRecover recover=new PasswordRecover();
		// Generem un link unic juntant l'username i un randomUUID.
		String link=usuari.getUsername()+UUID.randomUUID().toString();
		// Afegim la data d'avui, l'usuari i el link
		recover.setData(new Date());
		recover.setUsuari(usuari);
		recover.setLink(link);
		// Guardem el registre
		passwordRecoverService.save(recover);		
		
		// Retornem el link complet
		return PasswordRecover.LINKMAIL+link;
	}
	
	/**
	 * Mètode que comprova l'estat dels mails rebuts per paràmetre
	 * @param emailTo Adreça electrònica de destí
	 * @param emailFrom Adreça electrònica d'origen
	 * @return Retorna un sencer amb la constant que equival al resultat obtingut
	 */
	private int comprobarMails(String emailTo, String emailFrom) {
	
		if(emailTo==null)return DESTINATARIOSINMAIL;
		if(emailFrom==null)return REMITENTESINMAIL;
		
		return ENVIOCORRECTO;
	}

}
