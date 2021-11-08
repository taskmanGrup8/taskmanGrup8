package com.grup8.taskman.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Conrolador que gestionarà que correspondrà a /home. De moment nomès redirecciona al controlador index 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */

@Controller
public class HomeController {
	
	@GetMapping({"/", "/index", "/taskman"})
	public String home() {
		
		return "redirect:/taskman/index";
		
	}

}
