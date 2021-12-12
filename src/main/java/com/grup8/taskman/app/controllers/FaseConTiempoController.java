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

/**
 * Classe que gestiona les diferents fases amb temps de l'aplicació. Podem editar, guardar, eliminar i veure les fases amb temps.
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */

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

	/**
	 * Mètode que elimina la fase amb temps rebuda per paràmetre
	 * @param id Id de la fase amb temps que volem eliminar.
	 * @param flash Instància que ens permet passar missatges al model
	 * @return Redirecciona a home si no hi ha empresa. Si hi ha algun tipus d'error redirecciona a tasques/listar
	 * i en cas contrari redirecciona a tasques/ver amb l'id de la tasca asociada.
	 */
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
		// número negatiu no ha de fer res per tant ens assegurem que sigui més gran que 0.
		if (id > 0) {

			// Busquem la fase a la base de dades
			FaseConTiempo fase = faseConTiempoService.findById(id);

			// Si la trobem l'eliminem
			if (fase != null) {

				faseConTiempoService.delete(fase);
				// Actualitzem el temps estimat de la tasca asociada perquè ja no conté aquesta fase
				Tasca tasca = fase.getTasca();
				tasca.setTiempoEstimado(tasca.getTiempoEstimado() - fase.getTiempoEstimado());
				// Guardem la tasca
				tascaService.save(tasca);
			}

			// Per saber si hem eliminat el registre, si no el trobem enviem un missatge com
			// que s'he eliminat correctament, en cas contrari enviem un missatge d'error
			if (faseConTiempoService.findById(id) != null) {

				flash.addAttribute("success", "Fase " + fase.getFase().getNombre() + " esborrada amb èxit");
			} else {

				flash.addAttribute("error", "La fase " + fase.getFase().getNombre() + " no s'ha pogut eliminar");
			}

			// Redireccionem al mètode ver de tasques amb l'id de la tasca asociada.
			return "redirect:/tasques/ver/" + fase.getTasca().getId();

		}

		// Redireccionem a la vista listar
		return "redirect:/tasques/listar/";
	}
	
	/**
	 * Mètode que crida a la vista fasesConTiempo/editar que permet editar una fase amb temps
	 * @param id Id de la fase que volem editar
	 * @param model Model que passem a la vista
	 * @param flash Instància per poder passar missatges a la vista
	 * @return Si la empresa no existeix redirecciona a home, si la fase no existeix redirecciona al
	 * mètode listar de tasques i si la troba crida a la vista "fasesConTiempo/editar".	 * 
	 */
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

	/**
	 * Mètode que rep per paràmetre l'id d'una fase amb temps i crida a la vista que mostra el detall d'aquesta fase.
	 * @param id Id de la fase amb temps que volem veure
	 * @param model Model que passem a la vista
	 * @return Si la empresa no existeix torna a home, si la fase no existeix crida a la vista listar fases amb temps i si 
	 * existeix crida a la vista ver passant la fase amb temps sel.leccionada.
	 */
	
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
		// vista i el text del bóto, a més passem l'atribut empresa per que la vista pugui crear la capçalera.
		model.addAttribute("fase", fase);
		model.addAttribute("titol", "Detall fase " + fase.getFase().getNombre());
		model.addAttribute("empresa", empresa);

		// Cridem a la vista ver.
		return "fasesConTiempo/ver";
	}
	
	
	/**
	 * Mètode que guarda a la base de dades la fase amb temps rebuda per paràmetre.
	 * @param fase Fasse amb temps que volem guardar
	 * @param model Model que passem a la vista
	 * @param flash Instància per poder passar missatges a la vista
	 * @param status Instancia que ens permet canviar l'status de les variables passades a SessionAttributes
	 * @return Redirecciona a la vista ver de la tasca a qui pertany aquesta fase amb temps.
	 */
	@PostMapping("/result")
	public String guardar(FaseConTiempo fase, Model model, RedirectAttributes flash,
			SessionStatus status) {

		// Afegim el titol, el text del botó i l'empresa abans de gestionar res més
		// perquè ens fa falta per qualsevol dels casos.
		model.addAttribute("titol", titol);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);		
				

		// Guardem el registre
		if (faseConTiempoService.save(fase) != null) {

			// Si el registre s'ha guardat amb exit actualitzem el camp tiempoEstimado de la seva tasca i el guardem
			fase.getTasca().calcularTiempoEstimado();
			tascaService.save(fase.getTasca());

			// Afegim al model el missatge que s'ha guardat correctament
			flash.addFlashAttribute("success", "Registre guardat amb èxit");

		} else {

			// Afegim al model el missatge que no s'ha guardat correctament
			flash.addFlashAttribute("errors", "El registre no s'ha pogut guardar a la base de dades");
		}

		// Confimem que s'ha completat tot el procès i que ja no cal que guardi les
		// dades de fase.
		status.setComplete();
		// Redireccionem a /tasques/ver amb l'id de la tasca on pertany la fase.
		return "redirect:/tasques/ver/" + fase.getTasca().getId();
	}

	/**
	 * Mètode que afegeix l'usuari autenticat al model
	 * @return Retorna l'usuari autenticat
	 */
	@ModelAttribute("usuariAutenticat")
	public Usuari getUsuariAuthenticat() {

		return Usuari.USUARIAUTENTICAT;
	}

}
