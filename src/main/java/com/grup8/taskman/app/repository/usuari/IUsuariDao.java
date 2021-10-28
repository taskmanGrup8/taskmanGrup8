package com.grup8.taskman.app.repository.usuari;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.grup8.taskman.app.domain.usuaris.Usuari;

public interface IUsuariDao extends PagingAndSortingRepository<Usuari, Long>{

	
	// ACTIVOS CON ROL
	
	@Query(value="SELECT * FROM usuaris u WHERE u.activo= :activo AND u.id_rol= :rol", nativeQuery=true)
	public Page<Usuari> findByActivoAndRol(boolean activo, Long rol, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.activo= :activo AND u.id_rol= :rol AND u.nombre LIKE CONCAT(:nom,'%')", nativeQuery=true)
	public Page<Usuari> findByActivoAndRolAndNombreStartsWith(boolean activo, Long rol, String nom, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.activo= :activo AND u.id_rol= :rol AND u.apellidos LIKE CONCAT(:cognoms,'%')", nativeQuery=true)
	public Page<Usuari> findByActivoAndRolAndApellidosStartsWith(boolean activo, Long rol, String cognoms, Pageable pageable);
		
	@Query(value="SELECT * FROM usuaris u WHERE u.activo= :activo AND u.id_rol= :rol AND u.dni LIKE CONCAT(:dni,'%')", nativeQuery=true)
	public Page<Usuari> findByActivoAndRolAndDniStartsWith(boolean activo, Long rol, String dni, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.activo= :activo AND u.id_rol= :rol AND u.nombre LIKE CONCAT(:nom,'%') AND u.apellidos LIKE CONCAT(:cognoms,'%')", nativeQuery=true)
	public Page<Usuari> findByActivoAndRolAndNombreStartsWithAndApellidosStartsWith(boolean activo, Long rol, String nom, String cognoms, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.activo= :activo AND u.id_rol= :rol AND u.nombre LIKE CONCAT(:nom,'%') AND u.dni LIKE CONCAT(:dni,'%')", nativeQuery=true)
	public Page<Usuari> findByActivoAndRolAndNombreStartsWithAndDniStartsWith(boolean activo, Long rol, String nom, String dni, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.activo= :activo AND u.id_rol= :rol AND u.apellidos LIKE CONCAT(:cognoms,'%') AND u.dni LIKE CONCAT(:dni,'%')", nativeQuery=true)
	public Page<Usuari> findByActivoAndRolAndApellidosStartsWithAndDniStartsWith(boolean activo, Long rol, String cognoms, String dni, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.activo= :activo AND u.id_rol= :rol AND u.nombre LIKE CONCAT(:nom,'%') AND u.apellidos LIKE CONCAT(:cognoms,'%') AND u.dni LIKE CONCAT(:dni,'%')", nativeQuery=true)
	public Page<Usuari> findByActivoAndRolAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(boolean activo, Long rol, String nom, String cognoms, String dni, Pageable pageable);
	
	
	// ACTIVOS SIN ROL
	
	public Page<Usuari> findByActivo(boolean activo, Pageable pageable);
	public Page<Usuari> findByActivoAndNombreStartsWith(boolean activo, String nom, Pageable pageable);
	public Page<Usuari> findByActivoAndApellidosStartsWith(boolean activo, String cognoms, Pageable pageable);
	public Page<Usuari> findByActivoAndDniStartsWith(boolean activo, String dni, Pageable pageable);
	public Page<Usuari> findByActivoAndNombreStartsWithAndApellidosStartsWith(boolean activo, String nom, String cognoms, Pageable pageable);
	public Page<Usuari> findByActivoAndNombreStartsWithAndDniStartsWith(boolean activo, String nom, String dni, Pageable pageable);
	public Page<Usuari> findByActivoAndApellidosStartsWithAndDniStartsWith(boolean activo, String cognoms, String dni, Pageable pageable);
	public Page<Usuari> findByActivoAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(boolean activo, String nom, String cognoms, String dni, Pageable pageable);
	
	
	
	// HISTÓRICOS CON ROL
	
	@Query(value="SELECT * FROM usuaris u WHERE u.id_rol= :rol", nativeQuery=true)
	public Page<Usuari> findByRol(Long rol, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.id_rol= :rol AND u.nombre LIKE CONCAT(:nom,'%')", nativeQuery=true)
	public Page<Usuari> findByRolAndNombreStartsWith(Long rol, String nom, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.id_rol= :rol AND u.apellidos LIKE CONCAT(:cognoms,'%')", nativeQuery=true)
	public Page<Usuari> findByRolAndApellidosStartsWith(Long rol, String cognoms, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.id_rol= :rol AND u.dni LIKE CONCAT(:dni,'%')", nativeQuery=true)
	public Page<Usuari> findByRolAndDniStartsWith(Long rol, String dni, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.id_rol= :rol AND u.nombre LIKE CONCAT(:nom,'%') AND u.apellidos LIKE CONCAT(:cognoms,'%')", nativeQuery=true)
	public Page<Usuari> findByRolAndNombreStartsWithAndApellidosStartsWith(Long rol, String nom, String cognoms, Pageable pageable);

	@Query(value="SELECT * FROM usuaris u WHERE u.id_rol= :rol AND u.nombre LIKE CONCAT(:nom,'%') AND u.dni LIKE CONCAT(:dni,'%')", nativeQuery=true)
	public Page<Usuari> findByRolAndNombreStartsWithAndDniStartsWith(Long rol, String nom, String dni, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.id_rol= :rol AND u.apellidos LIKE CONCAT(:cognoms,'%') AND u.dni LIKE CONCAT(:dni,'%')", nativeQuery=true)
	public Page<Usuari> findByRolAndApellidosStartsWithAndDniStartsWith(Long rol, String cognoms, String dni, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.id_rol= :rol AND u.nombre LIKE CONCAT(:nom,'%') AND u.apellidos LIKE CONCAT(:cognoms,'%') AND u.dni LIKE CONCAT(:dni,'%')", nativeQuery=true)
	public Page<Usuari> findByRolAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(Long rol, String nom, String cognoms, String dni, Pageable pageable);
	
	
	
	
	
	// HISTÓRICOS SIN ROL
	
	public Page<Usuari> findByNombreStartsWith(String nom, Pageable pageable);
	public Page<Usuari> findByApellidosStartsWith(String cognoms, Pageable pageable);	
	public Page<Usuari> findByDniStartsWith(String dni, Pageable pageable);
	public Page<Usuari> findByNombreStartsWithAndApellidosStartsWith(String nombre, String apellidos, Pageable pageable);
	public Page<Usuari> findByNombreStartsWithAndDniStartsWith(String nombre, String dni, Pageable pageable);
	public Page<Usuari> findByApellidosStartsWithAndDniStartsWith(String apellido, String dni, Pageable pageable);
	public Page<Usuari> findByNombreStartsWithAndApellidosStartsWithAndDniStartsWith(String nombre, String apellidos, String dni, Pageable pageable);
	
	
	// OTROS	

	public Optional<Usuari> findByDni(String dni);
	

}
