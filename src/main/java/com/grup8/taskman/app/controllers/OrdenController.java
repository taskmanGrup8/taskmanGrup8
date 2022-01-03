package com.grup8.taskman.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grup8.taskman.app.domain.empreses.Empresa;
import com.grup8.taskman.app.domain.tasques.FiltreOrdres;
import com.grup8.taskman.app.domain.tasques.Orden;

import com.grup8.taskman.app.domain.tasques.Tasca;
import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.services.departament.IDepartamentService;
import com.grup8.taskman.app.services.empresa.IEmpresaService;
import com.grup8.taskman.app.services.tasques.IOrdenService;
import com.grup8.taskman.app.services.tasques.ITascaService;
import com.grup8.taskman.app.util.Utilidades;

@Controller
@SessionAttributes("ordre")
@RequestMapping("/ordenes")
public class OrdenController {

	@Autowired
	IOrdenService ordenService;

	@Autowired
	ITascaService tascaService;

	@Autowired
	IEmpresaService empresaService;

	@Autowired
	IDepartamentService departamentService;

	private String titolBoto;
	private String titol;
	private Empresa empresa;

	@Secured("ROLE_ADMIN")
	@GetMapping("/crear")
	public String crear(Model model) {

		// Si la variable empresa encara és igual a null la cerquem
		if (empresa == null)
			empresa = empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if (empresa == null)
			return "redirect:/perfil";

		// Assignem els titols de creació als nostres atributs
		titol = "Crear nova ordre";
		titolBoto = "Enviar dades";

		// Afegim al model el titol i el text del botó acceptar del formulari, un nou
		// departament buït i l'atribut empresa.
		model.addAttribute("titol", titol);
		model.addAttribute("ordre", new Orden());
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);

		return "ordres/crear";
	}

	@PostMapping("/result")
	public String guardar(@Valid Orden ordre, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		// Afegim el titol, el text del botó i l'empresa abans de gestionar res més
		// perquè ens fa falta per qualsevol dels casos.
		model.addAttribute("titol", titol);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);
		model.addAttribute("orden", ordre);

		if (result.hasErrors()) {
			flash.addFlashAttribute("error", "No ha estat possible guardar les dades");

			return "ordres/crear";
		}

		ordre.generarFasesExecutables();
		Orden orden = ordenService.save(ordre);
		// Guardem el registre
		if (orden != null) {

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
		return "redirect:listar";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/listar")
	public String listar(Model model, RedirectAttributes flash) {

		// Si la variable empresa encara és igual a null la cerquem
		if (empresa == null)
			empresa = empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if (empresa == null) {

			return "redirect:/perfil";
		}
		// Obtenim la Page que passarem a la vista

		if (Utilidades.restarFechas(FiltreOrdres.filtreOrdre.getDataFi(),
				FiltreOrdres.filtreOrdre.getDataInici()) < 0) {

			flash.addAttribute("error", "La data de finalització ha de ser posterior a la data d'inici");
			return "redirect:listar";

		}

		List<Orden> ordres = FiltreOrdres.filtreOrdre.getOrdres(ordenService);

		// Afegim els atributs necessaris al model per poder-los utilitzar a la vista
		model.addAttribute("titol", "Llistat d'ordres");
		model.addAttribute("ordres", ordres);
		model.addAttribute("filtreOrdres", FiltreOrdres.filtreOrdre);
		model.addAttribute("empresa", empresa);

		// Cridem a la vista listar.
		return "ordres/listar";
	}

	@PostMapping("/filtrar")
	public String filtrar(FiltreOrdres filtreOrdres, Model model) {

		FiltreOrdres.filtreOrdre = filtreOrdres;
		return "redirect:listar";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {

		// Si la variable empresa encara és igual a null la cerquem
		if (empresa == null)
			empresa = empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if (empresa == null)
			return "redirect:";
		// Si ja s'ha passat per llistar i el mètode és cridat des del navegador amb un
		// número negatiu no ha de fer res
		// per tant ens assegurem que sigui més gran que 0.
		if (id > 0) {

			// Busquem el departament a la base de dades
			Orden orden = ordenService.findById(id);

			// Si el trobem l'eliminem
			if (orden != null) {
				// Eliminem el departament a la base de dades
				ordenService.delete(orden);

				// Per saber si hem eliminat el registre, si no el trobem enviem un missatge com
				// que s'he eliminat
				// correctament, en cas contrari enviem un missatge d'error
				if (ordenService.findById(id) != null) {

					flash.addAttribute("success", "Ordre esborrada amb èxit");
				} else {

					flash.addAttribute("error", "L'ordre no s'ha pogut eliminar");
				}
			}

		}

		// Redireccionem a la vista listar
		return "redirect:/ordenes/listar";
	}

	/**
	 * Mètode que crida a la vista crear passant-li les dades del departament que tè
	 * per id el rebut per paràmetre per poder ser modificat.
	 * 
	 * @param id    Id del departament que volem editar
	 * @param model És el model que passem a la vista
	 * @param flash Variable que fem servir per enviar missatges a les vistes.
	 * @return Si l'atribut empresa és null redireccionem a home, en cas contrari
	 *         redireccionem a listar si l'id no existeix a la base de dades o
	 *         cridem a la vista crear si el trobem.
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

		Orden orden = null;

		// Si ja s'ha passat per llistar i criden al mètode des del navegador el mètode
		// funcionarà, per tant hem
		// de controlar que el paràmetre no sigui -1 per exemple, volem que sigui més
		// gran que cero.

		if (id > 0) {
			// Busquem el departament a la base de dades
			orden = ordenService.findById(id);
			// Si no existeix a la base de dades redireccionem a llistar amb el missatge
			// corresponent.
			if (orden == null) {
				flash.addFlashAttribute("error", "L'ID de l'ordre no existeix a la BBDD");
				return "redirect:listar";
			}
		} else {

			// Si l'id és erroni redireccionem a listar amb el missatge corresponent.
			flash.addFlashAttribute("error", "L'ID ha de ser un número més gran que 0");
			return "redirect:listar";
		}

		// Canviem el titol i el text del bóto de la vista crear canviant els atributs
		// corresponents
		titol = "Modificar ordre";
		titolBoto = "Enviar dades";

		// passem el departament trobat, el titol, el text del botó i l'empresa a la
		// vista.
		model.addAttribute("titol", titol);
		model.addAttribute("ordre", orden);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);

		// Cridem a la vista crear
		return "ordres/crear";
	}

	@ModelAttribute("usuariAutenticat")
	public Usuari getUsuariAuthenticat() {

		return Usuari.USUARIAUTENTICAT;
	}

	@ModelAttribute("tasques")
	public List<Tasca> getTasques() {

		return tascaService.findAll();
	}
}
