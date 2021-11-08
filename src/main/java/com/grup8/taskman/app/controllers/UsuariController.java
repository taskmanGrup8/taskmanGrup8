package com.grup8.taskman.app.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grup8.taskman.app.domain.departaments.Departament;
import com.grup8.taskman.app.domain.empreses.Empresa;
import com.grup8.taskman.app.domain.usuaris.FiltreUsuaris;
import com.grup8.taskman.app.domain.usuaris.Rol;
import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.services.departament.IDepartamentService;
import com.grup8.taskman.app.services.empresa.IEmpresaService;
import com.grup8.taskman.app.services.rol.IRolService;
import com.grup8.taskman.app.services.usuari.IUsuariService;
import com.grup8.taskman.app.util.PageRender;

/**
 * Classe que controla el mòdul d'usuaris.
 * Rebra les sol.licituts a partir de la url "/usuaris" i tindrà
 * les funcionalitats de crear, actualitzar, guardar, llistar i veure detall de l'usuari.
 * 
 *  De moment no s'està tenint en compte el rol de qui ho fa servir, mès endavant nomès els
 *  administrador i el súper administrador podràn fer servir aquesta èina.
 *  
 *  Des del menú del superadministrador s'accedeix a llistar i és el camí que ha de seguir
 *  el programa pel bon funcionament. Si algún crida a qualsevol de les url des del navegador
 *  abans de passar per llistar, l'atribut empresa serà null i es redireccionarà l'aplicació a home.
 *  
 *  L'empresa ha de estar creada abans de fer servir aquesta funcionalitat.
 *  
 *  Passarem la variable anomenada usuari a SessionAttributes perquè guardi el valor de la mateixa 
 *  d'un a altre mètode. 
 *  
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */

@Controller
@RequestMapping("/usuaris")
@SessionAttributes("usuari")
public class UsuariController {

	// ATRIBUTS
	
	// Injectem el servei de rols per poder realitzar accions a la taula rols.	
	@Autowired
	IRolService rolService;
	
	// Injectem el servei de departaments per poder realitzar accions a la taula departaments.
	@Autowired
	IDepartamentService departamentService;
		
	// Injectem el servei de usuaris per poder realitzar accions a la taula usuaris.
	@Autowired
	IUsuariService usuariService;
	
	// Injectem el servei de empreses per poder realitzar accions a la taula empreses.
	@Autowired
	IEmpresaService empresaService;

	private FiltreUsuaris filtreUsuari = new FiltreUsuaris();
	private String titolBoto;
	private String titol;
	Empresa empresa;

	// MÈTODES
	
	/**
	 * Funció que crida a la vista crear que conté el formulari per crear un usuari
	 * @param model És el model que passem a la vista
	 * @return Crida a la vista "usuaris/crear" si l'atribut empresa no és null, en cas contrari redirecciona 
	 * a home.
	 */
	@GetMapping("/crear")
	public String crear(Model model) {
		
		// Si la variable empresa encara és igual a null la cerquem
		if(empresa==null)empresa=empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if(empresa==null)return "redirect:/";	

		titol = "Crear nou usuari";
		titolBoto = "crear Usuari";
		model.addAttribute("titol", titol);
		model.addAttribute("usuari", new Usuari());
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);

