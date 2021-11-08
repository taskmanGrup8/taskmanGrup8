package com.grup8.taskman.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.grup8.taskman.app.domain.empreses.Empresa;
import com.grup8.taskman.app.services.empresa.IEmpresaService;

/**
 * Controlador temporal realitzat per que aquesta TEA tingui funcionalitat. Correspondría al perfil del super administrador
 * encara que de moment faltarien coses que no estaven incorporades a l'sprint 1
 * @author Sergio Esteban Gutiérrez
 *
 */

@Controller
@RequestMapping("/taskman/super")
public class SuperUsuariController {
	
	Empresa empresa;
	// Injectem el service de empresa per poder fer us de la taula empreses de la base de dades
	@Autowired
	IEmpresaService empresaService;
	
	/**
	 * Mètode que crida a la vista menu del superusuari.
	 * @param model És el model que passem a la vista
	 * @return Crida a la vista "superadministrador/menu" si la empresa existeix, en cas contrari redirecciona a /empreses/crear.
	 */
	@GetMapping("/perfil")
	public String perfil(Model model) {
		
		empresa=empresaService.findById(1);
		if(empresa==null) {			
			
			return "redirect:/empreses/crear";
		}
		
		// Com de moment no hem implementat els permissos cridem al menú sense tenir en compte qui ho ha fet
		
		model.addAttribute("empresa", empresa);
		return "superadministrador/menu";
		
	}
	
			
	

}
