package com.grup8.taskman.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grup8.taskman.app.domain.departaments.Departament;
import com.grup8.taskman.app.domain.usuaris.FiltreUsuaris;
import com.grup8.taskman.app.domain.usuaris.Rol;
import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.services.departament.IDepartamentService;
import com.grup8.taskman.app.services.rol.IRolService;
import com.grup8.taskman.app.services.usuari.IUsuariService;
import com.grup8.taskman.app.util.PageRender;

@Controller
@RequestMapping("/usuaris")
@SessionAttributes("usuari")
public class UsuariController {

	@Autowired
	IRolService rolService;

	@Autowired
	IDepartamentService departamentService;

	@Autowired
	IUsuariService usuariService;

	private FiltreUsuaris filtreUsuari = new FiltreUsuaris();
	private String titolBoto;
	private String titol;

	@GetMapping("/crear")
	public String crear(Model model) {

		titol = "Crear nou usuari";
		titolBoto = "crear Usuari";
		model.addAttribute("titol", titol);
		model.addAttribute("usuari", new Usuari());
		model.addAttribute("titolBoto", titolBoto);

		return "usuaris/crear";
	}

	@ModelAttribute("listaRoles")
	public List<Rol> listaRoles() {

		return rolService.findAll();
	}

	@ModelAttribute("listaDepartamentos")
	public List<Departament> listaDepartamentos() {

		return departamentService.findAll();
	}

	@PostMapping("/guardar")
	public String guardar(@Valid Usuari usuari, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		model.addAttribute("titol", titol);
		model.addAttribute("titolBoto", titolBoto);
		if (!comprobacionDni(usuari))
			result.rejectValue("dni", "usuari.dniExistente");

		if (result.hasErrors()) {
			flash.addFlashAttribute("error", "No ha sido posible guardar los datos");
			return "usuaris/crear";
		}

		usuari.setActivo(true);
		usuari.setPrivacidadFirmada(false);
		usuari.setPassword("1111");

		usuariService.save(usuari);
		status.setComplete();
		flash.addFlashAttribute("success", "Registro grabado con éxito");
		return "redirect:listar";
	}

