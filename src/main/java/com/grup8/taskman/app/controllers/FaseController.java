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
import com.grup8.taskman.app.domain.departaments.Departament;
import com.grup8.taskman.app.domain.empreses.Empresa;
import com.grup8.taskman.app.domain.tasques.Fase;
import com.grup8.taskman.app.domain.tasques.FaseConTiempo;
import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.services.departament.IDepartamentService;
import com.grup8.taskman.app.services.empresa.IEmpresaService;
import com.grup8.taskman.app.services.tasques.IFaseConTiempoService;
import com.grup8.taskman.app.services.tasques.IFaseService;
import com.grup8.taskman.app.services.tasques.ITascaService;
import com.grup8.taskman.app.util.Utilidades;

@Controller
@RequestMapping("/fases")
@SessionAttributes("fase")
public class FaseController {
	
	// ATRIBUTS
	
		// Injectem el servei de departaments per poder realitzar accions a la taula departaments.	
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
		 * Funció que crida a la vista crear que conté el formulari per crear un departament
		 * @param model És el model que passem a la vista
		 * @return Crida a la vista "departaments/crear" si l'atribut empresa no és null, en cas contrari redirecciona 
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
			titol="Crear nova fase";
			titolBoto="Enviar dades";
			
			// Afegim al model el titol i el text del botó acceptar del formulari, un nou departament buït i l'atribut empresa.
			model.addAttribute("titol", titol);
			model.addAttribute("fase", new Fase());
			model.addAttribute("titolBoto", titolBoto);
			model.addAttribute("empresa", empresa);
			
