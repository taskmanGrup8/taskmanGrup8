package com.grup8.taskman.app.controllers;

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
import com.grup8.taskman.app.domain.tasques.FaseConTiempo;
import com.grup8.taskman.app.domain.tasques.Tasca;
import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.services.departament.IDepartamentService;
import com.grup8.taskman.app.services.empresa.IEmpresaService;
import com.grup8.taskman.app.services.tasques.IFaseConTiempoService;
import com.grup8.taskman.app.services.tasques.IFaseService;
import com.grup8.taskman.app.services.tasques.ITascaService;

@Controller
@RequestMapping("/fasesConTiempo")
@SessionAttributes("fase")
public class FaseConTiempoController {

	// ATRIBUTS

	// Injectem el servei de departaments per poder realitzar accions a la taula
	// departaments.
	@Autowired
	IDepartamentService departamentService;

	// Injectem el servei de fases per poder realitzar accions a la taula fases.
	@Autowired
	IFaseService faseService;

	// Injectem el servei de empresa per poder determinar si ja existeix la empresa.
	@Autowired
	IEmpresaService empresaService;

	@Autowired
	IFaseConTiempoService faseConTiempoService;

	@Autowired
	ITascaService tascaService;

	private String titolBoto;
	private String titol;
	private Empresa empresa;

	@Secured("ROLE_ADMIN")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {

		// Si la variable empresa encara és igual a null la cerquem
		if (empresa == null)
			empresa = empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if (empresa == null)
			return "redirect:/";
		// Si ja s'ha passat per llistar i el mètode és cridat des del navegador amb un
		// número negatiu no ha de fer res
		// per tant ens assegurem que sigui més gran que 0.
		if (id > 0) {

			// Busquem la fase a la base de dades
			FaseConTiempo fase = faseConTiempoService.findById(id);

			// Si el trobem l'eliminem
			if (fase != null) {

				faseConTiempoService.delete(fase);
				Tasca tasca = fase.getTasca();
				tasca.setTiempoEstimado(tasca.getTiempoEstimado() - fase.getTiempoEstimado());
				System.out.println(tasca.getTiempoEstimado());
				tascaService.save(tasca);
			}

			// Per saber si hem eliminat el registre, si no el trobem enviem un missatge com
			// que s'he eliminat
			// correctament, en cas contrari enviem un missatge d'error
			if (faseConTiempoService.findById(id) != null) {

				flash.addAttribute("success", "Fase " + fase.getFase().getNombre() + " esborrada amb èxit");
			} else {

				flash.addAttribute("error", "La fase " + fase.getFase().getNombre() + " no s'ha pogut eliminar");
			}

			return "redirect:/tasques/ver/" + fase.getTasca().getId();

		}

		// Redireccionem a la vista listar
		return "redirect:/tasques/listar/";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/actualizar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		// Si la variable empresa encara és igual a null la cerquem
		if (empresa == null)
			empresa = empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if (empresa == null)
			return "redirect:/";

		// Si ja s'ha passat per llistar i criden al mètode des del navegador el mètode
		// funcionarà, per tant hem
		// de controlar que el paràmetre no sigui -1 per exemple, volem que sigui més
		// gran que cero.

		if (id > 0) {
			// Busquem la fase a la base de dades
			FaseConTiempo fase = faseConTiempoService.findById(id);
			// Si no existeix a la base de dades redireccionem a llistar amb el missatge
			// corresponent.
			if (fase == null) {
				flash.addFlashAttribute("error", "L'ID de la fase no existeix a la BBDD");
				return "redirect:/tasques/listar";
			}
			
			// Canviem el titol i el text del bóto de la vista crear canviant els atributs
			// corresponents
			titol = "Actualitzar fase";
			titolBoto = "Enviar dades";

			// passem la fase trobada, el titol, el text del botó i l'empresa a la vista.
			model.addAttribute("titol", titol);
			model.addAttribute("fase", fase);
			model.addAttribute("titolBoto", titolBoto);
			model.addAttribute("empresa", empresa);

			// Cridem a la vista crear
			return "fasesConTiempo/editar";
		} else {

			// Si l'id és erroni redireccionem a listar amb el missatge corresponent.
			flash.addFlashAttribute("error", "L'ID ha de ser un número més gran que 0");
			return "redirect:/tasques/listar";
		}

		
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Long id, Model model) {

		// Si la variable empresa encara és igual a null la cerquem
		if (empresa == null)
			empresa = empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if (empresa == null)
			return "redirect:/";

		// Busquem la fase demanada per paràmetre, si no es troba a la base de dades
		// redireccionem a llistar.
		FaseConTiempo fase = faseConTiempoService.findById(id);
		if (fase == null)
			return "redirect:/tasques/listar";

		// Afegim a la vista la fase trobada, els usuaris que conté, un títol per la
		// vista i el text del bóto,
		// a més passem l'atribut empresa per que la vista pugui crear la capçalera.
		model.addAttribute("fase", fase);
		model.addAttribute("titol", "Detall fase " + fase.getFase().getNombre());
		model.addAttribute("empresa", empresa);

		// Cridem a la vista ver.
		return "fasesConTiempo/ver";
	}

	@PostMapping("/result")
	public String guardar(FaseConTiempo fase, Model model, RedirectAttributes flash,
			SessionStatus status) {

		// Afegim el titol, el text del botó i l'empresa abans de gestionar res més
		// perquè ens fa falta per qualsevol dels casos.
		model.addAttribute("titol", titol);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);		
		
		if(fase.getFase()==null)System.out.println("Es null");

		// Guardem el registre
		if (faseConTiempoService.save(fase) != null) {

			fase.getTasca().calcularTiempoEstimado();
			tascaService.save(fase.getTasca());

			// Afegim al model el missatge que s'ha guardat correctament
			flash.addFlashAttribute("success", "Registre guardat amb èxit");

		} else {

			// Afegim al model el missatge que no s'ha guardat correctament
			flash.addFlashAttribute("errors", "El registre no s'ha pogut guardar a la base de dades");
		}

		// Confimem que s'ha completat tot el procès i que ja no cal que guardi les
		// dades de departament.
		status.setComplete();
		// Redireccionem a llistar
		return "redirect:/tasques/ver/" + fase.getTasca().getId();
	}

	@ModelAttribute("usuariAutenticat")
	public Usuari getUsuariAuthenticat() {

		return Usuari.USUARIAUTENTICAT;
	}

}