		return "usuaris/crear";
	}

	// Afegim als models sempre l'atribut listaRoles que conté la llista amb tots els rols
	@ModelAttribute("listaRoles")
	public List<Rol> listaRoles() {

		return rolService.findAll();
	}

	// Afegim als models sempre l'atribut listaDepartamentos que conté la llista amb tots els departaments
	@ModelAttribute("listaDepartamentos")
	public List<Departament> listaDepartamentos() {

		return departamentService.findAll();
	}

	/**
	 * Funció que es crida des de la vista crear i que rep les dades del formulari emplenat per l'usuari, valida les
	 * dades i les guarda a la base de dades si tot és correcte. Si el formulari no ha estat emplenat correctament i 
	 * conté errors llavors torna a cridar a la vista crear.
	 * @param usuari Instancia d'usuari amb el contingut del formulari
	 * @param result Instancia que conté els errors que han hagut al formulari.
	 * @param model És el model que passem a la vista
	 * @param flash Variable utilitzada per passar missatges a la vista.
	 * @param status Variable que controla l'estat en aquest cas de la variable departament.
	 * @return Redirecciona a llistar usuaris si tot és correcte, en cas contrari crida a la vista crear.
	 */
	@PostMapping("/guardar")
	public String guardar(@Valid Usuari usuari, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		// Afegim al model els atributs necessaris
		model.addAttribute("titol", titol);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);
		
		// Validem el dni perquè no existeixi a la base de dades
		if (!comprobacionDni(usuari))
			result.rejectValue("dni", "usuari.dniExistente");

		// Si result conté errors tornem a cridar la vista crear avisant de l'error
		if (result.hasErrors()) {
			flash.addFlashAttribute("error", "No ha sido posible guardar los datos");
			return "usuaris/crear";
		}

		// Emplenem els camps per defecte. 
		usuari.setActivo(true); // Per defecte l'usuari pertany a l'empresa
		usuari.setPrivacidadFirmada(false); // Per defecte l'usuari no ha signat la privacitat
		usuari.setPassword("1111"); // Password per defecte, més endavant el pot canviar

		// Guardem l'usuari
		Usuari user=usuariService.save(usuari);
		if(user.getId()!=null) {
			
			flash.addFlashAttribute("success", "Registro grabado con éxito");
		}else {
			
			flash.addFlashAttribute("error", "No s'ha pogut guardar el registre");
		}
		status.setComplete();		
		return "redirect:listar";
	}

	/**
	 * Mètode que crida a la vista listar passant-li els usuaris que hi han a la base de dades que compleixin
	 * el filtres demanats per la vista.
	 * @param page Variable que indica el número de pàgina on ens trobem
	 * @param model És el model que es passa a la vista
	 * @return Si la empresa no existeix redirecciona a home, si no crida a la vista listar
	 */
	@GetMapping("/listar")
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page,	Model model) {

		// Si la variable empresa encara és igual a null la cerquem
		if(empresa==null)empresa=empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if(empresa==null)return "redirect:/";	
		
		// Creem el pageable i donem l'ordre com volem les pàgines.
		Pageable pageRequest = PageRequest.of(page, 8, sortByIdAsc());
		
		// Obtenim la Page que passarem a la vista
		Page<Usuari> usuaris = filtreUsuari.getUsuaris(pageRequest, usuariService);
		// Fem el pageRender de la pàgina que passarem per parámetre
		PageRender<Usuari> pageRender = new PageRender<Usuari>("/usuaris/listar", usuaris);
		
		// Afegim els atributs necessaris al model per poder-los utilitzar a la vista
		model.addAttribute("titol", "Llistat d'usuaris");
		model.addAttribute("page", pageRender);
		model.addAttribute("filtreUsuari", filtreUsuari);
		model.addAttribute("usuaris", usuaris);
		model.addAttribute("empresa", empresa);

		// Cridem a la vista listar.
		return "usuaris/listar";
	}
	
	/**
	 * Mètode que actualitza la variable filtreUsuari i torna a cridar a listar perquè realitzi els filtres
	 * corresponents
	 * @param filtreUsuari Variable que te les dades del formulari de filtres de la vista listar
	 * @param model Model que passem a la vista
	 * @return Redirecciona a listar
	 */
	@PostMapping("/filtrar")
	public String filtrar(FiltreUsuaris filtreUsuari, Model model) {
		
		this.filtreUsuari=filtreUsuari;		
		return "redirect:listar";
	}
	
	/**
	 * Mètode que crida la vista ver amb el detall de l'usuari rebut per paràmetre
	 * @param id És l'identificador de l'usuari del qual volem veure el detall
	 * @param model És el model que passem a la vista
	 * @return Crida a la vista usuaris/ver si l'atribut empresa  no és null i el departament passat
	 * per paràmetre es troba a la base de dades, si no existeix la empresa redirecciona a home i si no existeix el 
	 * l'usuari redirecciona a listar. 
	 */
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Long id, Model model) {
		// Si la variable empresa encara és igual a null la cerquem
		if(empresa==null)empresa=empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if(empresa==null)return "redirect:/";	
		
		// Si no trobem l'usuari tornem a llistar
		Usuari usuari=usuariService.findById(id);		
		if(usuari==null) return "redirect:listar";	
		
		// Agafem la llista de departaments que té l'usuari
		List<Departament> departaments= usuari.getDepartaments();
		
		// Afegim els atributs al model
		model.addAttribute("departaments", departaments);
		model.addAttribute("titol", "Detalle usuario " + usuari.getNombre());
		model.addAttribute("boton","Ver Listado departamentos");
		model.addAttribute("usuari",usuari);
		model.addAttribute("empresa", empresa);
		model.addAttribute("deps", usuari.getDepartaments());
		
		// Cridem a la vista ver
		return "usuaris/ver";
	}

	/**
	 * Mètode que elimina de la base de dades l'usuari del qual hem rebut l'id.		
	 * @param id Identificador de l'usuari que volem eliminar.	
	 * @param flash Variable que s'encarrega d'enviar els missatges a la vista a la que redireccionem.
	 * @return Si l'atribut empresa és null redirecciona a home, en cas contrari redirecciona a listar.
	 */
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {

		// Si la variable empresa encara és igual a null la cerquem
		if(empresa==null)empresa=empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if(empresa==null)return "redirect:/";	
		
		// Id a ser més gran que 0.
		if (id > 0) {
			// Busquem l'usuari
			Usuari user = usuariService.findById(id);
			
			// Si el trobem canviem l'atribut activo a false, guardem i retornem el resultat
			if (user != null) {

				user.setActivo(false);
				Usuari usuari=usuariService.save(user);
				if(usuari.getId()!=null) {
					flash.addFlashAttribute("success",
							"Usuari " + user.getNombre() + " " + user.getApellidos() + " eliminat amb èxit");
				}else {
					
					flash.addFlashAttribute("error", "No s'ha pugut eliminar l'usuari!!!");
				}
			
			// Si no trobem l'usuari llavors informem de l'error
			} else {

				flash.addFlashAttribute("error", "L'usuari amb id " + id + " no existeix a la base de dades");
			}

		// Si l'id no es més gran que cero informem de l'error	
		} else {

			flash.addFlashAttribute("error", "L'id ha de ser més gran que 0");
		}

		// Redireccionem a listar
		return "redirect:/usuaris/listar";
	}

	/**
	 * Mètode que crida a la vista crear passant-li les dades de l'usuari que tè per id el rebut per paràmetre per
	 * poder ser modificat.
	 * @param id Id de l'usuari que volem editar
	 * @param model És el model que passem a la vista
	 * @param flash Variable que fem servir per enviar missatges a les vistes.
	 * @return Si l'atribut empresa és null redireccionem a home, en cas contrari redireccionem a listar si l'id no
	 * existeix a la base de dades o cridem a la vista crear si el trobem.
	 */
	@GetMapping("/actualizar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Usuari usuari = null;
		
		// Si la variable empresa encara és igual a null la cerquem
		if(empresa==null)empresa=empresaService.findById(1);
		// Si desprès de cercar no tenim empresa llavors redireccionem a home
		if(empresa==null)return "redirect:/";	

		// Si l'id és més gran que cero busquem l'usuari
		if (id > 0) {

			usuari = usuariService.findById(id);
			// Si no el trobem informem de l'error i redireccionem a listar
			if (usuari == null) {
				flash.addFlashAttribute("error", "El ID de l'usuari no existeix a la BBDD");
				return "redirect:/usuaris/listar";
			}
		// Si l'id no és més gran que cero cridem a listar	
		} else {

			flash.addFlashAttribute("error", "L'ID de l'usuari no pot ser 0");
			return "redirect:/usuaris/listar";
		}

		// Passem al model els atributs necessaris
		titolBoto = "Modificar Usuari";
		titol = "Modificar usuari";
		model.addAttribute("titol", titol);
		model.addAttribute("usuari", usuari);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);

		// Cridem a la vista crear
		return "usuaris/crear";
	}

	/**
	 * Mètode que comproba que el dni que s'està introduint no existeixi a la base de dades.
	 * @param usuario Usuari del qual mirem el dni.
	 * @return Retorna true si no existeix i false en cas contrari
	 */	
	private boolean comprobacionDni(Usuari usuario) {

		// Inicialitzem a true el resultat
		boolean result = true;
		// Busquem l'usuari mitjançant el dni
		Usuari usuariPerDni = usuariService.findByDni(usuario.getDni());
		// Si el trobem
		if (usuariPerDni != null) {
			// Si els ids no coincideixen llavors estem intentant repetir per tant retornem false
			if (usuario.getId() != usuariPerDni.getId()) {

				result = false;
			}

		}
		// Retornem result
		return result;
	}
	
	/**
	 * Mètode que retorna una Instància de sort que conté la direcció i el camp pel qual volem que ens ordeni les llistes
	 * @return Instància de Sort
	 */
	private Sort sortByIdAsc() {
		 
			// Creem un nou ordre al cual donem direcció ascendent i com a camp clau l'id
		 	Order orden=new Order(Direction.ASC, "id");
		 	// Creem una llista d'ordres i afegim l'ordre creat
		 	List<Order> lista=new ArrayList<>();
		 	lista.add(orden);
		 	// Retornem la instància de sort amb la llista d'ordres.
		 	return Sort.by(lista);
	    }

}
