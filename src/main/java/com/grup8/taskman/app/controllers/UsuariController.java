package com.grup8.taskman.app.controllers;

import java.io.IOException;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.grup8.taskman.app.domain.departaments.Departament;
import com.grup8.taskman.app.domain.empreses.Empresa;
import com.grup8.taskman.app.domain.usuaris.FiltreUsuaris;
import com.grup8.taskman.app.domain.usuaris.Rol;
import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.security.PasswordChange;
import com.grup8.taskman.app.services.departament.IDepartamentService;
import com.grup8.taskman.app.services.empresa.IEmpresaService;
import com.grup8.taskman.app.services.imatges.IImatgesHandlerService;
import com.grup8.taskman.app.services.rol.IRolService;
import com.grup8.taskman.app.services.usuari.IUsuariService;
import com.grup8.taskman.app.util.PageRender;

/**
 * Classe que controla el m??dul d'usuaris.
 * Rebra les sol.licituts a partir de la url "/usuaris" i tindr??
 * les funcionalitats de crear, actualitzar, guardar, llistar, veure detall de l'usuari sel.leccionat,
 * canviar les dades personals i la contrasenya de l'usuari autenticat.
 * 
 *  Cada m??tode referent a url estar?? anotat amb @secured i el rol m??nim de l'usuari que el pot utilitzar.
 *  
 *  Si alg??n crida a qualsevol de les url des del navegador si l'atribut empresa ??s null 
 *  es redireccionar?? l'aplicaci?? a home.
 *  
 *  L'empresa ha de estar creada abans de fer servir qualsevol funcionalitat.
 *  
 *  Passarem la variable anomenada usuari a SessionAttributes perqu?? guardi el valor de la mateixa 
 *  d'un a altre m??tode. 
 *  
 * @author Sergio Esteban Guti??rrez
 * @version 1.0.1
 *
 */

@Controller
@RequestMapping("/usuaris")
@SessionAttributes("usuari")
public class UsuariController {
	
	public static final int CREAR=1;
	public static final int MODIFICAR=2;
	public static final int MODIFICAR_PERFIL=3;
	public static final int CAMBIAR_CONTRASE??A=4;
	

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
	
	// Injectem el bean per l'encriptaci??
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// Injecci?? del service d'imatges que ens permetr?? pujar imatges al sistema
	@Autowired
	IImatgesHandlerService imatgesService;
	

	
	private String titolBoto;
	private String titol;
	private int funcion;
	private Empresa empresa;

	// M??TODES
	
