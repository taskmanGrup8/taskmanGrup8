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
import com.grup8.taskman.app.domain.tasques.Fase;
import com.grup8.taskman.app.domain.tasques.FaseConTiempo;
import com.grup8.taskman.app.domain.tasques.Tasca;
import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.services.departament.IDepartamentService;
import com.grup8.taskman.app.services.empresa.IEmpresaService;
import com.grup8.taskman.app.services.tasques.IFaseConTiempoService;
import com.grup8.taskman.app.services.tasques.IFaseService;
import com.grup8.taskman.app.services.tasques.ITascaService;
import com.grup8.taskman.app.util.Utilidades;

/**
 * Classe que gestiona les diferents tasques de l'aplicació. Podem crear, editar, guardar, eliminar, veure i llistar les diferents tasques.
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping("/tasques")
@SessionAttributes("tasca")
public class TascaController {
	
	// ATRIBUTS
	
		// Injectem el servei de departaments per poder realitzar accions a la taula departaments.	
		@Autowired
		IDepartamentService departamentService;
		
		// Injectem el servei de fases per poder realitzar accions a la taula fases.
		@Autowired
		IFaseService faseService;	
		
		// Injectem el servei de fases amb temps per poder realitzar accions a la taula fases amb temps.
		@Autowired
		IFaseConTiempoService faseConTiempoService;
		
		// Injectem el servei de tasques per poder realitzar accions a la taula tasques.
		@Autowired
		ITascaService tascaService;	
		
		// Injectem el servei de empresa per poder determinar si ja existeix la empresa.
		@Autowired
		IEmpresaService empresaService;	
			
		private String titolBoto;
		private String titol;
		private Empresa empresa;
		
		
		/**
		 * Funció que crida a la vista crear que conté el formulari per crear una tasca
		 * @param model És el model que passem a la vista
		 * @return Crida a la vista "tasques/crear" si l'atribut empresa no és null, en cas contrari redirecciona 
		 * a home.
		 */
		@Secured("ROLE_ADMIN")
		@GetMapping("/crear")
		public String crear(Model model) {
			
			// Si la variable empresa encara és igual a null la cerquem
			if(empresa==null)empresa=empresaService.findById(1);
			// Si desprès de cercar no tenim empresa llavors redireccionem a home
			if(empresa==null)return "redirect:/";		
			
			// Assignem els titols de creació als nostres atributs
			titol="Crear nova tasca";
			titolBoto="Enviar dades";
			
			// Afegim al model el titol i el text del botó acceptar del formulari, un nou departament buït i l'atribut empresa.
			model.addAttribute("titol", titol);
			model.addAttribute("tasca", new Tasca());
			model.addAttribute("titolBoto", titolBoto);
			model.addAttribute("empresa", empresa);
			
			// Cridem a la vista crear.
			return "tasques/crear";
		}
		
		/**
		 * Funció que es crida des de la vista crear i que rep les dades del formulari emplenat per l'usuari, valida les
		 * dades i crida a configurar si tot és correcte. Si el formulari no ha estat emplenat correctament i 
		 * conté errors llavors torna a cridar a la vista crear.
		 * @param tasca Instancia de Tasca amb el contingut del formulari
		 * @param result Instancia que conté els errors que han hagut al formulari.
		 * @param model És el model que passem a la vista
		 * @param flash Variable utilitzada per passar missatges a la vista.		 
		 * @return Redirecciona a configurar tasques si tot és correcte, en cas contrari crida a la vista crear.
		 */
		@PostMapping("/result")
		public String guardar(@Valid Tasca tasca, BindingResult result, Model model, RedirectAttributes flash) {
			
			// Afegim el titol, el text del botó i l'empresa abans de gestionar res més perquè ens fa falta per qualsevol dels casos.
			model.addAttribute("titol", titol);
			model.addAttribute("titolBoto", titolBoto);
			model.addAttribute("empresa", empresa);
			model.addAttribute("tasca", tasca);
			
			// Volem que tant el codi com el nom de la tasca no es puguin repetir a la base de dades
			// i encara que ho podriem fer amb try catch en guardar m'estimo més fer la comprobació a priori.
			// En cas que existeixin afegeixo els errors a la variable result indicant el seu missatge.
			if (!comprobacionCodigo(tasca))
				result.rejectValue("codigo", "tasca.codigoExistente");
			
			if(!comprobacionNombre(tasca))
				result.rejectValue("nombre", "tasca.nombreExistente");
			
			// L'anotacio @Valid ha validat les dades de fase a partir de les anotacions fetes a la classe
			// fase i els errors que ha trobat els ha passat a result amb els missatges asociats.
			// Si la variable result conté qualsevol error llavors passem l'informació de que no s'ha pogut
			// guardar el registre i redireccionem a crear. Implicitament result assigna al model el camp errors
			// que gestionarem amb thymeleaf per coneixer quins errors han hagut. Cridem de nou a la vista crear.
			
			if(result.hasErrors()) {		
				flash.addFlashAttribute("error", "No ha estat possible guardar les dades");		
				return "tasques/crear";
			}		
			
			// Posem el temps estimat a 0.
			tasca.setTiempoEstimado(0);
			// Generem les fases amb temps a partir de les fases i les afegim a la tasca
			List<FaseConTiempo> fasesConTiempo=FaseConTiempo.generarLista(tasca.getFases());
			tasca.setFasesConTiempo(fasesConTiempo);			
			
			// Redireccionem a configurar les tasques
			return "tasques/configurar";
		}
		
		/**
		 * Mètode que crida a la vista listar on podem veure totes les tasques de la base de dades que poden estar filtrades per nom i codi.
		 * @param model Model que passem a la vista
		 * @param keyword Paràmetre que fem servir per filtrar la llista
		 * @return Crida a la vista listar de tasques si existeix l'empresa, en cas contrari redirecciona a home.
		 */
		@Secured("ROLE_ADMIN")
		@GetMapping("/listar")
		public String listar(Model model, String keyword) {
			
			// Comprobem que la empresa existeixi, si no existeix a la base de dades redireccionem a home.
			if(empresa==null)empresa=empresaService.findById(1);
			if(empresa==null)return "redirect:/";
					
			List<Tasca> tasques=new ArrayList<>();
			// Variable que passarem a la vista perquè aquesta sapigui si hi ha filtre o no.
			boolean filtrado;
			
			// Si no s'ha escrit res al cercador omplim la llista de departaments amb totls els que hi ha a la base de dades
			// i marquem filtrado com false.
			if(keyword==null) {
				
				tasques=tascaService.findAll();
				filtrado=false;	
				
			}else {
				// Si hi ha filtre volem que aquest busqui tant per codi com per nom per tant descarreguem les dues llistes
				// i les combinem perquè no hi hagi repetició. Finalment marquem filtrado com true.
				List<Tasca> tasquesPorCodigo=tascaService.findByCodigoStartsWith(keyword.toUpperCase());
				List<Tasca> tasquesPorNombre=tascaService.findByNombreStartsWith(keyword);			
				tasques=Utilidades.combinarListasSinRepeticion(tasquesPorCodigo, tasquesPorNombre);
				filtrado=true;
			}		
			
			// Afegirm al model el títol, la llista de departaments, si està filtrat o no i la empresa.
			model.addAttribute("titol", "Llistat de fases");
			model.addAttribute("tasques", tasques);
			model.addAttribute("filtrado", filtrado);
			model.addAttribute("empresa",empresa);
			
			// Cridem a la vista listar.
			return "tasques/listar";
		}
		
		/**
		 * Mètode que elimina de la base de dades la tasca amb l'id rebut per paràmetre
		 * @param id Id de la tasca que volem eliminar
		 * @param flash  Variable utilitzada per passar missatges a la vista.
		 * @return Crida a la vista listar de tasques si la empresa existeix i trobem, en cas contrari redireccionem a home.
		 */
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

				// Busquem la tasca a la base de dades
				Tasca tasca = tascaService.findById(id);
				
				// Si la trobem l'eliminem
				if(tasca!=null) {
					// Eliminem la tasca a la base de dades
					tascaService.delete(tasca);
					
					// Per saber si hem eliminat el registre, si no el trobem enviem un missatge com que s'he eliminat
					// correctament, en cas contrari enviem un missatge d'error
					if(tascaService.findById(id)!=null) {
						
						flash.addAttribute("success", "Tasca " + tasca.getNombre() + " esborrada amb èxit");					
					}else {
						
						flash.addAttribute("error", "La tasca " + tasca.getNombre() + " no s'ha pogut eliminar");
					}
				}						

			}
			
			// Redireccionem a la vista listar		
			return "redirect:/tasques/listar";
		}
		
		/**
		 * Mètode que crida a la vista crear passant-li les dades de la tasca que tè per id el rebut per paràmetre per
		 * poder ser modificat.
		 * @param id Id de la tasca que volem editar
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
			if(empresa==null)return "redirect:/";	
			
			Tasca tasca = null;
			
			// Si ja s'ha passat per llistar i criden al mètode des del navegador el mètode funcionarà, per tant hem
			// de controlar que el paràmetre no sigui -1 per exemple, volem que sigui més gran que cero.
			
			if (id > 0) {
				// Busquem la fase a la base de dades
				tasca = tascaService.findById(id);
				// Si no existeix a la base de dades redireccionem a llistar amb el missatge corresponent.
				if (tasca == null) {
					flash.addFlashAttribute("error", "L'ID de la tasca no existeix a la BBDD");
					return "redirect:/tasques/listar";
				}
			} else {
				
				// Si l'id és erroni redireccionem a listar amb el missatge corresponent.
				flash.addFlashAttribute("error", "L'ID ha de ser un número més gran que 0");
				return "redirect:/tasques/listar";
			}
			
			// Canviem el titol i el text del bóto de la vista crear canviant els atributs corresponents		
			titol="Actualitzar tasca";
			titolBoto="Actualitzar tasca";
			
			// passem la fase trobada, el titol, el text del botó i l'empresa a la vista.
			model.addAttribute("titol", titol);
			model.addAttribute("tasca", tasca);
			model.addAttribute("titolBoto", titolBoto);
			model.addAttribute("empresa", empresa);
			
			// Cridem a la vista crear
			return "tasques/crear";
		}
		
		
		/**
		 * Funció que crida la vista ver amb el detall de la tasca rebuda per paràmetre
		 * @param id És l'identificador de la tasca de la qual volem veure el detall
		 * @param model És el model que passem a la vista
		 * @return Crida a la vista tasques/ver si l'atribut empresa  no és null i la tasca passada
		 * per paràmetre es troba a la base de dades, si no existeix la empresa redirecciona a home i si no existeix la 
		 * tasca redirecciona a llistar. 
		 */
		@Secured("ROLE_ADMIN")
		@GetMapping("/ver/{id}")
		public String ver(@PathVariable Long id, Model model) {
			
			// Si la variable empresa encara és igual a null la cerquem
			if(empresa==null)empresa=empresaService.findById(1);
			// Si desprès de cercar no tenim empresa llavors redireccionem a home
			if(empresa==null)return "redirect:/";	
			
			// Busquem la tasca demanada per paràmetre, si no es troba a la base de dades redireccionem a llistar.
			Tasca tasca=tascaService.findById(id);
			if(tasca==null)return "redirect:listar";		
			
			// Afegim a la vista la tasca trobada, les fases que conté i un títol per la vista,
			// a més passem l'atribut empresa per que la vista pugui crear la capçalera.
			model.addAttribute("tasca", tasca);
			model.addAttribute("titol", "Detall tasca " + tasca.getNombre());			
			model.addAttribute("empresa",empresa);
			model.addAttribute("fases", tasca.getFasesConTiempo());
			
			// Cridem a la vista ver.
			return "tasques/ver";
		}
		
		
		@PostMapping("/configurar")
		public String configurar(Tasca tasca, Model model,	RedirectAttributes flash, SessionStatus status){
			
			// Afegim el titol, el text del botó i l'empresa abans de gestionar res més perquè ens fa falta per qualsevol dels casos.
						model.addAttribute("titol", titol);
						model.addAttribute("titolBoto", titolBoto);
						model.addAttribute("empresa", empresa);
						model.addAttribute("tasca", tasca);
						
			// Si la fase és cíclica el temps del cicle ha de ser més gran que cero
			if(tasca.isCiclica() && tasca.getTiempoCiclo()<1) {
				
				flash.addAttribute("error", "El temps de cicle ha de ser més gran que zero");
				return "tasques/cofigurar";
			}			
				
			// Els temps estimats de les fases ha de ser superior a cero.
			for(FaseConTiempo fase: tasca.getFasesConTiempo()) {
				
				if(fase.getTiempoEstimado()<1) {
					
					flash.addAttribute("error", "El temps d'una fase ha de ser més gran que zero");
					return "tasques/cofigurar";
					
				}
			}
			
			// Calculem el temps de la tasca
			tasca.calcularTiempoEstimado();
			
			
			// Guardem el registre	
			if(tascaService.save(tasca)!=null) {
				
				// Guardem les fases amb temps amb el nou temps estimat
				guardarFasesConTiempo(tascaService.findByCodigo(tasca.getCodigo()));
			
				// Afegim al model el missatge que s'ha guardat correctament 
				flash.addFlashAttribute("success", "Registre guardat amb èxit");
			}else {
				
				// Afegim al model el missatge que no s'ha guardat correctament 
				flash.addFlashAttribute("errors", "El registre no s'ha pogut guardar a la base de dades");
			}
			
			// Confimem que s'ha completat tot el procès i que ja no cal que guardi les dades de tasca.
			status.setComplete();			
			return "redirect:listar";
		}
		
		/**
		 * Mètode que comproba que el codi posat a la tasca passada per paràmetre no existeixi a la base de dades
		 * @param tasca Tasca de la qual volem comprobar el codi
		 * @return Retorna true si no existeix i false en cas contrari
		 */
		private boolean comprobacionCodigo(Tasca tasca) {

			// Inicialitzem result a true. Nomès el canviarem si existeix a la base de dades
			boolean result = true;
			
			// Busquem la tasca per codi a la base de dades. Passen el contingut del codi de tasca
			// a majúscules perquè quan guardem el codi a la base dades ho fem en majúscules.
			Tasca tascaPerCodi=tascaService.findByCodigo(tasca.getCodigo().toUpperCase());
			
			// Si el trobem vol dir que aquest codi existeix a la base de dades. Com podriem estar fent una
			// actulització això es podria donar i ser valid, en cas de que siguès una actualització l'id de la tasca
			// passat per paràmetre i l'id de la fase trobat serien iguals. En cas contrari llavors vol dir
			// que ja existeix a la base de dades, per tant com és únic donaria error en fer save. Pasem result a false.
			if(tascaPerCodi!=null) {
							
				if(tasca.getId()!=tascaPerCodi.getId()) {
					result=false;			
				}
				
			}			

			// Retornem el contingut de result
			return result;
		}
		
		/**
		 * Mètode que comproba que el nom posat a la tasca no existixi a la base de dades
		 * @param tasca Tasca que volem comprobar
		 * @return Retorna true si no existeix a la base de dades i false en cas contrari
		 */
		private boolean comprobacionNombre(Tasca tasca) {
			
			// Mateix criteri que a comprobacionCodigo.
			
			boolean result=true;
			Tasca tascaPerNom=tascaService.findByNombre(tasca.getNombre());
			
			if(tascaPerNom!=null) {
				
				if(tasca.getId()!=tascaPerNom.getId()) {
					result=false;
				}
			
			}	
			
			return result;
		}
		
		/**
		 * Mètode que guarda a la base de dades les fases amb temps pertanyents a la tasca pasada per paràmetre
		 * @param tasca Tasca de la qual volem guardar les fases amb temps
		 */
		private void guardarFasesConTiempo(Tasca tasca) {
			
			for(FaseConTiempo fase: tasca.getFasesConTiempo()) {
				// Afegim la tasca a la fase amb temps
				fase.setTasca(tasca);
				// Guardem la fase amb temps
				faseConTiempoService.save(fase);
			}
			
		}
		
		@ModelAttribute("usuariAutenticat")
		public Usuari getUsuariAuthenticat() {
			
			return Usuari.USUARIAUTENTICAT;
		}
		
		@ModelAttribute("listaFases")
		public List<Fase> getFases(){
			
			return faseService.findAll();
		}		

}
