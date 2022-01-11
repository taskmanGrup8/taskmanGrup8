package com.grup8.taskman.app.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.grup8.taskman.app.domain.empreses.Empresa;
import com.grup8.taskman.app.domain.tasques.FaseExecutable;
import com.grup8.taskman.app.domain.tasques.Notificacion;
import com.grup8.taskman.app.domain.tasques.Orden;
import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.services.empresa.IEmpresaService;
import com.grup8.taskman.app.services.tasques.IFaseExecutableService;
import com.grup8.taskman.app.services.tasques.INotificacionService;
import com.grup8.taskman.app.services.tasques.IOrdenService;

/**
 * Classe que controla la gestió de notificacións. Està anotada amb @Controller i mapejada a la
 * url "/notificaciones". 
 * Aquesta classe permet editar, guardar i eliminar notificacións a més de mostrar la fase en progrès.
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping("/notificaciones")
@SessionAttributes("notificacio")
public class NotificacionController {
	
	// ATRIBUTS
	
	// Injectem el servei d'empreses per tenir accés a la taula empreses
	@Autowired
	IEmpresaService empresaService;
	
	// Injectem el servei de notificacions per tenir accés a la taula notificacions
	@Autowired
	INotificacionService notificacionService;
	
	// Injectem el servei de fases executables per tenir accés a la taula fases_executables
	@Autowired
	IFaseExecutableService faseExecutableService;
	
	// Injectem el servei d'ordres per tenir accés a la taula ordres
	@Autowired
	IOrdenService ordenService;
	
	private Empresa empresa;
	private String titolBoto;
	private String titol;
	private int cantidadPendiente;
	private Date fechaInici;
	private Date fechaFin;
	
	
	/**
	 * Mètode que ens permet eliminar una notificació
	 * @param id Id de la notificació que volem eliminar
	 * @param flash Variables que ens permet enviar missatges a la vista
	 * @return Redirecciona al llistat d'ordres o a perfil si no existeix l'empresa.
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {

		empresa=empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if(empresa==null)return "redirect:/";	
		// Si ja s'ha passat per llistar i el mètode és cridat des del navegador amb un número negatiu no ha de fer res
		// per tant ens assegurem que sigui més gran que 0.
		if (id > 0) {

			// Busquem la notificació a la base de dades
			Notificacion notificacion = notificacionService.findById(id);
			
			// Si la trobem l'eliminem
			if(notificacion!=null) {
				// Eliminem la notificació a la base de dades
				notificacionService.delete(notificacion);
				
				// Si l'hem esborrat hem de comprobar l'estat de les notificacions i bloquejos de fases i ordres.
				if(notificacionService.findById(id)!=null) {
					
					realizarComprobaciones(notificacion);					
					flash.addAttribute("success", "Notificació esborrada amb èxit");
					
				}else {
					
					flash.addAttribute("error", "La notificació no s'ha pogut eliminar");
				}
			}						

		}
		
		// Redireccionem a la vista listar		
		return "redirect:/ordenes/listar";
	}
	
	/**
	 * Mètode que crida a la vista editar passant-li les dades de la notificació que tè per id el rebut per paràmetre per
	 * poder ser modificat.
	 * @param id Id de la notificació que volem editar
	 * @param model És el model que passem a la vista
	 * @param flash Variable que fem servir per enviar missatges a les vistes.
	 * @return Si l'atribut empresa és null redireccionem a home, en cas contrari redireccionem a listar si l'id no
	 * existeix a la base de dades o cridem a la vista editar si el trobem.
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping("/actualizar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		
		empresa=empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if(empresa==null)return "redirect:/perfil";	
		
		Notificacion notificacio = new Notificacion();
		
		// Si ja s'ha passat per llistar i criden al mètode des del navegador el mètode funcionarà, per tant hem
		// de controlar que el paràmetre no sigui -1 per exemple, volem que sigui més gran que cero.
		
		if (id > 0) {
			// Busquem la notificació a la base de dades
			notificacio = notificacionService.findById(id);
			// Si no existeix a la base de dades redireccionem a llistar amb el missatge corresponent.
			if (notificacio == null) {
				flash.addFlashAttribute("error", "L'ID de la notificació no existeix a la BBDD");
				return "redirect:/ordenes/listar";
			}
		} else {
			
			// Si l'id és erroni redireccionem a listar amb el missatge corresponent.
			flash.addFlashAttribute("error", "L'ID ha de ser un número més gran que 0");
			return "redirect:/ordenes/listar";
		}
		
		// Canviem el titol i el text del bóto de la vista editar canviant els atributs corresponents		
		titol="Modificar notificació";
		titolBoto="Enviar dades";
		
		// Guardem les dates i la quantitat pendent
		fechaInici=notificacio.getDataInici();
		fechaFin=notificacio.getDataFin();
		cantidadPendiente=FaseExecutable.calcularCantidadPendiente(notificacio.getFase())+notificacio.getCantidad();
		
		// passem la notificació trobada, el titol, el text del botó i l'empresa a la vista.
		model.addAttribute("titol", titol);
		model.addAttribute("notificacio", notificacio);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);
		
		// Cridem a la vista editar
		return "notificacions/editar";
	}
	
	
	/**
	 * Métode que guarda les dades modificades a editar
	 * @param notificacio Notificació que volem guardar
	 * @param model És el model que passem a la vista
	 * @param flash Variable que ens permet passar missastges a la vista
	 * @param status Instància que controla l'estat de la variable notificació.
	 * @return Si les dades rebudes del formulari són correctes redirecciona a llistar d'ordres, en cas contrari crida la vista editar.
	 */
	@PostMapping("/result")
	public String guardar(Notificacion notificacio, Model model, RedirectAttributes flash,
			SessionStatus status) {		
		
		// Afegim el titol, el text del botó i l'empresa abans de gestionar res més
		// perquè ens fa falta per qualsevol dels casos.
		model.addAttribute("titol", titol);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);		
			
		// La quantitat ha de ser més gran que zero si no tornem a la vista editar
		if(notificacio.getCantidad()<1) {
			flash.addFlashAttribute("error", "La quantitat ha de ser superior a cero, en cas de voler posar cero millor esborra la notificació");
			return "notificacions/editar";
		}
		
		// Si la quantitat rebuda és més gran que la quantitat pendent informem de l'error i tornem a la vista editar
		if(notificacio.getCantidad()>cantidadPendiente) {
						
			flash.addFlashAttribute("error", "La quantitat no pot ser superior a la quantitat pendent");
			return "notificacions/editar";
		}
		
		// Ajustem les dates		
		notificacio.setDataInici(fechaInici);
		notificacio.setDataFin(fechaFin);
		// Guardem el registre
		if (notificacionService.save(notificacio) != null) {
			// Realitzem les comprobacions de notificacions i bloquejos
			realizarComprobaciones(notificacio);			
			flash.addFlashAttribute("success", "Registre guardat amb èxit");
			

		} else {

			// Afegim al model el missatge que no s'ha guardat correctament
			flash.addFlashAttribute("error", "El registre no s'ha pogut guardar a la base de dades");
		}

		// Confimem que s'ha completat tot el procès i que ja no cal que guardi les
		// dades de notificació.
		status.setComplete();
		// Redireccionem a /ordenes/listar.
		return "redirect:/ordenes/listar";
	}
	
	/**
	 * Mètode que carrega la vista progreso de la notificació rebuda per paràmetre
	 * @param id Id de la notificació de la qual volem carregar la vista progreso
	 * @param model Model que passem a la vista
	 * @param flash Variable que ens permet passar missatges a la vista 
	 * @return Si la empresa no existeix o no troba la notificació, redirecciona a perfil en cas contrari carrega la vista progreso  
	 */
	@GetMapping("/progreso/{id}")
	public String progreso(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash){
		
		empresa=empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if(empresa==null)return "redirect:/perfil";
		
		Notificacion notificacio=notificacionService.findById(id);
		if(notificacio==null) {
			
			flash.addAttribute("error", "L'id de la notificació no existeix a la base de dades");
			return "redirect:/perfil";
		}
		
		model.addAttribute("notificacio", notificacio);
		model.addAttribute("empresa", empresa);
		model.addAttribute("boton", "notificar");
		
		return "notificacions/progreso";
	}
	
	/**
	 * Métode que realitza les comprobacions de notificacions i bloquejos sobre les fases executables i ordre relacionades
	 * amb la notificació rebuda per paràmetre
	 * @param notificacio Notificació de la qual volem fer les comprobacions
	 */
	public void realizarComprobaciones(Notificacion notificacio) {
	
		// Si hem de cambiar la notificació de la fase executable
		if(FaseExecutable.comprobarNotificacion(notificacio.getFase())) {
			
			// Canviem el signe de la notificació de la fase i la guardem
			notificacio.getFase().setNotificada(!notificacio.getFase().isNotificada());
			faseExecutableService.save(notificacio.getFase());
			// Si s'han de repara bloquejos 
			if(Orden.comprobarBloqueos(notificacio.getFase().getOrden())) {
				// Reparem els bloquejos
				Orden.repararBloqueos(notificacio.getFase().getOrden());
			}				
		}
		
		// Si hem de cambiar la notificació de l'ordre
		if(Orden.comprobarNotificacion(notificacio.getFase().getOrden())) {
			
			// Reparem la notificació de l'ordre
			Orden.repararNotificacion(notificacio.getFase().getOrden());
		}
		
		// Guardem l'ordre haguem fet canvis o no
		ordenService.save(notificacio.getFase().getOrden());
		
		// Sí l'ordre està notificada i és cíclica llavors generem una nova ordre cíclica i la guardem
		if(notificacio.getFase().getOrden().isNotificada() && notificacio.getFase().getOrden().isCiclica()) {
		
			Orden ordenCiclica=Orden.generarOrdenCiclica(notificacio.getFase().getOrden());		
			if(ordenCiclica!=null)ordenService.save(ordenCiclica);		
		}
		
	}
	
	@ModelAttribute("usuariAutenticat")
	public Usuari getUsuariAuthenticat() {
		
		return Usuari.USUARIAUTENTICAT;
	}
	
	

}