	/**
	 * Funci?? que crida a la vista crear que cont?? el formulari per crear un usuari. Nom??s qui tingui un rol
	 * d'administrador o superior el podr?? fer servir.
	 * @param model ??s el model que passem a la vista
	 * @return Crida a la vista "usuaris/crear" si l'atribut empresa no ??s null, en cas contrari redirecciona 
	 * a home.
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping("/crear")
	public String crear(Model model) {
		
		
		empresa=empresaService.findById(1);
		
		// Si despr??s de cercar no tenim empresa llavors redireccionem a home
		if(empresa==null)return "redirect:/perfil";	

		titol = "Crear nou usuari";
		titolBoto = "Enviar dades";
		funcion=CREAR;
		Usuari usuari=new Usuari();
		usuari.setPrivacidadFirmada(false); // Per defecte l'usuari no ha signat la privacitat
		usuari.setActivo(true); // Per defecte l'usuari pertany a l'empresa					
		usuari.setPassword("1234"); // Password per defecte, m??s endavant el pot canviar
		usuari.setFoto(Usuari.DEFAULT_IMG_PROFILE);
		model.addAttribute("titol", titol);
		model.addAttribute("usuari", usuari);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);
	

		return "usuaris/crear";
	}

	// Afegim als models sempre l'atribut listaRoles que cont?? la llista amb tots els rols
	@ModelAttribute("listaRoles")
	public List<Rol> listaRoles() {

		// Busquem tots els rols
		List<Rol> listaRoles=rolService.findAll();
		
		// Si l'usuari no ??s Taskman llavors treiem els rols que tenen id superior a 2 perqu?? 1 i 2 son usuari i administrador
		if(Usuari.USUARIAUTENTICAT.getRol().getId()!=Rol.TASKMAN) {			
			
			for(int i=listaRoles.size()-1; i>-1; i--) {
				
				if(listaRoles.get(i).getId()>2)listaRoles.remove(i);
			}
		}
		
		// Retornem la llista de rols
		return listaRoles;
	}

	// Afegim als models sempre l'atribut listaDepartamentos que cont?? la llista amb tots els departaments
	@ModelAttribute("listaDepartamentos")
	public List<Departament> listaDepartamentos() {

		return departamentService.findAll();
	}

	/**
	 * Funci?? que es crida des de la vista crear, actualitzar o modificar_perfil. Rep les dades del formulari emplenat 
	 * per l'usuari, valida les dades i les guarda a la base de dades si tot ??s correcte. Si el formulari no ha estat 
	 * emplenat correctament i cont?? errors llavors torna a cridar a la vista que correspongui.
	 * @param usuari Instancia d'usuari amb el contingut del formulari
	 * @param result Instancia que cont?? els errors que han hagut al formulari.
	 * @param model ??s el model que passem a la vista
	 * @param foto ??s l'arxiu d'imatge de perfil carregada a la vista per l'usuari
	 * @param flash Variable utilitzada per passar missatges a la vista.
	 * @param status Variable que controla l'estat en aquest cas de la variable usuari.
	 * @return Redirecciona a diferents urls segons de quina funci?? provingui la crida.
	 */
	@PostMapping("/guardar")
	public String guardar(@Valid Usuari usuari, BindingResult result, Model model,
			@RequestParam(name="file", required=false) MultipartFile foto, 
			RedirectAttributes flash, SessionStatus status) {
		
		

		// Afegim al model els atributs necessaris
		model.addAttribute("titol", titol);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);
		
		// Validem el dni perqu?? no existeixi a la base de dades
		// Aix?? nom??s ho validarem quan el creem per primer cop, despr??s el dni no es pot canviar
		if (!comprobacionDni(usuari) && funcion==CREAR)
			result.rejectValue("dni", "usuari.dniExistente");
		
		// el primer cop que creem un usuari username es null per tant l'assignem el dni com username
		if(usuari.getUsername()==null) {	
			
			usuari.setUsername(usuari.getDni());
		}
		
		// Comprovem que username es ??nic. A la creaci?? ja hem posat el dni que sabem que ??s unic per tant nom??s ho validem
		// quan modifiquem el perfil
		if (!comprobacionUsername(usuari) && funcion==MODIFICAR_PERFIL)
			result.rejectValue("username", "usuari.usernameExistente");

		// Si result cont?? errors tornem a cridar la que correspongui avisant de l'error
		if (result.hasErrors()) {
			flash.addFlashAttribute("error", "No ha estat possible guardar les dades");
		
			if(funcion==MODIFICAR_PERFIL) {
				return "usuaris/modificar_perfil";
			}else {
				return "usuaris/crear"; // Actualitzar tamb?? crida la vista crear
			}
		}

		// Comprobem si tenim una foto
		
		if(foto!=null && !foto.isEmpty()) {
			
			// Si es tracta d'una actualitzaci?? hem d'esborrar l'anterior foto per tant ens assegurem que l'usuari
			// existeix i que t?? una foto pujada.
			if(usuari.getId()!=null && usuari.getId()>0 && usuari.getFoto()!=null && usuari.getFoto().length()>0) {
				
				// Esborrem la foto anterior si no ??s la foto per defecte.
				if(!usuari.getFoto().equalsIgnoreCase(Usuari.DEFAULT_IMG_PROFILE))imatgesService.delete(usuari.getFoto());
			}
			
			// Intentem guardar la imatge i informarem del resultat
			String uniqueFilename=null;
			try {
				//Guardem la imatge i ens retorna el path on ha estat guardat
				uniqueFilename=imatgesService.copy(foto);
				flash.addAttribute("info", "Imatge gravada amb ??xit");
				
			}catch(IOException e) {
				
				// Si no es pot guardar informem.
				flash.addAttribute("error", "Nos'ha pogut guardar la imatge");
			}
			
			// Guardem a usuari la ruta de l'arxiu, si no s'ha pogut guardar ser?? null
			usuari.setFoto(uniqueFilename);			
		}
		
