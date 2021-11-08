package com.grup8.taskman.app;


import java.nio.file.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe de configuració que enregistra la carpeta uploads que farem servir
 * per guardar les imatges. 
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */

@Configuration
public class MvcConfig implements WebMvcConfigurer{

	/**
	 * Funció que afegeix un recurs al registre de recursos de la aplicació.
	 * En aquest cas afegeix uploads que és una carpeta que agafarà el path absolut
	 * del projecte i s'afegirà a l'alçada de src. Aixó evita que hagi de posar
	 * una ubicació dona't que no conec el sistema operatiu on treballarà el programa.
	 * 
	 * Més endavant, si donès temps es crearà una èina de configuració dins de
	 * l'aplicació perquè sigui el superusuari qui decideixi a quina carpeta vol
	 * guardar les imatges.
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		String resourcePath=Paths.get("uploads").toAbsolutePath().toUri().toString();
		registry.addResourceHandler("/uploads/**").addResourceLocations(resourcePath);
	}

	
}
