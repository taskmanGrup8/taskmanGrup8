package com.grup8.taskman.app.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import com.grup8.taskman.app.domain.usuaris.FiltreUsuaris;
import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.services.usuari.IUsuariService;

/**
 * Classe que manega l'event success del login. L'anotem amb @Component per poder-la injectar.
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 */

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	@Autowired
	IUsuariService usuariService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		// Actualitzem la constant USUARIAUTENTICAT amb el nou login
		String username=authentication.getName();
		Usuari.USUARIAUTENTICAT=usuariService.findByUsername(username);
		FiltreUsuaris.filtreUsuari=new FiltreUsuaris();
		
		// Com que no tenim RedirectAttributes creem un manager de Maps de tipus flash amb sessió. 
		SessionFlashMapManager flashMapManager=new SessionFlashMapManager();		
		// Creem un nou FlashMap que exten de HashMap
		FlashMap flashMap=new FlashMap();
		// Afegim el missatge success amb el contingut que volem
		flashMap.put("success", "Benvingut " + authentication.getName());
		// Guardem el flashmap al manager.
		flashMapManager.saveOutputFlashMap(flashMap, request, response);		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	

}