			// Cridem a la vista crear.
			return "fases/crear";
		}
		
		/**
		 * Funció que es crida des de la vista crear i que rep les dades del formulari emplenat per l'usuari, valida les
		 * dades i les guarda a la base de dades si tot és correcte. Si el formulari no ha estat emplenat correctament i 
		 * conté errors llavors torna a cridar a la vista crear.
		 * @param departament Instancia de departament amb el contingut del formulari
		 * @param result Instancia que conté els errors que han hagut al formulari.
		 * @param model És el model que passem a la vista
		 * @param flash Variable utilitzada per passar missatges a la vista.
		 * @param status Variable que controla l'estat en aquest cas de la variable departament.
		 * @return Redirecciona a llistar departaments si tot és correcte, en cas contrari crida a la vista crear.
		 */
		@PostMapping("/result")
		public String guardar(@Valid Fase fase, BindingResult result, Model model, 
				RedirectAttributes flash, SessionStatus status) {
			
			// Afegim el titol, el text del botó i l'empresa abans de gestionar res més perquè ens fa falta per qualsevol dels casos.
			model.addAttribute("titol", titol);
			model.addAttribute("titolBoto", titolBoto);
			model.addAttribute("empresa", empresa);
			model.addAttribute("fase", fase);
			
			// Volem que tant el codi com el nom de la fase no es puguin repetir a la base de dades
			// i encara que ho podriem fer amb try catch en guardar m'estimo més fer la comprobació a priori.
			// En cas que existeixin afegeixo els errors a la variable result indicant el seu missatge.
			if (!comprobacionCodigo(fase))
				result.rejectValue("codigo", "fase.codigoExistente");
			
			if(!comprobacionNombre(fase))
				result.rejectValue("nombre", "fase.nombreExistente");
			
			// L'anotacio @Valid ha validat les dades de fase a partir de les anotacions fetes a la classe
			// fase i els errors que ha trobat els ha passat a result amb els missatges asociats.
			// Si la variable result conté qualsevol error llavors passem l'informació de que no s'ha pogut
			// guardar el registre i redireccionem a crear. Implicitament result assigna al model el camp errors
			// que gestionarem amb thymeleaf per coneixer quins errors han hagut. Cridem de nou a la vista crear.
			
			if(result.hasErrors()) {		
				flash.addFlashAttribute("error", "No ha estat possible guardar les dades");		
				return "fases/crear";
			}		
			
			// Guardem el registre	
			if(faseService.save(fase)!=null) {
			
				// Afegim al model el missatge que s'ha guardat correctament 
				flash.addFlashAttribute("success", "Registre guardat amb èxit");
			}else {
				
				// Afegim al model el missatge que no s'ha guardat correctament 
				flash.addFlashAttribute("errors", "El registre no s'ha pogut guardar a la base de dades");
			}
			
			// Confimem que s'ha completat tot el procès i que ja no cal que guardi les dades de departament.
			status.setComplete();
			// Redireccionem a llistar
			return "redirect:listar";
		}
		
		
		@Secured("ROLE_ADMIN")
		@GetMapping("/listar")
		public String listar(Model model, String keyword) {
			
			// Comprobem que la empresa existeixi, si no existeix a la base de dades redireccionem a home.
			if(empresa==null)empresa=empresaService.findById(1);
			if(empresa==null)return "redirect:/";
					
			List<Fase> fases=new ArrayList<>();
			// Variable que passarem a la vista perquè aquesta sapigui si hi ha filtre o no.
			boolean filtrado;
			
			// Si no s'ha escrit res al cercador omplim la llista de departaments amb totls els que hi ha a la base de dades
			// i marquem filtrado com false.
			if(keyword==null) {
				
				fases=faseService.findAll();
				filtrado=false;	
				
			}else {
				// Si hi ha filtre volem que aquest busqui tant per codi com per nom per tant descarreguem les dues llistes
				// i les combinem perquè no hi hagi repetició. Finalment marquem filtrado com true.
				List<Fase> fasesPorCodigo=faseService.findByCodigoStartsWith(keyword.toUpperCase());
				List<Fase> fasesPorNombre=faseService.findByNombreStartsWith(keyword);			
				fases=Utilidades.combinarListasSinRepeticion(fasesPorCodigo, fasesPorNombre);
				filtrado=true;
			}		
			
			// Afegirm al model el títol, la llista de departaments, si està filtrat o no i la empresa.
			model.addAttribute("titol", "Llistat de fases");
			model.addAttribute("fases", fases);
			model.addAttribute("filtrado", filtrado);
			model.addAttribute("empresa",empresa);
			
			// Cridem a la vista listar.
			return "fases/listar";
		}
		
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

				// Busquem la fase a la base de dades
				Fase fase = faseService.findById(id);
				
				// Si el trobem l'eliminem
				if(fase!=null) {
					// Eliminem la fase a la base de dades
					List<FaseConTiempo> lista= faseConTiempoService.findByFase(fase.getId());					
					for(FaseConTiempo f: lista) {
						
						tascaService.delete(f.getTasca());
					}					
					
					faseService.delete(fase);
					
					// Per saber si hem eliminat el registre, si no el trobem enviem un missatge com que s'he eliminat
					// correctament, en cas contrari enviem un missatge d'error
					if(faseService.findById(id)!=null) {
						
						flash.addAttribute("success", "Fase " + fase.getNombre() + " esborrada amb èxit");					
					}else {
						
						flash.addAttribute("error", "La fase " + fase.getNombre() + " no s'ha pogut eliminar");
					}
				}						

			}
			
			// Redireccionem a la vista listar		
			return "redirect:/fases/listar";
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
			if(empresa==null)return "redirect:/";	
			
			Fase fase = null;
			
			// Si ja s'ha passat per llistar i criden al mètode des del navegador el mètode funcionarà, per tant hem
			// de controlar que el paràmetre no sigui -1 per exemple, volem que sigui més gran que cero.
			
			if (id > 0) {
				// Busquem la fase a la base de dades
				fase = faseService.findById(id);
				// Si no existeix a la base de dades redireccionem a llistar amb el missatge corresponent.
				if (fase == null) {
					flash.addFlashAttribute("error", "L'ID de la fase no existeix a la BBDD");
					return "redirect:listar";
				}
			} else {
				
				// Si l'id és erroni redireccionem a listar amb el missatge corresponent.
				flash.addFlashAttribute("error", "L'ID ha de ser un número més gran que 0");
				return "redirect:listar";
			}
			
			// Canviem el titol i el text del bóto de la vista crear canviant els atributs corresponents		
			titol="Actualitzar fase";
			titolBoto="Actualitzar fase";
			
			// passem la fase trobada, el titol, el text del botó i l'empresa a la vista.
			model.addAttribute("titol", titol);
			model.addAttribute("fase", fase);
			model.addAttribute("titolBoto", titolBoto);
			model.addAttribute("empresa", empresa);
			
			// Cridem a la vista crear
			return "fases/crear";
		}
		
		
		/**
		 * Funció que crida la vista ver amb el detall de la fase rebuda per paràmetre
		 * @param id És l'identificador de la fase de la qual volem veure el detall
		 * @param model És el model que passem a la vista
		 * @return Crida a la vista fases/ver si l'atribut empresa  no és null i la fase passada
		 * per paràmetre es troba a la base de dades, si no existeix la empresa redirecciona a home i si no existeix la 
		 * fase redirecciona a llistar. 
		 */
		@Secured("ROLE_ADMIN")
		@GetMapping("/ver/{id}")
		public String ver(@PathVariable Long id, Model model) {
			
			// Si la variable empresa encara és igual a null la cerquem
			if(empresa==null)empresa=empresaService.findById(1);
			// Si desprès de cercar no tenim empresa llavors redireccionem a home
			if(empresa==null)return "redirect:/";	
			
			// Busquem la fase demanada per paràmetre, si no es troba a la base de dades redireccionem a llistar.
			Fase fase=faseService.findById(id);
			if(fase==null)return "redirect:listar";		
			
			// Afegim a la vista la fase trobada, els usuaris que conté, un títol per la vista i el text del bóto,
			// a més passem l'atribut empresa per que la vista pugui crear la capçalera.
			model.addAttribute("fase", fase);
			model.addAttribute("titol", "Detall fase " + fase.getNombre());			
			model.addAttribute("empresa",empresa);
			
			// Cridem a la vista ver.
			return "fases/ver";
		}
		
		private boolean comprobacionCodigo(Fase fase) {

			// Inicialitzem result a true. Nomès el canviarem si existeix a la base de dades
			boolean result = true;
			
			// Busquem la fase per codi a la base de dades. Passen el contingut del codi de fase
			// a majúscules perquè quan guardem el codi a la base dades ho fem en majúscules.
			Fase fasePerCodi=faseService.findByCodigo(fase.getCodigo().toUpperCase());
			
			// Si no el trobem vol dir que aquest codi existeix a la base de dades. Com podriem estar fent una
			// actulització això es podria donar i ser valid, en cas de que siguès una actualització l'id de la fase
			// passat per paràmetre i l'id de la fase trobat serien iguals. En cas contrari llavors vol dir
			// que ja existeix a la base de dades, per tant com és únic donaria error en fer save. Pasem result a false.
			if(fasePerCodi!=null) {
							
				if(fase.getId()!=fasePerCodi.getId()) {
					result=false;			
				}
				
			}			

			// Retornem el contingut de result
			return result;
		}
		
		private boolean comprobacionNombre(Fase fase) {
			
			// Mateix criteri que a comprobacionCodigo.
			
			boolean result=true;
			Fase fasePerNom=faseService.findByNombre(fase.getNombre());
			
			if(fasePerNom!=null) {
				
				if(fase.getId()!=fasePerNom.getId()) {
					result=false;
				}
			
			}	
			
			return result;
		}
		
		@ModelAttribute("usuariAutenticat")
		public Usuari getUsuariAuthenticat() {
			
			return Usuari.USUARIAUTENTICAT;
		}
		
		@ModelAttribute("listaDepartaments")
		public List<Departament> getDepartaments(){
			
			return departamentService.findAll();
		}

		

}
