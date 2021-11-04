package com.grup8.taskman.app.controllers;

import java.io.IOException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.grup8.taskman.app.domain.empreses.Empresa;
import com.grup8.taskman.app.services.empresa.IEmpresaService;
import com.grup8.taskman.app.services.imatges.IImatgesHandlerService;

@Controller
@RequestMapping("/empreses")
@SessionAttributes("empresa")
public class EmpresaController {
	
	
	
	@Autowired
	IEmpresaService empresaService;
	
	@Autowired
	IImatgesHandlerService imatgesService;
	
	private String titolBoto;
	private String titol;
	
	@GetMapping("/crear")
	public String crear(Model model) {
				
		titol="Crear Empresa";
		titolBoto="Crear Empresa";
		model.addAttribute("titol", titol);
		model.addAttribute("boto", titolBoto);
		model.addAttribute("empresa", new Empresa());
		
		
		return "empresas/crear";
	}
	
	
	@PostMapping("/result")
	public String guardar(@Valid Empresa empresa, BindingResult result, Model model, @RequestParam("file") MultipartFile logo, 
			RedirectAttributes flash, SessionStatus status) {
		
		
		model.addAttribute("titol", titol);
		model.addAttribute("titolBoto", titolBoto);
		
		if(result.hasErrors()) {		
			flash.addFlashAttribute("error", "No ha estat possible guardar les dades");		
			return "empresas/crear";
		}		
		
		if(!logo.isEmpty()) {
			
			if(empresa.getId()!=null && empresa.getId()>0 && empresa.getLogo()!=null && empresa.getLogo().length()>0) {
				
				imatgesService.delete(empresa.getLogo());
			}
			
			String uniqueFilename=null;
			try {
				uniqueFilename=imatgesService.copy(logo);
				flash.addAttribute("info", "Imatge gravada amb èxit");
				
			}catch(IOException e) {
				
				flash.addAttribute("error", "Nos'ha pogut guardar la imatge");
			}
			
			empresa.setLogo(uniqueFilename);
			
		}
			
		empresaService.save(empresa);
		
		status.setComplete();
		flash.addFlashAttribute("success", "Registro grabado con éxito");
				
	//	return "redirect:listar";
		
		return "redirect:/taskman/super/perfil";
	}
	
	@GetMapping("/actualizar")
	public String actualizar(Model model) {
		
		Empresa empresa=empresaService.findById(1);
		if(empresa==null)return "redirect:crear";
		
		titol="Actualitzar Empresa";
		titolBoto="Actualitzar Empresa";
		model.addAttribute("titol", titol);
		model.addAttribute("boto", titolBoto);
		model.addAttribute("empresa", empresa);
		
		
		return "empresas/crear";		
		
	}

}
