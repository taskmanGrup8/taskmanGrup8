package com.grup8.taskman.app.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grup8.taskman.app.domain.departaments.Departament;
import com.grup8.taskman.app.domain.empreses.Empresa;
import com.grup8.taskman.app.domain.tasques.FaseExecutable;
import com.grup8.taskman.app.domain.tasques.Notificacion;
import com.grup8.taskman.app.domain.tasques.Orden;
import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.services.empresa.IEmpresaService;
import com.grup8.taskman.app.services.tasques.IFaseExecutableService;
import com.grup8.taskman.app.services.tasques.INotificacionService;
import com.grup8.taskman.app.services.tasques.IOrdenService;
import com.grup8.taskman.app.services.usuari.IUsuariService;

/**
 * Classe que controla la gestió de les vistes relacionades amb les fases executables.
 * Està anotada com @Controller i està mapejada a la url "/fasesExecutables"
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 */
@Controller
@RequestMapping("/fasesExecutables")
public class FaseExecutableController {

	// Injectem el servei d'ordres per tenir accés a la taula ordres
	@Autowired
	IOrdenService ordenService;

	// Injectem el servei de fases executables per tenir accés a la taula fases_executables
	@Autowired
	IFaseExecutableService faseExecutableService;

	// Injectem el servei d'empreses per tenir accés a la taula empreses
	@Autowired
	IEmpresaService empresaService;

	// Injectem el servei d'usuaris per tenir accés a la taula usuaris
	@Autowired
	IUsuariService usuariService;

	// Injectem el servei de notificacions per tenir accés a la taula notificacions
	@Autowired
	INotificacionService notificacionService;

	private Empresa empresa;

	/**
	 * Mètode que crida a la vista /fasesExecutables/ver passant la primera fase desbloquejada de l'ordre 
	 * passada per paràmetre que pertany al departament de l'usuari
	 * @param id Id de l'ordre de la qual volem veure la fase.
	 * @param model Model que passem a la vista
	 * @param flash Instància que ens permet passar missatges a la vista
	 * @return Si tot és correcte carrega la pàgina fasesExecutables/ver, en cas contrari redirecciona a perfil.
	 */
	@Secured("ROLE_USER")
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		empresa = empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if (empresa == null)
			return "redirect:/perfil";

		// Cerquem l'ordre
		Orden orden=ordenService.findById(id);
		
		// Si és null notifiquem l'error i redireccionem a perfil
		if(orden==null) {
			
			flash.addAttribute("error", "L'ordre indicada no existeix");
			return "redirect:/perfil";
		}
		
		// Busquem la primera fase desbloquejada pendent al departament de l'usuari actiu
		FaseExecutable fase=getFase(orden.getFases());
		
		
		// Si no trobem cap tornem a perfil		
		if (fase == null) {
			flash.addAttribute("error", "Aquesta ordre no té fases pendents a aquest departament");
			return "redirect:/perfil";
		}

		// Afegim els atributs al model

		model.addAttribute("titol", "Detall fase " + fase.getFase().getFase().getNombre());
		model.addAttribute("boton", "Iniciar");
		model.addAttribute("boton2", "Tornar");
		model.addAttribute("fase", fase);
		model.addAttribute("empresa", empresa);

