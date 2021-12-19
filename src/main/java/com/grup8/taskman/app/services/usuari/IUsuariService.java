package com.grup8.taskman.app.services.usuari;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.grup8.taskman.app.domain.usuaris.Usuari;

public interface IUsuariService {

	// CONSULTES SIMPLES
	
	public Usuari save(Usuari usuari);
	public Usuari findByDni(String dni);
	public Usuari findById(Long id);
	public Usuari findByUsername(String username);
	public void deleteAll();
	public Usuari findByNotificacionActual(Long notificacion);
	
	
	// FILTRES
	
	// Filtres d'usuaris actius on el rol importa
	
	public Page<Usuari> findByActivoAndRol(boolean activo, Long rol, Pageable pageable);
	public Page<Usuari> findByActivoAndRolAndNombreStartsWith(boolean activo, Long rol, String nom, Pageable pageable);
	public Page<Usuari> findByActivoAndRolAndApellidosStartsWith(boolean activo, Long rol, String cognoms,
			Pageable pageable);
	public Page<Usuari> findByActivoAndRolAndDniStartsWith(boolean activo, Long rol, String dni, Pageable pageable);
	public Page<Usuari> findByActivoAndRolAndNombreStartsWithAndApellidosStartsWith(boolean activo, Long rol,
			String nom, String cognoms, Pageable pageable);
	public Page<Usuari> findByActivoAndRolAndApellidosStartsWithAndDniStartsWith(boolean activo, Long rol,
			String cognoms, String dni, Pageable pageable);
	public Page<Usuari> findByActivoAndRolAndNombreStartsWithAndDniStartsWith(boolean activo, Long rol, String nom,
			String dni, Pageable pageable);
	public Page<Usuari> findByActivoAndRolAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(boolean activo,
			Long rol, String nom, String cognoms, String dni, Pageable pageable);
	
	// Filtres d'usuaris actius on el rol no importa
	
	public Page<Usuari> findByActivo(boolean activo, Pageable pageable);
	public Page<Usuari> findByActivoAndNombreStartsWith(boolean activo, String nom, Pageable pageable);
	public Page<Usuari> findByActivoAndApellidosStartsWith(boolean activo, String cognoms, Pageable pageable);	
	public Page<Usuari> findByActivoAndDniStartsWith(boolean activo, String dni, Pageable pageable);
	public Page<Usuari> findByActivoAndNombreStartsWithAndApellidosStartsWith(boolean activo, String nom,
			String cognoms, Pageable pageable);
	public Page<Usuari> findByActivoAndApellidosStartsWithAndDniStartsWith(boolean activo, String cognoms, String dni,
			Pageable pageable);
	public Page<Usuari> findByActivoAndNombreStartsWithAndDniStartsWith(boolean activo, String nom, String dni,
			Pageable pageable);
	public Page<Usuari> findByActivoAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(boolean activo,
			String nom, String cognoms, String dni, Pageable pageable);
	
	
	// Filtres d'usuaris actius i no actius on el rol importa
	
	public Page<Usuari> findByRol(Long rol, Pageable pageable);
	public Page<Usuari> findByRolAndNombreStartsWith(Long rol, String nom, Pageable pageable);	
	public Page<Usuari> findByRolAndApellidosStartsWith(Long rol, String cognoms, Pageable pageable);
	public Page<Usuari> findByRolAndDniStartsWith(Long rol, String dni, Pageable pageable);	
	public Page<Usuari> findByRolAndNombreStartsWithAndApellidosStartsWith(Long rol, String nom, String cognoms, Pageable pageable);
	public Page<Usuari> findByRolAndNombreStartsWithAndDniStartsWith(Long rol, String nom, String dni, Pageable pageable);
	public Page<Usuari> findByRolAndApellidosStartsWithAndDniStartsWith(Long rol, String cognoms, String dni, Pageable pageable);
	public Page<Usuari> findByRolAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(Long rol, String nom, String cognoms, String dni, Pageable pageable);
	
	
	// Filtres d'usuaris actius i no actius on el rol no importa
	
	public Page<Usuari> findAll(Pageable pageable);
	public Page<Usuari> findByNombreStartsWithAndApellidosStartsWith(String nombre, String apellidos, Pageable pageable);
	public Page<Usuari> findByNombreStartsWith(String nom, Pageable pageable);
	public Page<Usuari> findByApellidosStartsWith(String cognoms, Pageable pageable);
	public Page<Usuari> findByDniStartsWith(String dni, Pageable pageable);	
	public Page<Usuari> findByNombreStartsWithAndDniStartsWith(String nombre, String dni, Pageable pageable);
	public Page<Usuari> findByApellidosStartsWithAndDniStartsWith(String apellido, String dni, Pageable pageable);
	public Page<Usuari> findByNombreStartsWithAndApellidosStartsWithAndDniStartsWith(String nombre, String apellidos, String dni, Pageable pageable);
	

	
	

}
