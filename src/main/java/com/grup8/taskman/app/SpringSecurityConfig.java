package com.grup8.taskman.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.grup8.taskman.app.security.LoginSuccessHandler;
import com.grup8.taskman.app.services.usuari.UsuariService;

/**
 * Classe de configuració dedicada a la seguretat de l'aplicació.
 * L'anotem amb Configuration perquè sigui de configuració i l'anotem amb @EnableGlobalMethodSecurity(securedEnabled=true)
 * per poder anotar els diferents mètodes dels diferents controladors amb @Secured
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 */

@EnableGlobalMethodSecurity(securedEnabled=true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UsuariService usuariService;
	
	/**
	 * Funció que injectem i que permet la construcció d'autenticació 
	 * @param builder És el constructor d'autenticacións
	 * @throws Exception Mesura de seguretat per si no pogués realitzar l'operació
	 */	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
		
		// Demanem al constructor d'autenticacions que faci servi el servei que implementa userDetailService perquè
		// faci servir la base dades i també passem passwordEncoder perquè utilitzi BCrypt.
		
		builder.userDetailsService(usuariService).passwordEncoder(passwordEncoder);
	}
		
		
	/**
	 * Funció que configura les autoritzacions de l'aplicació. Fem override perquè si no ho fa tot privat.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/css/**", "/js/**", "/img/**", "/plugins/**", "/index", "/recover/**").permitAll()	
		.antMatchers("/uploads/**").hasAnyRole("USER")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.successHandler(successHandler)
		.loginPage("/login")		
		.permitAll()
		.and()
		.logout()
		.permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
		
	}	

}
