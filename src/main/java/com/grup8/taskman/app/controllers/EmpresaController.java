package com.grup8.taskman.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.grup8.taskman.app.domain.empreses.Empresa;
import com.grup8.taskman.app.services.empresa.IEmpresaService;

@Controller
@RequestMapping("/empresas")
@SessionAttributes("empresa")
public class EmpresaController {
	
	@Autowired
	IEmpresaService empresaService;
	
	private String titolBoto;
	private String titol;
	
	@GetMapping("/crear")
	public String crear(Model model) {
		
		if(empresaService.findById(1)==null)return null;
		
		titol="Crear Empresa";
		titolBoto="Crear Empresa";
		model.addAttribute("titol", titol);
		model.addAttribute("boto", titolBoto);
		model.addAttribute("empresa", new Empresa());
		
		
		return "empresas/crear";
	}
	
	
	@PostMapping("/result")
	public String guardar(@Valid Empresa empresa, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
		
		
		model.addAttribute("titol", titol);
		model.addAttribute("titolBoto", titolBoto);
		
		if(result.hasErrors()) {		
			flash.addFlashAttribute("error", "No ha sido posible guardar los datos");		
			return "departaments/crear";
		}		
		
			
		empresaService.save(empresa);
		
		status.setComplete();
		flash.addFlashAttribute("success", "Registro grabado con éxito");
				
	//	return "redirect:listar";
		
		return null;
	}
	
	@GetMapping("/actualizar")
	public String actualizar(Model model) {
		
		Empresa empresa=empresaService.findById(1);
		if(empresa==null)return null;
		
		titol="Actualitzar Empresa";
		titolBoto="Actualitzarr Empresa";
		model.addAttribute("titol", titol);
		model.addAttribute("boto", titolBoto);
		model.addAttribute("empresa", empresa);
		
		
		return "empresas/crear";		
		
	}

}
