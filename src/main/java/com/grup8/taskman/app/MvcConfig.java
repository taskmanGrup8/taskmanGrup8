package com.grup8.taskman.app;


import java.nio.file.Paths;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe de configuració que enregistra diferents aspectes de l'aplicació
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.1
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
	
	/**
	 * Enregistrem un ViewController amb la Url "/error_403" i amb la vista "errores/error_403", això 
	 * ho faig per no haver de crear un controlador per aquesta url ja que la seva funció només és cridar
	 * a la vista.
	 */
	
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/error_403").setViewName("error/error_403");
		
		
	}
	
	/**
	 * Funció que genera un nou Bean del tipus BCryptPasswordEncoder.
	 * @return Retorna una nova instància de BCryptPasswordEncoder.
	 */
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder(12);
	}
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		
		JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("ester210403@gmail.com");
		mailSender.setPassword("Seres210403");
		
		Properties props= mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");
		
		return mailSender;
		
		
	}

	
}