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

import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.services.usuari.IUsuariService;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	@Autowired
	IUsuariService usuariService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		
		String username=authentication.getName();
		Usuari.USUARIAUTENTICAT=usuariService.findByUsername(username);
		SessionFlashMapManager flashMapManager=new SessionFlashMapManager();		
		FlashMap flashMap=new FlashMap();
		flashMap.put("success", "Benvingut " + authentication.getName());
		flashMapManager.saveOutputFlashMap(flashMap, request, response);		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	

}
