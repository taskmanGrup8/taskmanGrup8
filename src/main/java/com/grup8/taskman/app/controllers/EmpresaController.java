package com.grup8.taskman.app.controllers;

import java.io.IOException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.grup8.taskman.app.domain.empreses.Empresa;
import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.services.empresa.IEmpresaService;
import com.grup8.taskman.app.services.imatges.IImatgesHandlerService;

/**
 * Controlador que gestiona les dades de l'empresa. L'aplicació ha de tenir obligatoriament una empresa,
 * aquesta serà donada d'alta el primer cop que s'utilitzi. Aquesta empresa no podrà ser eliminada però
 * si editada.
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.1
 *
 */

@Controller
@RequestMapping("/empreses")
@SessionAttributes("empresa")
public class EmpresaController {
	
	// ATRIBUTS
	
	// Injecció del service de empresa per poder realitzar accions amb la taula empreses de la base de dades.
	@Autowired
	IEmpresaService empresaService;
	
	// Injecció del service d'imatges que ens permetrà pujar imatges al sistema
	@Autowired
	IImatgesHandlerService imatgesService;
	
	private String titolBoto;
	private String titol;
	private boolean crear; // Aquest mètode serveix perquè la vista sapigui si estem creant o actualitzant
	
	
	// MÈTODES
	
	/**
	 * Mètode que crida a la vista crear que conté el formulari per crear una empresa
	 * @param model
	 * @return
	 */
	@Secured("ROLE_SUPER")
	@GetMapping("/crear")
	public String crear(Model model) {
				
		titol="Dona d'alta la teva empresa a Taskman!";
		titolBoto="Enviar dades";
		crear=true;
		
		
		// Passem les dades al model per poder tenir-los a la vista
		model.addAttribute("titol", titol);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("alerta", "És necessari que et donis d'alta per continuar");
		model.addAttribute("empresa", new Empresa());
		model.addAttribute("crear", crear);
		
		
		return "empresas/crear"; // Cridem a la vista que conté el formulari per crear empreses.
	}
	
	/**
	 * Funció que es crida des de la vista crear i que rep les dades del formulari emplenat per l'usuari, valida les
	 * dades i les guarda a la base de dades si tot és correcte. Si el formulari no ha estat emplenat correctament i 
	 * conté errors llavors torna a cridar a la vista crear.
	 * @param empresa Instancia de Empresa amb les dades introduïdes al formulari crear
	 * @param result Instancia que conté els errors que han hagut al formulari.
	 * @param model És el model que passem a la vista
	 * @param logo Arxiu que contindrà el logo de la empresa
	 * @param flash Variable utilitzada per passar missatges a la vista.
	 * @param status Variable que controla l'estat en aquest cas de la variable empresa.
	 * @return De moment redirecciona cap al perfil del superadministrador si tot és correcte, 
	 * en cas contrari crida a la vista crear.
	 */
	@PostMapping("/result")
	public String guardar(@Valid Empresa empresa, BindingResult result, Model model, @RequestParam("file") MultipartFile logo, 
			RedirectAttributes flash, SessionStatus status) {
		
		
		model.addAttribute("titol", titol);
		model.addAttribute("titolBoto", titolBoto);
		
		// Si hi ha errors tornem a cridar la vista crear
		if(result.hasErrors()) {		
			flash.addFlashAttribute("error", "No ha estat possible guardar les dades");		
			return "empresas/crear";
		}		
		
		// Gestionem l'arxiu logo rebut des del formulari
		// Si logo no està buït llavors vol dir que ha pujat alguna cosa
		if(!logo.isEmpty()) {
			
			// Si es tracta d'una actualització hem d'esborrar l'anterior logo per tant ens assegurem que l'empresa
			// existeix i que té un logo pujat.
			if(empresa.getId()!=null && empresa.getId()>0 && empresa.getLogo()!=null && empresa.getLogo().length()>0) {
				
				// Esborrem el logo anterior.
				imatgesService.delete(empresa.getLogo());
			}
			
			// Intentem guardar la imatge i informarem del resultat
			String uniqueFilename=null;
			try {
				//Guardem la imatge i ens retorna el path on ha estat guardat
				uniqueFilename=imatgesService.copy(logo);
				flash.addAttribute("info", "Imatge gravada amb èxit");
				
			}catch(IOException e) {
				
				// Si no es pot guardar informem.
				flash.addAttribute("error", "No s'ha pogut guardar la imatge");
			}
			
			// Guardem a empresa la ruta de l'arxiu, si no s'ha pogut guardar serà null
			empresa.setLogo(uniqueFilename);
			
		}
		
		// Guardem l'empresa
		if(empresaService.save(empresa)!=null) {
			
			flash.addFlashAttribute("success", "Registre gravat amb èxit");
		}else {
			
			flash.addFlashAttribute("errors", "No s'ha pogut guardar el registre");
		}
		
		// Completem l'status de la variable empresa i informem
		status.setComplete();
		
		// De moment redireccionem al perfil de l'usuari
		return "redirect:/perfil";
	}
	
	/**
	 * Mètode que crida a la vista crear, passant-li les dades de la empresa amb id 1 perquè nomès pot existir
	 * una empresa, per modificar la mateixa.
	 * @param model És el model que passem a la vista
	 * @return Si no troba empresa redirecciona a crear, en cas contrari crida a la vista crear passant les dades de l'empresa.
	 */
	@Secured("ROLE_SUPER")
	@GetMapping("/actualizar")
	public String actualizar(Model model) {
		
		// Si no trobem l'empresa redireccionem a crear
		Empresa empresa=empresaService.findById(1);
		if(empresa==null)return "redirect:crear";
		
		crear=false;
		
		// Passem els elements necessaris al model
		titol="Actualitzar empresa";
		titolBoto="Enviar dades";		
		model.addAttribute("titol", titol);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);	
		model.addAttribute("crear", crear);
		
		// Cridem a la vista crear
		
		return "empresas/crear";		
		
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/leerPrivacidad")
	public String leerPrivacidad(Model model) {
		
		model.addAttribute("aceptar", false);
		
		return "empresas/politica";
		
	}
	
	
	@ModelAttribute("usuariAutenticat")
	public Usuari getUsuariAuthenticat() {
		
		return Usuari.USUARIAUTENTICAT;
	}

}