	@GetMapping("/listar")
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page,	Model model) {

		System.out.println(filtreUsuari.isFiltrat());
	/*	filtreUsuari.setNom(nom);
		filtreUsuari.setCognoms(cognoms);
		filtreUsuari.setHistoric(historic);
		filtreUsuari.setDni(dni);
		filtreUsuari.setRol(rol);
		System.out.println("Historic: " + filtreUsuari.getHistoric());*/

		
		
		Pageable pageRequest = PageRequest.of(page, 4);
		
	//	filtreUsuari.actualizar();
		Page<Usuari> usuaris = this.getUsuaris(pageRequest);

		/*
		 * 
		 * boolean filtrado=true; if(keyword==null) {
		 * 
		 * usuaris=usuariService.findByActivo(true, pageRequest); filtrado=false;
		 * 
		 * }
		 */

		PageRender<Usuari> pageRender = new PageRender<Usuari>("/usuaris/listar", usuaris);

		model.addAttribute("titol", "Llistat d'usuaris");
		model.addAttribute("page", pageRender);
		model.addAttribute("filtreUsuari", filtreUsuari);
		model.addAttribute("usuaris", usuaris);
		

		return "usuaris/listar";
	}
	
	@PostMapping("/filtrar")
	public String filtrar(FiltreUsuaris filtreUsuari, Model model) {
		
		this.filtreUsuari=filtreUsuari;
	
		
		return "redirect:listar";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {

		if (id > 0) {

			Usuari user = usuariService.findById(id);
			if (user != null) {

				user.setActivo(false);
				usuariService.save(user);
				flash.addFlashAttribute("success",
						"Usuari " + user.getNombre() + " " + user.getApellidos() + " eliminat amb èxit");
			} else {

				flash.addFlashAttribute("error", "L'usuari amb id " + id + " no existeix a la base de dades");
			}

		} else {

			flash.addFlashAttribute("error", "L'id ha de ser més gran que 0");
		}

		return "redirect:/usuaris/listar";
	}

	@GetMapping("/actualizar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Usuari usuari = null;

		if (id > 0) {

			usuari = usuariService.findById(id);
			if (usuari == null) {
				flash.addFlashAttribute("error", "El ID de l'usuari no existeix a la BBDD");
				return "redirect:/usuaris/listar";
			}
		} else {

			flash.addFlashAttribute("error", "L'ID de l'usuari no pot ser 0");
			return "listar";
		}

		titolBoto = "Modificar Usuari";
		titol = "Modificar usuari";
		model.addAttribute("titol", titol);
		model.addAttribute("usuari", usuari);
		model.addAttribute("titolBoto", titolBoto);

		return "usuaris/crear";
	}

	private boolean comprobacionDni(Usuari usuario) {

		boolean result = true;
		Usuari usuariPerDni = usuariService.findByDni(usuario.getDni());
		if (usuariPerDni != null) {
			if (usuario.getId() != usuariPerDni.getId()) {

				result = false;
			}

		}

		return result;
	}

	
	Page<Usuari> getUsuaris(Pageable pageRequest) {
		
		Page<Usuari> usuaris=null;	
		
		/*boolean cambios=filtreUsuari.consultarCambios();
		
		
		if(cambios) {
			
	//		page=0;
			
		}*/
		
		
		
		
		int opcion=filtreUsuari.getFiltreUsuaris();
		System.out.println(opcion);

		switch (opcion) {

		case FiltreUsuaris.USUARIOACTIVOROLNO:
			
			usuaris = usuariService.findByActivo(true, pageRequest);
			break;
			
			
		case FiltreUsuaris.USUARIOACTIVOROLSI:
			
			usuaris = usuariService.findByActivoAndRol(true, filtreUsuari.getRol() ,pageRequest);
			break;
			
		case FiltreUsuaris.USUARIOHISTORICOROLNO:
			
			usuaris = usuariService.findAll(pageRequest);
			break;
		
		case FiltreUsuaris.USUARIOHISTORICOROLSI:
			
			usuaris = usuariService.findByRol(filtreUsuari.getRol() ,pageRequest);
			break;
			
		case FiltreUsuaris.USUARIOACTIVONOMBREROLSI:
			
			usuaris = usuariService.findByActivoAndRolAndNombreStartsWith(true, filtreUsuari.getRol(), filtreUsuari.getNom(), pageRequest);
			break;
		
		case FiltreUsuaris.USUARIOACTIVONOMBREROLNO:
			
			usuaris = usuariService.findByActivoAndNombreStartsWith(true, filtreUsuari.getNom(), pageRequest);
			break;
			
		case FiltreUsuaris.USUARIOHISTORICONOMBREROLSI:
			
			usuaris = usuariService.findByRolAndNombreStartsWith(filtreUsuari.getRol(), filtreUsuari.getNom(), pageRequest);
			break;			
					
		case FiltreUsuaris.USUARIOHISTORICONOMBREROLNO:
			
			usuaris = usuariService.findByNombreStartsWith(filtreUsuari.getNom(), pageRequest);
			break;
			
		case FiltreUsuaris.USUARIOACTIVOAPELLIDOROLSI:
			
			usuaris = usuariService.findByActivoAndRolAndApellidosStartsWith(true, filtreUsuari.getRol(), filtreUsuari.getCognoms(), pageRequest);
			break;
			
		case FiltreUsuaris.USUARIOACTIVOAPELLIDOROLNO:
			
			usuaris = usuariService.findByActivoAndApellidosStartsWith(true, filtreUsuari.getCognoms(), pageRequest);
			break;	
			
			
		case FiltreUsuaris.USUARIOHISTORICOAPELLIDOROLSI:
			
			usuaris = usuariService.findByRolAndApellidosStartsWith(filtreUsuari.getRol(), filtreUsuari.getCognoms(), pageRequest);
			break;
			
			
		case FiltreUsuaris.USUARIOHISTORICOAPELLIDOROLNO:
			
			usuaris = usuariService.findByApellidosStartsWith(filtreUsuari.getCognoms(), pageRequest);
			break;
			
			
		case FiltreUsuaris.USUARIOACTIVODNIROLSI:
			
			usuaris = usuariService.findByActivoAndRolAndDniStartsWith(true, filtreUsuari.getRol(), filtreUsuari.getDni(), pageRequest);
			break;
			
		case FiltreUsuaris.USUARIOACTIVODNIROLNO:
			
			usuaris = usuariService.findByActivoAndDniStartsWith(true, filtreUsuari.getDni(), pageRequest);
			break;
			
			
		case FiltreUsuaris.USUARIOHISTORICODNIROLSI:
			
			usuaris = usuariService.findByRolAndDniStartsWith(filtreUsuari.getRol(), filtreUsuari.getDni(), pageRequest);
			break;	
			
		case FiltreUsuaris.USUARIOHISTORICODNIROLNO:
			
			usuaris = usuariService.findByDniStartsWith(filtreUsuari.getDni(), pageRequest);
			break;		
			
		case FiltreUsuaris.USUARIOACTIVONOMBREYAPELLIDOROLSI:
			
			usuaris = usuariService.findByActivoAndRolAndNombreStartsWithAndApellidosStartsWith(true, filtreUsuari.getRol(), 
					filtreUsuari.getNom(), filtreUsuari.getCognoms(), pageRequest);
			break;	
			
			
		case FiltreUsuaris.USUARIOACTIVONOMBREYAPELLIDOROLNO:
			
			usuaris = usuariService.findByActivoAndNombreStartsWithAndApellidosStartsWith(true, filtreUsuari.getNom(), 
					filtreUsuari.getCognoms(), pageRequest);
			break;
			
			
		case FiltreUsuaris.USUARIOHISTORICONOMBREYAPELLIDOROLSI:
			
			usuaris = usuariService.findByRolAndNombreStartsWithAndApellidosStartsWith(filtreUsuari.getRol(), 
					filtreUsuari.getNom(), filtreUsuari.getCognoms(), pageRequest);
			break;
			
		case FiltreUsuaris.USUARIOHISTORICONOMBREYAPELLIDOROLNO:
			
			usuaris = usuariService.findByNombreStartsWithAndApellidosStartsWith(filtreUsuari.getNom(), 
					filtreUsuari.getCognoms(), pageRequest);
			break;	
			
		case FiltreUsuaris.USUARIOACTIVONOMBREYDNIROLSI:
			
			usuaris = usuariService.findByActivoAndRolAndNombreStartsWithAndDniStartsWith(true, filtreUsuari.getRol(), 
					filtreUsuari.getNom(), filtreUsuari.getDni(), pageRequest);
			break;	
			
		case FiltreUsuaris.USUARIOACTIVONOMBREYDNIROLNO:
			
			usuaris = usuariService.findByActivoAndNombreStartsWithAndDniStartsWith(true, filtreUsuari.getNom(), 
					filtreUsuari.getDni(), pageRequest);
			break;
			
		case FiltreUsuaris.USUARIOHISTORICONOMBREYDNIROLSI:
			
			usuaris = usuariService.findByRolAndNombreStartsWithAndDniStartsWith(filtreUsuari.getRol(), 
					filtreUsuari.getNom(), filtreUsuari.getDni(), pageRequest);
			break;
			
		case FiltreUsuaris.USUARIOHISTORICONOMBREYDNIROLNO:
			
			usuaris = usuariService.findByNombreStartsWithAndDniStartsWith(filtreUsuari.getNom(), 
					filtreUsuari.getDni(), pageRequest);
			break;	
			
		case FiltreUsuaris.USUARIOACTIVOAPELLIDOYDNIROLSI:
			
			usuaris = usuariService.findByActivoAndRolAndApellidosStartsWithAndDniStartsWith(true, filtreUsuari.getRol(), 
					filtreUsuari.getCognoms(), filtreUsuari.getDni(), pageRequest);
			break;	
			
		case FiltreUsuaris.USUARIOACTIVOAPELLIDOYDNIROLNO:
			
			usuaris = usuariService.findByActivoAndApellidosStartsWithAndDniStartsWith(true, filtreUsuari.getCognoms(), 
					filtreUsuari.getDni(), pageRequest);
			break;	
			
		case FiltreUsuaris.USUARIOHISTORICOAPELLIDOYDNIROLSI:
			
			usuaris = usuariService.findByRolAndApellidosStartsWithAndDniStartsWith(filtreUsuari.getRol(), 
					filtreUsuari.getCognoms(), filtreUsuari.getDni(), pageRequest);
			break;
			
		case FiltreUsuaris.USUARIOHISTORICOAPELLIDOYDNIROLNO:
			
			usuaris = usuariService.findByApellidosStartsWithAndDniStartsWith(filtreUsuari.getCognoms(), 
					filtreUsuari.getDni(), pageRequest);
			break;	
			
		case FiltreUsuaris.USUARIOACTIVONOMBREAPELLIDOYDNIROLSI:
			
			usuaris = usuariService.findByActivoAndRolAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(true, filtreUsuari.getRol(), 
					filtreUsuari.getNom(), filtreUsuari.getCognoms(), filtreUsuari.getDni(), pageRequest);
			break;	
			
			
		case FiltreUsuaris.USUARIOACTIVONOMBREAPELLIDOYDNIROLNO:
			
			usuaris = usuariService.findByActivoAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(true, filtreUsuari.getNom(), 
					filtreUsuari.getCognoms(), filtreUsuari.getDni(), pageRequest);
			break;
			
			
		case FiltreUsuaris.USUARIOHISTORICONOMBREAPELLIDOYDNIROLSI:
			
			usuaris = usuariService.findByRolAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(filtreUsuari.getRol(), 
					filtreUsuari.getNom(), filtreUsuari.getCognoms(), filtreUsuari.getDni(), pageRequest);
			break;
			
		case FiltreUsuaris.USUARIOHISTORICONOMBREAPELLIDOYDNIROLNO:
			
			usuaris = usuariService.findByNombreStartsWithAndApellidosStartsWithAndDniStartsWith(filtreUsuari.getNom(), 
					filtreUsuari.getCognoms(), filtreUsuari.getDni(), pageRequest);
			break;		
		

		default:

			usuaris = usuariService.findByActivo(true, pageRequest);
			break;
		}
		
		return usuaris;
	}

}
