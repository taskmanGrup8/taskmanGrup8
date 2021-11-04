package com.grup8.taskman.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


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
