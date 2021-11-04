package com.grup8.taskman.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.grup8.taskman.app.domain.empreses.Empresa;
import com.grup8.taskman.app.services.empresa.IEmpresaService;


@Controller
@RequestMapping("/taskman/super")
public class SuperUsuariController {
	
	Empresa empresa;
	
	@Autowired
	IEmpresaService empresaService;
	
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
