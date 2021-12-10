package com.grup8.taskman.app.controllers;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Classe que controla el login tot i que la autenticació la fa la classe de configuració de seguretat.
 * @author Sergio Estenban Gutíerrez
 * @version 1.0.0
 */

@Controller
public class LoginController {
	
	/**
	 * Mètode que està mapejat a "/login". Aquesta url està indicada a la classe de configuració perquè
	 * la vingui a buscar quan es faci login.
	 * @param error Bandera que envia spring per indicar que ha hagut un error en fer login.
	 * @param logout Bandera que envia spring per indicar que l'usuari ha fet logout.
	 * @param model Model que s'envia a la vista
	 * @param principal Paràmetre que fa referència a l'usuari autenticat
	 * @param flash Instància que farem servir per enviar missatges al model
	 * @return Redirecciona a home si l'usuari ja estava autenticat, en cas contrari torna a cridar la vista login.
	 */
	@GetMapping("/login")
	public String login(@RequestParam(value="error", required=false) String error, 
			@RequestParam(value="error", required=false) String logout,
			Model model, Principal principal, RedirectAttributes  flash) {
		
		// Mirem si ja ha fet login, en aquest cas l'avisem i el redireccionem al seu menú principal
		if(principal!= null) {
			
			flash.addAttribute("info", "Ja ha iniciat sessió anteriorment!!!");			
			return "redirect:/";
		}
		
		// Si ha hagut cap error en fer login llavors la variable error no estarà buïda per tant indiquem l'error a l'usuari
		
		if(error!=null) {
						
			model.addAttribute("error", "Error en el login: Nombre de usuario o contraseña incorrectos");
		}
		
		// Si la variable logout no està buïda vol dir que l'usuari ha tancat sessió. L'avisem d'aquesta dada a la vista.
		
		if(logout!=null) {
			
			
			model.addAttribute("info", "Sesssió tancada correctament");
			
		}
		
		
		return "login";
	}

}
