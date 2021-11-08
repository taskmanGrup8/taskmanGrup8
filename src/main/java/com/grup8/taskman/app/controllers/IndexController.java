package com.grup8.taskman.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador que redireccionarà cap al perfil de cada usuari un cop fet login. De moment nomès tenim
 * superadministrador.
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */

@Controller
@RequestMapping("/taskman")
public class IndexController {
	
		
	@GetMapping("/index")
	public String index() {		
		
		// De moment només tenim el superusuari actiu(nomès haurà un super usuari que es guardarà a la base de dades durant la instal.lació),
		// la resta d'usuaris no tenen accès per tant el redireccionem directament.
		
		return "redirect:/taskman/super/perfil";
	}
	
			
	

}