		return "fasesExecutables/ver";
	}

	/**
	 * Mètode que s'encarrega de notificar les fases executables i ordres.
	 * @param model Model que pasem a la vista
	 * @param cantidad Quantitat que l'usuari vol notificar de la fase on està treballant
	 * @param flash Variable que ens permet passar missatges a la vista
	 * @return Si la quantitat no supera la quantitat pendent redirecciona a perfil, en cas contrari redirecciona
	 * a /notificaciones/progreso
	 */
	@PostMapping("/notificar")
	public String notificar(Model model, int cantidad, RedirectAttributes flash) {

		// Obtenim l'usuari i la notificació en que està treballant
		Usuari usuari = usuariService.findById(Usuari.USUARIAUTENTICAT.getId());
		Notificacion notificacio = usuari.getNotificacionActual();
		
		// Si la notificació és null vol dir que no treballa a cap notificació
		if (notificacio == null) {

			flash.addAttribute("error", "No estàs treballant a cap notificació");
			return "redirect:/perfil";
		}

		// Calculem la quantitat pendent de notificar
		int cantidadPendiente = FaseExecutable.calcularCantidadPendiente(notificacio.getFase());

		// No és pot notificar més de la quantitat pendent, si és el cas redireccionem a  la vista progreso
		if (cantidad > cantidadPendiente) {
		
			flash.addAttribute("error", "La quantitat a notificar no pot ser superior a " + cantidadPendiente);
			return "redirect:/notificaciones/progreso/" + notificacio.getId();
		}

		// Afegim les dades a notificació i la guardem
		notificacio.setCantidad(cantidad);
		notificacio.setDataFin(new Date());
		notificacionService.save(notificacio);
		// Treiem la notificació a l'usuari
		usuari.setNotificacionActual(null);
		usuariService.save(usuari);

		// Si la quantitat coincideix amb el que queda per notificar llavors hem de notificar la fase
		if (cantidad == cantidadPendiente) {

			// Notifiquem la fase i la guardem
			notificacio.getFase().setNotificada(true);
			faseExecutableService.save(notificacio.getFase());

			// Desbloquejem la següent fase si es que hi ha
			FaseExecutable faseSiguiente = FaseExecutable.desbloquearSiguienteFase(notificacio.getFase());
			// Si hem desbloquejat una nova fase la guardem per actualitzar el canvi
			if (faseSiguiente != null) {
							
				faseExecutableService.save(faseSiguiente);
				
			// Si no hem desbloquejat cap fase vol dir que aquesta era la darrera, per tant hem de notificar l'ordre.
			}else {
				
				// Notifiquem l'ordre i la guardem
				notificacio.getFase().getOrden().setNotificada(true);
				ordenService.save(notificacio.getFase().getOrden());
				
				// Si la orden era ciclica generem una nova ordre cíclica a partir de la que acabem de notificar
				if(notificacio.getFase().getOrden().isCiclica()) {
					
					Orden ordenCiclica=Orden.generarOrdenCiclica(notificacio.getFase().getOrden());
					
					// Si l'hem pogut crear la guardem.
					if(ordenCiclica!=null)ordenService.save(ordenCiclica);
				}
				
			}

		}

		return "redirect:/perfil";

	}

	@GetMapping("/iniciar/{id}")
	public String iniciar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Usuari usuari = usuariService.findById(Usuari.USUARIAUTENTICAT.getId());
		if (usuari.getNotificacionActual() != null) {

			flash.addAttribute("error", "Ja estàs treballant a una notificació");
			return "redirect:/perfil";
		}

		if (id > 0) {

			FaseExecutable fase = faseExecutableService.findById(id);

			if (fase == null) {
				flash.addAttribute("error", "L'id de la fase no es troba a la base de dades");
				return "redirect:/perfil";

			} else {

				if (fase.isBloqueada()) {
					flash.addAttribute("error", "La fase encara està bloquejada");
					return "redirect:/perfil";
				}

				if (fase.isNotificada()) {

					flash.addAttribute("info", "La fase ja està notificada, només podràs afegir temps");
				}

				Notificacion notificacio = Notificacion.crear(usuari, fase);
				Notificacion notificacion = notificacionService.save(notificacio);
				usuari.setNotificacionActual(notificacio);
				usuariService.save(usuari);
				
				return "redirect:/notificaciones/progreso/" + notificacion.getId();
			}

		} else {

			flash.addAttribute("error", "L'id de la fase ha de ser més gran que zero");
			return "redirect:/perfil";
		}

	}
	
	/**
	 * Mètode que cerca la primera fase desbloquejada que pertany als departaments de l'usuari
	 * @param fases Llista de fases on fa la cerca
	 * @return Retorna la fase trobada o null si no en troba
	 */
	private FaseExecutable getFase(List<FaseExecutable> fases) {
		
		List<FaseExecutable> fasesDepartaments=new ArrayList<>();
		Usuari usuari=usuariService.findById(Usuari.USUARIAUTENTICAT.getId());
		
		// Agafem la llista de departaments de l'usuari autenticat
		List<Departament> departaments=usuari.getDepartaments();
		
		// En primer lloc filtrem les fases per les que si estàn als departaments de l'usuari
		for(FaseExecutable fase: fases) {
			
			for(Departament departament: departaments) {
				
				if(fase.getFase().getFase().getDepartament().getId()==departament.getId()) {
					
					fasesDepartaments.add(fase);
					break;
				}
			}
		}
		
		FaseExecutable fase=null;
		
		// De la llista filtrada cerquem la primera que no estigui ni bloquejada ni notificada
		for(FaseExecutable faseEx: fasesDepartaments) {
			
			if(!faseEx.isBloqueada() && !faseEx.isNotificada()) {
				
				fase=faseEx;
				break;
			}
		}
		
		return fase;		
	}

	@ModelAttribute("usuariAutenticat")
	public Usuari getUsuariAuthenticat() {

		return Usuari.USUARIAUTENTICAT;
	}
}
