package com.grup8.taskman.app.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.grup8.taskman.app.domain.departaments.Departament;
import com.grup8.taskman.app.domain.empreses.Empresa;
import com.grup8.taskman.app.domain.tasques.FaseExecutable;
import com.grup8.taskman.app.domain.tasques.Notificacion;
import com.grup8.taskman.app.domain.tasques.Orden;
import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.services.empresa.IEmpresaService;
import com.grup8.taskman.app.services.tasques.IOrdenService;
import com.grup8.taskman.app.services.usuari.IUsuariService;
import com.grup8.taskman.app.util.Utilidades;

/**
 * Controlador que correspon a home. Contè el mètode perfil que crida al menú de cada usuari. 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.1
 *
 */

@Controller
@RequestMapping("/")
public class HomeController {
			
	Empresa empresa;
	// Injectem el service de empresa per poder fer us de la taula empreses de la base de dades
	@Autowired
	IEmpresaService empresaService;
	
	@Autowired
	IOrdenService ordenService;
	
	@Autowired
	IUsuariService usuariService;
		
	
	/**
	 * Mètode que crida a la vista menu de qualsevol usuari autenticat.
	 * @param model És el model que passem a la vista
	 * @return Crida a la vista "perfil/menu" si la empresa existeix, en cas contrari redirecciona a /empreses/crear.
	 */
	@Secured("ROLE_USER")
	@GetMapping({"/", "/perfil"})
	public String perfil(Model model, Authentication authentication) {
		
		empresa=empresaService.findById(1);
		if(empresa==null) {			
			
			return "redirect:/empreses/crear";
		}
		
		Notificacion notificacioActual=null;
		if(Usuari.USUARIAUTENTICAT==null) {
			String username=authentication.getName();
			Usuari.USUARIAUTENTICAT=usuariService.findByUsername(username);
			notificacioActual=Usuari.USUARIAUTENTICAT.getNotificacionActual();
		}else {
			
			Usuari usuari=usuariService.findById(Usuari.USUARIAUTENTICAT.getId());
			notificacioActual=usuari.getNotificacionActual();
		}
	
		model.addAttribute("empresa", empresa);
		if(!Usuari.USUARIAUTENTICAT.isPrivacidadFirmada()) {
			
			model.addAttribute("titol", "Política de privacitat");
			return "empresas/politica";
		}
		
		List<Orden> ordenes=getOrdenes();
		ordenes=filtrarPorDepartamento(ordenes);
		Collections.sort(ordenes, Collections.reverseOrder());
		
		model.addAttribute("ordres", ordenes);
		model.addAttribute("notificacioActual", notificacioActual);
		
		return "perfil/menu";
		
	}
	
	
	@ModelAttribute("usuariAutenticat")
	public Usuari getUsuariAuthenticat() {
		
		return Usuari.USUARIAUTENTICAT;
	}
	
	@GetMapping({"/index"})
	public String index(Model model) {
		
		return "index";
	}
	
private List<Orden> getOrdenes(){
		
		List<Orden> ordenes=ordenService.findByNotificada(false);
		List<Orden> ordenesEntreFechas=ordenService.findByEntreDates(Utilidades.calcularData(-15), Utilidades.calcularData(15));
		ordenes.retainAll(ordenesEntreFechas);
		return ordenes;
		
	}
	
	private List<Orden> filtrarPorDepartamento(List<Orden> ordenes){
		
		List<Orden> ordenesFiltradas=new ArrayList<>();
	
		
		for(Orden orden: ordenes) {
						
			if(ordenPoseeDepartamento(orden)) {
				
				ordenesFiltradas.add(orden);
			}	
		}			
		
		
		return ordenesFiltradas;
		
	}
	
	private boolean ordenPoseeDepartamento(Orden orden) {
		Usuari usuari=usuariService.findById(Usuari.USUARIAUTENTICAT.getId());
		List<Departament> departaments=usuari.getDepartaments();
		
		for(FaseExecutable fase: orden.getFases()) {
			
			for(Departament departament: departaments) {
				
				if(fase.getFase().getFase().getDepartament().equals(departament))return true;
				
			}
		}
	
		return false;
	}	
	
	

}
