package com.grup8.taskman.app.controllers;

import java.util.ArrayList;
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
import com.grup8.taskman.app.domain.tasques.FaseExecutable;
import com.grup8.taskman.app.domain.tasques.FiltreOrdres;
import com.grup8.taskman.app.domain.tasques.Notificacion;
import com.grup8.taskman.app.domain.tasques.Orden;

import com.grup8.taskman.app.domain.tasques.Tasca;
import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.services.departament.IDepartamentService;
import com.grup8.taskman.app.services.empresa.IEmpresaService;
import com.grup8.taskman.app.services.tasques.IFaseExecutableService;
import com.grup8.taskman.app.services.tasques.IOrdenService;
import com.grup8.taskman.app.services.tasques.ITascaService;
import com.grup8.taskman.app.util.Utilidades;

/**
 * Controlador que gestiona les vistes referents a les ordres d'execució. Està anotada amb @Controller
 * i guarda ordre com atribut de sessió. Està mapejat a la url "/ordenes".
 * 
 * Aquest controlador ens pemetrà crear ordres i ordres cícliques, editar, guardar, llistar, eliminar, veure el detall
 * detenir el cicle d'una ordre cíclica.
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */

@Controller
@SessionAttributes("ordre")
@RequestMapping("/ordenes")
public class OrdenController {
	
	// CONSTANTS
	
	private static final int CREAR=1;
	private static final int EDITAR=2;

	// ATRIBUTS
	
	// Injectem el servei d'ordres per tenir accés a la taula ordres.
	@Autowired
	IOrdenService ordenService;

	// Injectem el servei de tasques per tenir accés a la taula tasques.
	@Autowired
	ITascaService tascaService;

	// Injectem el servei d'empreses per tenir accés a la taula empreses.
	@Autowired
	IEmpresaService empresaService;

	// Injectem el servei de departaments per tenir accés a la taula departaments.
	@Autowired
	IDepartamentService departamentService;
	
	// Injectem el servei de fases executables per tenir accés a la seva taula.
	@Autowired
	IFaseExecutableService faseExecutableService;

	
	private String titolBoto;
	private String titol;
	private Empresa empresa;
	private boolean ciclica;
	private int funcion;
	

	/**
	 * Mètode que crida a la pàgina ordres/crear i li passa el llistat de tasques que no són cícliques.
	 * @param model Model que passem a la vista
	 * @return Redirecciona a la pàgina d'inici si l'empresa no existeix i si no crida la vista ordres/crear
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping("/crear")
	public String crear(Model model) {

		
		empresa = empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if (empresa == null)
			return "redirect:/perfil";

		// Assignem els titols de creació als nostres atributs
		titol = "Crear nova ordre";
		titolBoto = "Enviar dades";
		ciclica=false;
		
		List<Tasca> tasques=tascaService.findByCiclica(false);
		funcion=CREAR;

		// Afegim al model el titol i el text del botó acceptar del formulari, una nova
		// ordre buida,l'atribut empresa i el llistat de tasques.
		model.addAttribute("titol", titol);
		model.addAttribute("ordre", new Orden());
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);
		model.addAttribute("tasques", tasques);
		model.addAttribute("funcion", funcion);

		return "ordres/crear";
	}
	
	/**
	 * Mètode que crida a la pàgina ordres/crear i li passa el llistat d'ordres que són cícliques
	 * @param model Model que passem a la vista
	 * @return Crida a la pàgina ordres/crear sempre que l'empresa existeixi, en cas contrari redirecciona a inici
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping("/crearCiclica")
	public String crearCiclica(Model model) {

		empresa = empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if (empresa == null)
			return "redirect:/perfil";

		// Assignem els titols de creació als nostres atributs
		titol = "Crear nova ordre cíclica";
		titolBoto = "Enviar dades";
		ciclica=true;
		List<Tasca> tasques=tascaService.findByCiclica(true);
		funcion=1;
		
		// Afegim al model el titol i el text del botó acceptar del formulari, una nova
		// ordre buda, l'atribut empresa i el llistat de tasques.
		model.addAttribute("titol", titol);
		model.addAttribute("ordre", new Orden());
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);
		model.addAttribute("tasques", tasques);
		model.addAttribute("funcion", funcion);

		return "ordres/crear";
	}

	/**
	 * Mètode que genera les fases executables de l'ordre i les salva conjuntament amb l'ordre.
	 * @param ordre Instancia d'Ordre amb el contingut del formulari
	 * @param result Instancia que conté els errors que han hagut al formulari.
	 * @param model És el model que passem a la vista
	 * @param flash Variable utilitzada per passar missatges a la vista.
	 * @param status Variable que controla l'estat en aquest cas de la variable ordre
	 * @return Redirecciona a llistar ordres si tot és correcte, en cas contrari crida a la vista crear.
	 */	
	@PostMapping("/result")
	public String guardar(@Valid Orden ordre, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		// Afegim el titol, el text del botó i l'empresa abans de gestionar res més
		// perquè ens fa falta per qualsevol dels casos.
		model.addAttribute("titol", titol);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);
		model.addAttribute("orden", ordre);
		model.addAttribute("funcion", funcion);

