package com.grup8.taskman.app.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.grup8.taskman.app.domain.departaments.Departament;
import com.grup8.taskman.app.domain.empreses.Empresa;
import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.services.departament.IDepartamentService;
import com.grup8.taskman.app.services.empresa.IEmpresaService;
import com.grup8.taskman.app.util.Utilidades;

@Controller
@RequestMapping("/departaments")
@SessionAttributes("departament")
public class DepartamentController {
	
	@Autowired
	IDepartamentService departamentService;	
	
	@Autowired
	IEmpresaService empresaService;
	
	
	
	private String titolBoto;
	private String titol;
	private Empresa empresa;
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Long id, Model model) {
		
		
		if(empresa==null)return "redirect:/";
		Departament departament=departamentService.findById(id);
		if(departament==null)return "redirect:listar";
		List<Usuari> usuaris=departament.getUsuaris();		
		model.addAttribute("departament", departament);
		model.addAttribute("titol", "Detall departament " + departament.getNombre());
		model.addAttribute("boton","Veure Llistat d'usuaris");
		model.addAttribute("usuaris",usuaris);
		model.addAttribute("empresa",empresa);
		
		return "departaments/ver";
	}
	
	@GetMapping("/crear")
	public String crear(Model model) {
		
		if(empresa==null)return "redirect:/";		
		titol="Crear Departament";
		titolBoto="Crear Departament";
		
		model.addAttribute("titol", titol);
		model.addAttribute("departament", new Departament());
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);
		
		return "departaments/crear";
	}
	
	@PostMapping("/result")
	public String guardar(@Valid Departament departament, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
		
		model.addAttribute("titol", titol);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);
		
		if (!comprobacionCodigo(departament))
			result.rejectValue("codigo", "departament.codigoExistente");
		
		if(!comprobacionNombre(departament))
			result.rejectValue("codigo", "departament.nombreExistente");
		
		if(result.hasErrors()) {		
			flash.addFlashAttribute("error", "No ha estat possible guardar les dades");		
			return "departaments/crear";
		}		
		
			
		departamentService.save(departament);
		
		status.setComplete();
		flash.addFlashAttribute("success", "Registre guardat amb èxit");
				
		return "redirect:listar";
	}
	
	@GetMapping("/listar")
	public String listar(Model model, String keyword) {
		
		if(empresa==null)empresa=empresaService.findById(1);
		if(empresa==null)return "redirect:/";
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
		model.addAttribute("empresa",empresa);
		
		
		return "departaments/listar";
	}
	
	
	
	
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {

		if(empresa==null)return "redirect:/";
		
		if (id > 0) {

			Departament departament = departamentService.findById(id);
			departamentService.delete(departament);
						

		}
		
		return "redirect:/departaments/listar";
	}
	
	@GetMapping("/actualizar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		
		if(empresa==null)return "redirect:/";
		Departament departament = null;

		if (id > 0) {

			departament = departamentService.findById(id);
			if (departament == null) {
				flash.addFlashAttribute("error", "El ID del departament no existeix a la BBDD");
				return "redirect:/departaments/listar";
			}
		} else {

			flash.addFlashAttribute("error", "El ID del departament no pot ser 0");
			return "listar";
		}
		
		titol="Actualitzar Departament";
		titolBoto="Actualitzar Departament";
		model.addAttribute("titol", titol);
		model.addAttribute("departament", departament);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);

		return "departaments/crear";
	}
	
	private boolean comprobacionCodigo(Departament departament) {

		boolean result = true;
		Departament departamentPerCodi=departamentService.findByCodi(departament.getCodigo().toUpperCase());
		
		if(departamentPerCodi!=null) {
						
			if(departament.getId()!=departamentPerCodi.getId()) {
				result=false;			
			}
			
		}			

		return result;
	}
	
	private boolean comprobacionNombre(Departament departament) {
		
		boolean result=true;
		Departament departamentPerNom=departamentService.findByNom(departament.getNombre());
		
		if(departamentPerNom!=null) {
			
			if(departament.getId()!=departamentPerNom.getId()) {
				result=false;
			}
		
		}	
		
		return result;
	}

}
