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

@Controller
@RequestMapping("/fasesExecutables")
public class FaseExecutableController {

	@Autowired
	IOrdenService ordenService;

	@Autowired
	IFaseExecutableService faseExecutableService;

	@Autowired
	IEmpresaService empresaService;

	@Autowired
	IUsuariService usuariService;

	@Autowired
	INotificacionService notificacionService;

	private Empresa empresa;

	@Secured("ROLE_USER")
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		// Si la variable empresa encara és igual a null la cerquem
		if (empresa == null)
			empresa = empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if (empresa == null)
			return "redirect:/perfil";

		
		Orden orden=ordenService.findById(id);
		if(orden==null) {
			
			flash.addAttribute("error", "L'ordre indicada no existeix");
			return "redirect:/perfil";
		}
		FaseExecutable fase=getFase(orden.getFases());
		
		
		// Si no trobem l'usuari tornem a llistar
		
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

	@PostMapping("/notificar")
	public String notificar(Model model, int cantidad, RedirectAttributes flash) {

		Usuari usuari = usuariService.findById(Usuari.USUARIAUTENTICAT.getId());
		Notificacion notificacio = usuari.getNotificacionActual();
		if (notificacio == null) {

			flash.addAttribute("error", "No estàs treballant a cap notificació");
			return "redirect:/perfil";
		}

		int cantidadPendiente = FaseExecutable.calcularCantidadPendiente(notificacio.getFase());

		if (cantidad > cantidadPendiente) {
			System.out.println("Error en cantidad, cantidad pendiente: "+cantidadPendiente);
			flash.addAttribute("error", "La quantitat a notificar no pot ser superior a " + cantidadPendiente);
			return "redirect:/notificaciones/progreso/" + notificacio.getId();
		}

		notificacio.setCantidad(cantidad);
		notificacio.setDataFin(new Date());
		notificacionService.save(notificacio);
		usuari.setNotificacionActual(null);
		usuariService.save(usuari);

		if (cantidad == cantidadPendiente) {

			notificacio.getFase().setNotificada(true);

			FaseExecutable faseSiguiente = FaseExecutable.desbloquearSiguienteFase(notificacio.getFase());
			if (faseSiguiente != null) {
							
				faseExecutableService.save(faseSiguiente);
				

			}else {
				
				notificacio.getFase().getOrden().setNotificada(true);
				ordenService.save(notificacio.getFase().getOrden());
				if(notificacio.getFase().getOrden().isCiclica()) {
					
					Orden ordenCiclica=Orden.generarOrdenCiclica(notificacio.getFase().getOrden());
					
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
	
	private FaseExecutable getFase(List<FaseExecutable> fases) {
		
		List<FaseExecutable> fasesDepartaments=new ArrayList<>();
		Usuari usuari=usuariService.findById(Usuari.USUARIAUTENTICAT.getId());
		List<Departament> departaments=usuari.getDepartaments();
		for(FaseExecutable fase: fases) {
			
			for(Departament departament: departaments) {
				
				if(fase.getFase().getFase().getDepartament().getId()==departament.getId()) {
					
					fasesDepartaments.add(fase);
					break;
				}
			}
		}
		
		FaseExecutable fase=null;
		
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
