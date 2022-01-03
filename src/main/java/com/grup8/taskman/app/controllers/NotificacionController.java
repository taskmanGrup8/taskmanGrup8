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

@Controller
@RequestMapping("/notificaciones")
@SessionAttributes("notificacio")
public class NotificacionController {
	
	@Autowired
	IEmpresaService empresaService;
	
	@Autowired
	INotificacionService notificacionService;
	
	@Autowired
	IFaseExecutableService faseExecutableService;
	
	@Autowired
	IOrdenService ordenService;
	
	private Empresa empresa;
	private String titolBoto;
	private String titol;
	private int cantidadPendiente;
	private Date fechaInici;
	private Date fechaFin;
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {

		// Si la variable empresa encara és igual a null la cerquem
		if(empresa==null)empresa=empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if(empresa==null)return "redirect:/";	
		// Si ja s'ha passat per llistar i el mètode és cridat des del navegador amb un número negatiu no ha de fer res
		// per tant ens assegurem que sigui més gran que 0.
		if (id > 0) {

			// Busquem el departament a la base de dades
			Notificacion notificacion = notificacionService.findById(id);
			
			// Si el trobem l'eliminem
			if(notificacion!=null) {
				// Eliminem el departament a la base de dades
				notificacionService.delete(notificacion);
				
				// Per saber si hem eliminat el registre, si no el trobem enviem un missatge com que s'he eliminat
				// correctament, en cas contrari enviem un missatge d'error
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
	 * Mètode que crida a la vista crear passant-li les dades del departament que tè per id el rebut per paràmetre per
	 * poder ser modificat.
	 * @param id Id del departament que volem editar
	 * @param model És el model que passem a la vista
	 * @param flash Variable que fem servir per enviar missatges a les vistes.
	 * @return Si l'atribut empresa és null redireccionem a home, en cas contrari redireccionem a listar si l'id no
	 * existeix a la base de dades o cridem a la vista crear si el trobem.
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping("/actualizar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		
		// Si la variable empresa encara és igual a null la cerquem
		if(empresa==null)empresa=empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if(empresa==null)return "redirect:/perfil";	
		
		Notificacion notificacio = new Notificacion();
		
		// Si ja s'ha passat per llistar i criden al mètode des del navegador el mètode funcionarà, per tant hem
		// de controlar que el paràmetre no sigui -1 per exemple, volem que sigui més gran que cero.
		
		if (id > 0) {
			// Busquem el departament a la base de dades
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
		
		// Canviem el titol i el text del bóto de la vista crear canviant els atributs corresponents		
		titol="Modificar notificació";
		titolBoto="Enviar dades";
		
		fechaInici=notificacio.getDataInici();
		fechaFin=notificacio.getDataFin();
		cantidadPendiente=FaseExecutable.calcularCantidadPendiente(notificacio.getFase())+notificacio.getCantidad();
		
		// passem el departament trobat, el titol, el text del botó i l'empresa a la vista.
		model.addAttribute("titol", titol);
		model.addAttribute("notificacio", notificacio);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);
		
		// Cridem a la vista crear
		return "notificacions/editar";
	}
	
	@PostMapping("/result")
	public String guardar(Notificacion notificacio, Model model, RedirectAttributes flash,
			SessionStatus status) {
		
		
		
		// Afegim el titol, el text del botó i l'empresa abans de gestionar res més
		// perquè ens fa falta per qualsevol dels casos.
		model.addAttribute("titol", titol);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);		
			
		// El temps ha de ser més gran que zero
		if(notificacio.getCantidad()<1) {
			flash.addFlashAttribute("error", "La quantitat ha de ser superior a cero, en cas de voler posar cero millor esborra la notificació");
			return "notificacions/editar";
		}
		
		if(notificacio.getCantidad()>cantidadPendiente) {
			
			System.out.println("Cantidad Pendiente: "+cantidadPendiente );
			
			flash.addFlashAttribute("error", "La quantitat no pot ser superior a la quantitat pendent");
			return "notificacions/editar";
		}
		
		notificacio.setDataInici(fechaInici);
		notificacio.setDataFin(fechaFin);
		// Guardem el registre
		if (notificacionService.save(notificacio) != null) {
			System.out.println("He guardado la notificación");
			realizarComprobaciones(notificacio);			
			flash.addFlashAttribute("success", "Registre guardat amb èxit");
			

		} else {

			// Afegim al model el missatge que no s'ha guardat correctament
			flash.addFlashAttribute("errors", "El registre no s'ha pogut guardar a la base de dades");
		}

		// Confimem que s'ha completat tot el procès i que ja no cal que guardi les
		// dades de fase.
		status.setComplete();
		// Redireccionem a /tasques/ver amb l'id de la tasca on pertany la fase.
		return "redirect:/ordenes/listar";
	}
	
	
	@GetMapping("/progreso/{id}")
	public String progreso(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash){
		
		// Si la variable empresa encara és igual a null la cerquem
		if(empresa==null)empresa=empresaService.findById(1);
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
	
	
	public void realizarComprobaciones(Notificacion notificacio) {
	
		
		if(FaseExecutable.comprobarNotificacion(notificacio.getFase())) {
			
			// Cambiamos el signo de la notificación
			notificacio.getFase().setNotificada(!notificacio.getFase().isNotificada());
			faseExecutableService.save(notificacio.getFase());
			if(Orden.comprobarBloqueos(notificacio.getFase().getOrden())) {
				
				Orden.repararBloqueos(notificacio.getFase().getOrden());
			}				
		}
		
		
		if(Orden.comprobarNotificacion(notificacio.getFase().getOrden())) {
			
			Orden.repararNotificacion(notificacio.getFase().getOrden());
		}
		
		ordenService.save(notificacio.getFase().getOrden());
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