		if (result.hasErrors()) {
			flash.addFlashAttribute("error", "No ha estat possible guardar les dades");

			return "ordres/crear";
		}

		ordre.setCiclica(ciclica);		
		if(funcion==1)ordre.generarFasesExecutables();		
		Orden orden = ordenService.save(ordre);
		// Guardem el registre
		if (orden != null) {

			// Afegim al model el missatge que s'ha guardat correctament
			flash.addFlashAttribute("success", "Registre guardat amb èxit");
		} else {

			// Afegim al model el missatge que no s'ha guardat correctament
			flash.addFlashAttribute("error", "El registre no s'ha pogut guardar a la base de dades");
		}

		// Confirmem que s'ha completat tot el procès i que ja no cal que guardi les
		// dades de departament.
		status.setComplete();
		// Redireccionem a llistar
		return "redirect:listar";
	}

	/**
	 * Mètode que genera un llistat d'ordres segons el filtrat escollit i el passa a la vista llistar.
	 * @param model Model que passem a la vista
	 * @param flash Variable utilitzada per passar missatges a la vista.
	 * @return Si la empresa no existeix redirecciona a perfil, en cas contrari crida a la vista ordres/listar
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping("/listar")
	public String listar(Model model, RedirectAttributes flash) {

		empresa = empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if (empresa == null) {

			return "redirect:/perfil";
		}
		
		// Si la data de finalització és anterior a la data d'inici avisem de l'error i redireccionem a inici i fem reset del filtre
		if (Utilidades.restarFechas(FiltreOrdres.filtreOrdre.getDataFi(),
				FiltreOrdres.filtreOrdre.getDataInici()) < 0) {

			FiltreOrdres.filtreOrdre=new FiltreOrdres();
			flash.addAttribute("error", "La data de finalització ha de ser posterior a la data d'inici");
			return "redirect:listar";

		}

		// Cerquem les ordres segons el filtre
		List<Orden> ordres = FiltreOrdres.filtreOrdre.getOrdres(ordenService);

		// Afegim els atributs necessaris al model per poder-los utilitzar a la vista
		model.addAttribute("titol", "Llistat d'ordres");
		model.addAttribute("ordres", ordres);
		model.addAttribute("filtreOrdres", FiltreOrdres.filtreOrdre);
		model.addAttribute("empresa", empresa);

		// Cridem a la vista listar.
		return "ordres/listar";
	}

	/**
	 * Mètode que actualitza la variable filtreOrdre i torna a cridar a listar perquè realitzi els filtres
	 * corresponents
	 * @param filtreOrdre Variable que te les dades del formulari de filtres de la vista listar
	 * @param model Model que passem a la vista
	 * @return Redirecciona a listar
	 */
	@PostMapping("/filtrar")
	public String filtrar(FiltreOrdres filtreOrdres, Model model) {

		FiltreOrdres.filtreOrdre = filtreOrdres;
		return "redirect:listar";
	}

	/**
	 * Mètode que rep l'id d'una ordre i l'elimina de la base de dades
	 * @param id Id de l'ordre que volem eliminar
	 * @param flash Variable utilitzada per passar missatges a la vista.
	 * @return Si la empresa no existeix redireccióna a inici, en cas contrari redirecciona a ordenes/listar
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {

		empresa = empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if (empresa == null)
			return "redirect:";
		// Si ja s'ha passat per llistar i el mètode és cridat des del navegador amb un
		// número negatiu no ha de fer res
		// per tant ens assegurem que sigui més gran que 0.
		if (id > 0) {

			// Busquem l'ordre a la base de dades
			Orden orden = ordenService.findById(id);

			// Si la trobem l'eliminem
			if (orden != null) {
				// Eliminem l'ordre a la base de dades
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
	 * Mètode que crida a la vista crear passant-li les dades de l'ordre que tè
	 * per id el rebut per paràmetre per poder ser modificat.
	 * 
	 * @param id Id de l'ordre que volem editar
	 * @param model És el model que passem a la vista
	 * @param flash Variable que fem servir per enviar missatges a les vistes.
	 * @return Si l'atribut empresa és null redireccionem a inici en cas contrari
	 * redireccionem a listar si l'id no existeix a la base de dades o cridem a la vista crear si el trobem.
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping("/actualizar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		
		empresa = empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if (empresa == null)
			return "redirect:/";

		Orden ordre = null;

		// Si ja s'ha passat per llistar i criden al mètode des del navegador el mètode
		// funcionarà, per tant hem
		// de controlar que el paràmetre no sigui -1 per exemple, volem que sigui més
		// gran que cero.

		if (id > 0) {
			// Busquem l'ordre a la base de dades
			ordre = ordenService.findById(id);
			// Si no existeix a la base de dades redireccionem a llistar amb el missatge
			// corresponent.
			if (ordre == null) {
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
		
		List<Tasca> tasques=new ArrayList<>();
		
		// Passem el llistat de tasques en funció a si l'ordre és cíclica o no
		if(ordre.isCiclica()) {
			
			tasques=tascaService.findByCiclica(true);
			
		}else {
			
			tasques=tascaService.findByCiclica(false);
		}
		
		funcion=EDITAR;

		// passem l'ordre trobada, el titol, el text del botó, l'empresa i les tasques a la vista.
		model.addAttribute("titol", titol);
		model.addAttribute("ordre", ordre);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);
		model.addAttribute("tasques", tasques);
		model.addAttribute("funcion", funcion);

		// Cridem a la vista crear
		return "ordres/crear";
	}
	
	/**
	 * Mètode que carrega la vista ver amb l'ordre a qué correspon l'id rebut per paràmetre
	 * @param id Id de l'ordre de la qual volem veure el detall
	 * @param model Model que passem a la vista
	 * @return Carrega la vista ver si la empresa i l'ordre existeixen, en cas contrari redirecciona a perfil
	 * i a listar respectivament. 
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Long id, Model model) {
		
		empresa=empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if(empresa==null)return "redirect:/perfil";	
		
		// Busquem l'ordre demanada per paràmetre, si no es troba a la base de dades redireccionem a llistar.
		Orden orden=ordenService.findById(id);
		if(orden==null)return "redirect:listar";		
		
		// Agafem la llista de fases de l'ordre
		List<FaseExecutable> fases=orden.getFases();
		// Calculem els temps de notificacions
		calcularTiemposNotificacion(fases);
		
		// Afegim a la vista l'ordre trobada, les fases que conté, un títol per la vista, el text del botons i l'empresa,
		// a més passem l'atribut empresa per que la vista pugui crear la capçalera.
		model.addAttribute("orden", orden);
		model.addAttribute("fases", fases);
		model.addAttribute("titol", "Detall ordre " + orden.getTasca().getNombre());
		model.addAttribute("boton","Mostrar notificacions");
		model.addAttribute("boton2","Detenir cicle");
		model.addAttribute("empresa",empresa);
		
		// Cridem a la vista ver.
		return "ordres/ver";
	}
	
	/**
	 * Mètode que atura l'autogeneració de cicles en notificar una ordre
	 * @param id Id de l'ordre que volem aturar
	 * @param model Model que passem a la vista
	 * @return Redirecciona a la vista ver de l'ordre rebuda.
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping("/detenerCiclo/{id}")
	public String deternerCiclo(@PathVariable Long id, Model model) {
			
		empresa=empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if(empresa==null)return "redirect:/perfil";	
		Orden orden=ordenService.findById(id);
		if(orden==null)return "redirect:listar";
		orden.setCiclica(false);
		ordenService.save(orden);
		return "redirect:/ordenes/ver/"+id;
	}
	
	/**
	 * Mètode que calcula el temps de notificació de cada notificació de cada fase de la llista rebuda.
	 * @param fases Llista de fases executables de la que volem calcular els temps de notificació.
	 */
	private void calcularTiemposNotificacion(List<FaseExecutable> fases) {
		
		for(FaseExecutable fase: fases) {
			
			for(Notificacion notificacion: fase.getNotificaciones())notificacion.calculaTiempo();
		}
	}	
	

	@ModelAttribute("usuariAutenticat")
	public Usuari getUsuariAuthenticat() {

		return Usuari.USUARIAUTENTICAT;
	}	


}
