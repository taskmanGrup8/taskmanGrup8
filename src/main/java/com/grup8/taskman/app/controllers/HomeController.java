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
 * @version 1.0.2
 *
 */

@Controller
@RequestMapping("/")
public class HomeController {
		
	// ATRIBUTS
	
	Empresa empresa;
	
	// Injectem el service de empresa per poder fer us de la taula empreses de la base de dades
	@Autowired
	IEmpresaService empresaService;
	
	// Injectem el service d'orden per poder fer us de la taula ordres de la base de dades
	@Autowired
	IOrdenService ordenService;
	
	// Injectem el service d'usuari per poder fer us de la taula usuaris de la base de dades
	@Autowired
	IUsuariService usuariService;
		
	
	/**
	 * Mètode que crida a la vista menu de qualsevol usuari autenticat. Si l'usuari autenticat no ha signat la politica
	 * de privacitat, automàticament l'envia perquè la pugui signar.
	 * @param model És el model que passem a la vista
	 * @param authentication Fa referència a l'usuari autenticat
	 * @return Crida a la vista "perfil/menu" si la empresa existeix, en cas contrari redirecciona a /empreses/crear.
	 */
	@Secured("ROLE_USER")
	@GetMapping({"/", "/perfil"})
	public String perfil(Model model, Authentication authentication) {
		
		// Si la empresa no està creada redireccionem perquè es pugui crear
		empresa=empresaService.findById(1);
		if(empresa==null) {			
			
			return "redirect:/empreses/crear";
		}
		
		// Cerquem si l'usuari autenticat està treballant a alguna notificació
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
		// Si l'usuari no té la política de privacitat signada redireccionem perquè ho pugui fer
		if(!Usuari.USUARIAUTENTICAT.isPrivacidadFirmada()) {
			
			model.addAttribute("titol", "Politica de privacitat");
			model.addAttribute("aceptar", true);
			return "empresas/politica";
		}
		
		// Preparem el llistat d'ordres que té que veure l'usuari
		List<Orden> ordenes=getOrdenes();
		ordenes=filtrarPorDepartamento(ordenes);
		activarBloqueos(ordenes);
		Collections.sort(ordenes, Collections.reverseOrder());
		
		// Afegim les dades necessaries a la vista
		model.addAttribute("ordres", ordenes);
		model.addAttribute("notificacioActual", notificacioActual);
		
		// Cridem la vista menu
		return "perfil/menu";
		
	}
	
	
	@ModelAttribute("usuariAutenticat")
	public Usuari getUsuariAuthenticat() {
		
		return Usuari.USUARIAUTENTICAT;
	}
	
	/**
	 * Mètode que busca les ordres no notificades en un rang de dates de 15 dies enrera a 15 dies en endavant
	 * @return Retorna la llista trobada.
	 */
	private List<Orden> getOrdenes(){
		
		List<Orden> ordenes=ordenService.findByNotificada(false);
		List<Orden> ordenesEntreFechas=ordenService.findByEntreDates(Utilidades.calcularData(-15), Utilidades.calcularData(15));
		ordenes.retainAll(ordenesEntreFechas);
		return ordenes;
		
	}
	
	/**
	 * Mètode que rep una llista d'ordres i en retorna una filtrada pels departaments on pertany l'usuari autenticat
	 * @param ordenes
	 * @return
	 */
	private List<Orden> filtrarPorDepartamento(List<Orden> ordenes){
		
		List<Orden> ordenesFiltradas=new ArrayList<>();
		
		for(Orden orden: ordenes) {
						
			if(ordenPoseeDepartamento(orden)) {
				
				ordenesFiltradas.add(orden);
			}	
		}		
		
		return ordenesFiltradas;		
	}
	
	/**
	 * Mètode que verifica sí una ordre te fases als departaments de l'usuari autenticat
	 * @param orden Ordre que volem verificar
	 * @return Retorna true si cap fase pertany a algun dels departaments de l'usuari i false en cas contrari.
	 */
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
	
	/**
	 * Mètode que marca una ordre com bloquejada si totes les fases executables d'aquesta
	 * ordre que pertanyen a algun dels departaments de l'usuari autenticat estan bloquejades.
	 * @param ordenes Llistat d'ordres que volem verificar per marcar
	 */
	private void activarBloqueos(List<Orden> ordenes) {
		Usuari usuari=usuariService.findById(Usuari.USUARIAUTENTICAT.getId());
		List<Departament> departaments=usuari.getDepartaments();
		for(Orden orden: ordenes) {
			
			if(algunaFaseDesbloqueada(orden, departaments)) {
				orden.setBloqueada(false);
			}else {
				
				orden.setBloqueada(true);
			}
		}
		
	}
	
	/**
	 * Mètode que verifica si una ordre te alguna fase executable a la llista de departaments rebuda per paràmetre
	 * @param orden Ordre que volem comprovar
	 * @param departaments Departaments on té que pertanyer alguna de les fases
	 * @return Retorna true si hi pertany i false en cas contrari.
	 */
	private boolean algunaFaseDesbloqueada(Orden orden, List<Departament> departaments) {
		
		for(FaseExecutable fase: orden.getFases()) {
			
			for(Departament departament: departaments) {
				
				if(fase.getFase().getFase().getDepartament().equals(departament) && !fase.isBloqueada())return true;
			}
		}
		
		return false;
	}	

}
