package com.grup8.taskman.app.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.grup8.taskman.app.domain.departaments.Departament;
import com.grup8.taskman.app.services.departament.IDepartamentService;
import com.grup8.taskman.app.util.Utilidades;
import com.grup8.taskman.app.validators.DepartamentValidator;

@Controller
@RequestMapping("/departaments")
@SessionAttributes("departament")
public class DepartamentController {
	
	@Autowired
	IDepartamentService departamentService;
	
	@Autowired
	private DepartamentValidator departamentValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
		binder.addValidators(departamentValidator);
	}
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Long id, Model model) {
		
		Departament departament=departamentService.findById(id);
		model.addAttribute("departament", departament);
		model.addAttribute("titol", "Detalle departamento " + departament.getNombre());
		
		return "departaments/ver";
	}
	
	@GetMapping("/crear")
	public String crear(Model model) {
		
		model.addAttribute("titol", "Crear Departament");
		model.addAttribute("departament", new Departament());
		model.addAttribute("titolBoto", "Crear Departament");
		
		return "departaments/crear";
	}
	
	@PostMapping("/result")
	public String guardar(@Valid Departament departament, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
		
		model.addAttribute("titol", "Crear nou departament");
		model.addAttribute("titolBoto", "Crear Departament");
		
		if(result.hasErrors()) {		
			flash.addFlashAttribute("error", "No ha sido posible guardar los datos");		
			return "departaments/crear";
		}		
		
			
		departamentService.save(departament);
		
		status.setComplete();
		flash.addFlashAttribute("success", "Registro grabado con éxito");
				
		return "redirect:listar";
	}
	
	@GetMapping("/listar")
	public String listar(Model model, String keyword) {
		
		List<Departament> departaments=new ArrayList<>();
		boolean filtrado;
		if(keyword==null) {
			
			departaments=departamentService.findAll();
			filtrado=false;	
			
		}else {
			
			List<Departament> departamentosPorCodigo=departamentService.findByCodigoStartsWith(keyword);
			List<Departament> departamentosPorNombre=departamentService.findByNombreStartsWith(keyword);			
			departaments=Utilidades.combinarListasSinRepeticion(departamentosPorCodigo, departamentosPorNombre);
			filtrado=true;
		}
		
		model.addAttribute("titol", "Listado de departamentos");
		model.addAttribute("departaments", departaments);
		model.addAttribute("filtrado", filtrado);
		
		
		return "departaments/listar";
	}
	
	
	
	
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {

		
		if (id > 0) {

			Departament departament = departamentService.findById(id);
			departamentService.delete(departament);
						

		}
		
		return "redirect:/departaments/listar";
	}
	
	@GetMapping("/actualizar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Departament departament = null;

		if (id > 0) {

			departament = departamentService.findById(id);
			if (departament == null) {
				flash.addFlashAttribute("error", "El ID del departamento no existe en la BBDD");
				return "redirect:/departaments/listar";
			}
		} else {

			flash.addFlashAttribute("error", "El ID del departamento no puede ser 0");
			return "listar";
		}
		model.addAttribute("titol", "Actualizar Departamento");
		model.addAttribute("departament", departament);

		return "departaments/crear";
	}

}
