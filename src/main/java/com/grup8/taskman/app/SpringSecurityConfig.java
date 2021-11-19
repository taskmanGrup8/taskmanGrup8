package com.grup8.taskman.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.grup8.taskman.app.security.LoginSuccessHandler;
import com.grup8.taskman.app.services.usuari.UsuariService;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UsuariService usuariService;
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
		
		builder.userDetailsService(usuariService).passwordEncoder(passwordEncoder);
	}
		
		

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/css/**", "/js/**", "/img/**", "/plugins/**").permitAll()
	/*	.antMatchers("/departaments/listar").hasAnyRole("ADMIN")
		.antMatchers("/departaments/crear").hasAnyRole("ADMIN")
		.antMatchers("/departaments/eliminar/**").hasAnyRole("ADMIN")
		.antMatchers("/departaments/actualizar/**").hasAnyRole("ADMIN")
		.antMatchers("/departaments/ver/**").hasAnyRole("ADMIN")
		.antMatchers("/usuaris/listar").hasAnyRole("ADMIN")
		.antMatchers("/usuaris/crear").hasAnyRole("ADMIN")
		.antMatchers("/usuaris/eliminar/**").hasAnyRole("ADMIN")
		.antMatchers("/usuaris/actualizar/**").hasAnyRole("ADMIN")
		.antMatchers("/usuaris/ver/**").hasAnyRole("ADMIN")
		.antMatchers("/usuaris/filtrar").hasAnyRole("ADMIN")
		.antMatchers("/empreses/crear").hasAnyRole("SUPER")		
		.antMatchers("/empreses/actualizar/**").hasAnyRole("SUPER")
		.antMatchers("/taskman/super/perfil").hasAnyRole("USER")*/
		.antMatchers("/uploads/**").hasAnyRole("USER")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.successHandler(successHandler)
		.loginPage("/login")
		.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
		
	}
	
	
	

}