		// Si estem creant un usuari nou assignem el password 1111 i l'encriptem.
		if(funcion==CREAR) {
		
			String passwordEncriptado=passwordEncoder.encode(usuari.getPassword());
			usuari.setPassword(passwordEncriptado);				
		}
		
		// Si creem o actualitzem un usuari hem de tornar a assignar els permissos. 		
		if(funcion!=MODIFICAR_PERFIL) {
			
			// Per poder reassignar permissos primer eliminem els que t?? i despr??s tornem a assignar els que toquin
			usuari.eliminarPermisos();
			usuari.assignarPermissos();			
		
		}

		// Guardem l'usuari
		Usuari user=usuariService.save(usuari);
		// Mirem si l'usuari s'ha guardat correctament per enviar els missatges que toquin
		if(user.getId()!=null) {
			// Si l'usuari modificat correspon con l'autenticat actualitzem la constant
			if(Usuari.USUARIAUTENTICAT.getDni().equalsIgnoreCase(usuari.getDni())) {
				
				Usuari.USUARIAUTENTICAT=usuariService.findByDni(usuari.getDni());
			}
			
			flash.addFlashAttribute("success", "Registre guardat amb  ??xit");
		}else {
			
			flash.addFlashAttribute("error", "No s'ha pogut guardar el registre");
		}
		
		// Completem l'status d'usuari
		status.setComplete();
		
