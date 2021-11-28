package com.grup8.taskman.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.grup8.taskman.app.domain.empreses.Empresa;
import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.services.empresa.IEmpresaService;

/**
 * Controlador que correspon a home. Contè el mètode perfil que crida al menú de cada usuari. 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.1
 *
 */

@Controller
public class HomeController {
			
	Empresa empresa;
	// Injectem el service de empresa per poder fer us de la taula empreses de la base de dades
	@Autowired
	IEmpresaService empresaService;
	
	
	
	/**
	 * Mètode que crida a la vista menu de qualsevol usuari autenticat.
	 * @param model És el model que passem a la vista
	 * @return Crida a la vista "perfil/menu" si la empresa existeix, en cas contrari redirecciona a /empreses/crear.
	 */
	@Secured("ROLE_USER")
	@GetMapping({"/", "/index", "/taskman","/perfil"})
	public String perfil(Model model, Authentication authentication) {
		
		
		
	
		
		empresa=empresaService.findById(1);
		if(empresa==null) {			
			
			return "redirect:/empreses/crear";
		}
		
		// Com de moment no hem implementat els permissos cridem al menú sense tenir en compte qui ho ha fet
		model.addAttribute("empresa", empresa);
		return "perfil/menu";
		
	}
	
	
	@ModelAttribute("usuariAutenticat")
	public Usuari getUsuariAuthenticat() {
		
		return Usuari.USUARIAUTENTICAT;
	}
	
	
	

}