		// Si hem modificat perfil o canviat el password redireccionem a home
		if(funcion==MODIFICAR_PERFIL || funcion==CAMBIAR_CONTRASE??A)return "redirect:/";
		// Si hem creat o actualitzat un usuari llavors redireccionem a listar
		return "redirect:listar";
	}

	/**
	 * M??tode que crida a la vista listar passant-li els usuaris que hi han a la base de dades que compleixin
	 * el filtres demanats per la vista.
	 * @param page Variable que indica el n??mero de p??gina on ens trobem
	 * @param model ??s el model que es passa a la vista
	 * @return Si la empresa no existeix redirecciona a home, si no crida a la vista listar
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping("/listar")
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page,	Model model) {

		
		empresa=empresaService.findById(1);
		// Si despr??s de cercar no tenim empresa llavors redireccionem a home
		if(empresa==null) {
						
			return "redirect:/perfil";
		}		
		
		// Creem el pageable i donem l'ordre com volem les p??gines.
		Pageable pageRequest = PageRequest.of(page, 8, sortByIdAsc());
		
		// Obtenim la Page que passarem a la vista
		Page<Usuari> usuaris = FiltreUsuaris.filtreUsuari.getUsuaris(pageRequest, usuariService);
		// Fem el pageRender de la p??gina que passarem per par??metre
		PageRender<Usuari> pageRender = new PageRender<Usuari>("/usuaris/listar", usuaris);
		
		// Afegim els atributs necessaris al model per poder-los utilitzar a la vista
		model.addAttribute("titol", "Llistat d'usuaris");
		model.addAttribute("page", pageRender);
		model.addAttribute("filtreUsuari", FiltreUsuaris.filtreUsuari);
		model.addAttribute("usuaris", usuaris);
		model.addAttribute("empresa", empresa);

		// Cridem a la vista listar.
		return "usuaris/listar";
	}
	
	/**
	 * M??tode que actualitza la variable filtreUsuari i torna a cridar a listar perqu?? realitzi els filtres
	 * corresponents
	 * @param filtreUsuari Variable que te les dades del formulari de filtres de la vista listar
	 * @param model Model que passem a la vista
	 * @return Redirecciona a listar
	 */

	@PostMapping("/filtrar")
	public String filtrar(FiltreUsuaris filtreUsuari, Model model) {
		
		FiltreUsuaris.filtreUsuari=filtreUsuari;		
		return "redirect:listar";
	}
	
	/**
	 * M??tode que crida la vista ver amb el detall de l'usuari rebut per par??metre
	 * @param id ??s l'identificador de l'usuari del qual volem veure el detall
	 * @param model ??s el model que passem a la vista
	 * @return Crida a la vista usuaris/ver si l'atribut empresa  no ??s null i el departament passat
	 * per par??metre es troba a la base de dades, si no existeix la empresa redirecciona a home i si no existeix el 
	 * l'usuari redirecciona a listar. 
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Long id, Model model) {
		
		empresa=empresaService.findById(1);
		// Si despr??s de cercar no tenim empresa llavors redireccionem a home
		if(empresa==null)return "redirect:/perfil";	
		
		// Si no trobem l'usuari tornem a llistar
		Usuari usuari=usuariService.findById(id);		
		if(usuari==null) return "redirect:listar";	
		
		// Agafem la llista de departaments que t?? l'usuari
		List<Departament> departaments= usuari.getDepartaments();
		
		// Afegim els atributs al model
		model.addAttribute("departaments", departaments);
		model.addAttribute("titol", "Detall usuari " + usuari.getNombre());
		model.addAttribute("boton","Mostrar departaments");
		model.addAttribute("usuari",usuari);
		model.addAttribute("empresa", empresa);
		model.addAttribute("deps", usuari.getDepartaments());
		
		// Cridem a la vista ver
		return "usuaris/ver";
	}

	/**
	 * M??tode que elimina de la base de dades l'usuari del qual hem rebut l'id.		
	 * @param id Identificador de l'usuari que volem eliminar.	
	 * @param flash Variable que s'encarrega d'enviar els missatges a la vista a la que redireccionem.
	 * @return Si l'atribut empresa ??s null redirecciona a home, en cas contrari redirecciona a listar.
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {

		empresa=empresaService.findById(1);
		// Si despr??s de cercar no tenim empresa llavors redireccionem a home
		if(empresa==null)return "redirect:/perfil";	
		
		// Id a ser m??s gran que 0.
		if (id > 0) {
			// Busquem l'usuari
			Usuari user = usuariService.findById(id);
			
			// Si el trobem canviem l'atribut activo a false, guardem i retornem el resultat
			if (user != null) {
				// Quan posem activo en false, quan faci login no podr?? accedir al sistema.
				user.setActivo(false);							
				Usuari usuari=usuariService.save(user);
				if(usuari.getId()!=null) {
					flash.addFlashAttribute("success",
							"Usuari " + user.getNombre() + " " + user.getApellidos() + " eliminat amb ??xit");
				}else {
					
					flash.addFlashAttribute("error", "No s'ha pogut eliminar l'usuari!!!");
				}
			
			// Si no trobem l'usuari llavors informem de l'error
			} else {

				flash.addFlashAttribute("error", "L'usuari amb id " + id + " no existeix a la base de dades");
			}

		// Si l'id no es m??s gran que cero informem de l'error	
		} else {

			flash.addFlashAttribute("error", "L'id ha de ser m??s gran que 0");
		}

		// Redireccionem a listar
		return "redirect:/usuaris/listar";
	}

	/**
	 * M??tode que crida a la vista crear passant-li les dades de l'usuari que t?? per id el rebut per par??metre per
	 * poder ser modificat.
	 * @param id Id de l'usuari que volem editar
	 * @param model ??s el model que passem a la vista
	 * @param flash Variable que fem servir per enviar missatges a les vistes.
	 * @return Si l'atribut empresa ??s null redireccionem a home, en cas contrari redireccionem a listar si l'id no
	 * existeix a la base de dades o cridem a la vista crear si el trobem.
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping("/actualizar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Usuari usuari = null;
		funcion=MODIFICAR;
		
		empresa=empresaService.findById(1);
		// Si despr??s de cercar no tenim empresa llavors redireccionem a home
		if(empresa==null)return "redirect:/perfil";	

		// Si l'id ??s m??s gran que cero busquem l'usuari
		if (id > 0) {

			usuari = usuariService.findById(id);
			// Si no el trobem informem de l'error i redireccionem a listar
			if (usuari == null) {
				flash.addFlashAttribute("error", "El ID de l'usuari no existeix a la BBDD");
				return "redirect:/usuaris/listar";
			}
		// Si l'id no ??s m??s gran que cero cridem a listar	
		} else {

			flash.addFlashAttribute("error", "L'ID de l'usuari no pot ser 0");
			return "redirect:/usuaris/listar";
		}		
		
		titolBoto = "Enviar dades";	
		titol = "Modificar usuari";
		
		// Passem al model els atributs necessaris
		model.addAttribute("titol", titol);
		model.addAttribute("usuari", usuari);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);		

		// Cridem a la vista crear
		return "usuaris/crear";
	}
	
	/**
	 * F??nci?? que permet a un usuari canviar algunes dades del seu perfil
	 * @param model Model passat a la vista
	 * @return crida a la vista modificar_perfil si la empresa existeix, en cas contrari redirecciona a home
	 */
	
	@Secured("ROLE_USER")
	@GetMapping("/modificar_perfil")
	public String modificarPerfil(Model model) {
		
		funcion=MODIFICAR_PERFIL;
		empresa=empresaService.findById(1);
		// Si despr??s de cercar no tenim empresa llavors redireccionem a home
		if(empresa==null)return "redirect:/perfil";	
		
		// L'usuari correspon a l'usuari autenticat per?? el busquem per si tingu??s qualsevol canvi pendent.
		Usuari usuari=usuariService.findById(Usuari.USUARIAUTENTICAT.getId());	
		PasswordChange passwordChange=new PasswordChange();
		// Assignem a la variable passwordChage l'id de l'usuari autenticat que ser?? a qu?? canviarem la contrasenya
		passwordChange.setId(Usuari.USUARIAUTENTICAT.getId());
		titol = "Actualitzar perfil";
		titolBoto = "Enviar dades";		
		model.addAttribute("titol", titol);			
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);		
		model.addAttribute("usuari", usuari);
		model.addAttribute("canviContrasenya", passwordChange);
		
		return "usuaris/modificar_perfil";
	}
	
	/**
	 * Funci?? que crida a la vista cambiar_password per poder canviar la contrasenya
	 * @param model Model passat a la vista
	 * @return Crida a la vista cambiar_password si existeix l'empresa, en cas contrari redirecciona a home
	 */

	
	/**
	 * Funci?? que guarda la contrasenya nova de un usuari despr??s d'assegurar-se que sap l'antiga
	 * @param passwordChange Variable que cont?? l'antiga i la nova contrasenya introduida per l'usuari
	 * @param result Variable que cont?? els errors de la validaci??.
	 * @param model Model que passem a la vista
	 * @param flash Par??metre que utilitzarem per enviar missatges a la vista
	 * @return Si no hi ha cap error redirecciona a home, en cas contrari torna a cridar la vista modificar_perfil
	 */
	@PostMapping("/guardarContrasenya")
	public String guardarPassword(@Valid PasswordChange passwordChange, BindingResult result, Model model,
						RedirectAttributes flash) {
		// Afegim al model els atributs necessaris
		model.addAttribute("titol", titol);
		model.addAttribute("titolBoto", titolBoto);
		model.addAttribute("empresa", empresa);
	
		//Validem el password antic. Si no passa la validaci?? assignem l'error a result
		if(!comprobarPassword(passwordChange)) {
			
			result.rejectValue("oldPassword", "passwordChange.oldPasswordInvalid");
		}		
		
		// Si result cont?? errors enviem un missatge d'error i tornem a cridar la vista cambiar_password
		if (result.hasErrors()) {
			flash.addFlashAttribute("error", "No ha estat possible guardar les dades");		
			return "usuaris/modificar_perfil";		
		}
		
		// Si tot es correcte busquem l'usuari
		Usuari usuari=usuariService.findById(passwordChange.getId());
		// encriptem el nou password i l'asignem a l'usuari
		String passwordEncriptado=passwordEncoder.encode(passwordChange.getNewPassword());
		usuari.setPassword(passwordEncriptado);	
		// Guardem l'usuari
		Usuari user=usuariService.save(usuari);
		// Mirem si l'usuari s'ha guardat amb ??xit
		if(user.getId()!=null) {
			
			flash.addFlashAttribute("success", "Registre guardat amb  ??xit");
		}else {
			
			flash.addFlashAttribute("error", "No s'ha pogut guardar el registre");
		}
		
		// Redireccionem a home
		return "redirect:/perfil";
	}	
	
	@Secured("ROLE_USER")
	@GetMapping("/aceptarPrivacidad")
	public String aceptarPrivacidad() {
		
		Usuari usuari=usuariService.findById(Usuari.USUARIAUTENTICAT.getId());
		
		if(usuari!=null) {
			
			usuari.setPrivacidadFirmada(true);
			usuariService.save(usuari);
			Usuari.USUARIAUTENTICAT=usuari;
		}
		
		return "redirect:/perfil";
	}
	


	/**
	 * M??tode que comproba que el dni que s'est?? introduint no existeixi a la base de dades.
	 * @param usuario Usuari del qual mirem el dni.
	 * @return Retorna true si no existeix i false en cas contrari
	 */	
	private boolean comprobacionDni(Usuari usuario) {

		// Inicialitzem a true el resultat
		boolean result = true;
		// Busquem l'usuari mitjan??ant el dni
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
	 * M??tode que comproba si l'username d'usuario ja existeix a la base de dades
	 * @param usuario Usuari de qui volem comprobar l'username
	 * @return Retorna true si l'username no existeix i false en cas contrari.
	 */
	private boolean comprobacionUsername(Usuari usuario) {

		// Inicialitzem a true el resultat
		boolean result = true;
		// Busquem l'usuari mitjan??ant l'username
		Usuari usuariPerUsername = usuariService.findByUsername(usuario.getUsername());
		// Si el trobem
		if (usuariPerUsername != null) {
			
		
			// Si els dni coincideixen llavors no estem intentant repetir per tant retornem false
			if (!usuario.getDni().equalsIgnoreCase(usuariPerUsername.getDni())) {
				
				result = false;
			}

		}
		// Retornem result
		return result;
	}
	
	/**
	 * M??tode que comprova si el password contingut a l'atribut oldPassword de PasswordChange coincideix amb el password de l'usuari.
	 * @param passwordChange Instancia que cont?? l'id de l'usuari i el password introduit al formulari
	 * @return Retorna true si coincideix, en cas contrari false.
	 */
	private boolean comprobarPassword(PasswordChange passwordChange) {
		
		boolean result=false;	
		// Busquem l'usuari (Sabem que existeix per tant no fem comprobaci??).
		Usuari usuario=usuariService.findById(passwordChange.getId());
		// Si coincideix retornem true
		if(passwordEncoder.matches(passwordChange.getOldPassword(), usuario.getPassword())) {
			
			result=true;
		}
		
		// Si no coincideix retornem false		
		return result;
	}
	
	/**
	 * M??tode que retorna una Inst??ncia de sort que cont?? la direcci?? i el camp pel qual volem que ens ordeni les llistes
	 * @return Inst??ncia de Sort
	 */
	private Sort sortByIdAsc() {
		 
			// Creem un nou ordre al cual donem direcci?? ascendent i com a camp clau l'id
		 	Order orden=new Order(Direction.ASC, "id");
		 	// Creem una llista d'ordres i afegim l'ordre creat
		 	List<Order> lista=new ArrayList<>();
		 	lista.add(orden);
		 	// Retornem la inst??ncia de sort amb la llista d'ordres.
		 	return Sort.by(lista);
	}
	
	@ModelAttribute("usuariAutenticat")
	public Usuari getUsuariAuthenticat() {
		
		return Usuari.USUARIAUTENTICAT;
	}	

}
