package com.grup8.taskman.app.repository.usuari;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.grup8.taskman.app.domain.usuaris.Usuari;

/**
 * Intefície que extend de PagingAndSortingRepository. PagingAndSortingRepository ja té implementades diverses funcionalitats,
 * entre elles l'opció de retorna Page en hores de List i manegar Pageable.
 * A aquesta inteficie afegim altres funcionalitat que volem que tingui
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 */

public interface IUsuariDao extends PagingAndSortingRepository<Usuari, Long>{

	
	// ACTIVOS CON ROL
	
	// Les consultes amb rol tenen que anar amb @Query perquè fan una consulta sobre la clau forània id_rol i 
	// Usuario conté una Instancia completa de Rol sencer per tant és una consulta complexa.
	// Les anotem amb @Query i a value posem la consulta. Finalment posem nativeQuery com true.
	
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
	
	
	// ACTIVOS SIN ROL PETICION DE TASKMAN
	
	public Page<Usuari> findByActivo(boolean activo, Pageable pageable);
	public Page<Usuari> findByActivoAndNombreStartsWith(boolean activo, String nom, Pageable pageable);
	public Page<Usuari> findByActivoAndApellidosStartsWith(boolean activo, String cognoms, Pageable pageable);
	public Page<Usuari> findByActivoAndDniStartsWith(boolean activo, String dni, Pageable pageable);
	public Page<Usuari> findByActivoAndNombreStartsWithAndApellidosStartsWith(boolean activo, String nom, String cognoms, Pageable pageable);
	public Page<Usuari> findByActivoAndNombreStartsWithAndDniStartsWith(boolean activo, String nom, String dni, Pageable pageable);
	public Page<Usuari> findByActivoAndApellidosStartsWithAndDniStartsWith(boolean activo, String cognoms, String dni, Pageable pageable);
	public Page<Usuari> findByActivoAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(boolean activo, String nom, String cognoms, String dni, Pageable pageable);
	
	
	// ACTIVOS SIN ROL PETICION DE ADMINISTRADORES
	
	
	@Query(value="SELECT * FROM usuaris u WHERE u.activo= :activo AND u.id_rol<3", nativeQuery=true)
	public Page<Usuari> findByActivoAdmin(boolean activo, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.activo= :activo AND u.id_rol<3 AND u.nombre LIKE CONCAT(:nom,'%')", nativeQuery=true)
	public Page<Usuari> findByActivoAndNombreStartsWithAdmin(boolean activo, String nom, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.activo= :activo AND u.id_rol<3 AND u.apellidos LIKE CONCAT(:cognoms,'%')", nativeQuery=true)
	public Page<Usuari> findByActivoAndApellidosStartsWithAdmin(boolean activo, String cognoms, Pageable pageable);
		
	@Query(value="SELECT * FROM usuaris u WHERE u.activo= :activo AND u.id_rol<3 AND u.dni LIKE CONCAT(:dni,'%')", nativeQuery=true)
	public Page<Usuari> findByActivoAndDniStartsWithAdmin(boolean activo, String dni, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.activo= :activo AND u.id_rol<3 AND u.nombre LIKE CONCAT(:nom,'%') AND u.apellidos LIKE CONCAT(:cognoms,'%')", nativeQuery=true)
	public Page<Usuari> findByActivoAndNombreStartsWithAndApellidosStartsWithAdmin(boolean activo, String nom, String cognoms, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.activo= :activo AND u.id_rol<3 AND u.nombre LIKE CONCAT(:nom,'%') AND u.dni LIKE CONCAT(:dni,'%')", nativeQuery=true)
	public Page<Usuari> findByActivoAndNombreStartsWithAndDniStartsWithAdmin(boolean activo, String nom, String dni, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.activo= :activo AND u.id_rol<3 AND u.apellidos LIKE CONCAT(:cognoms,'%') AND u.dni LIKE CONCAT(:dni,'%')", nativeQuery=true)
	public Page<Usuari> findByActivoAndApellidosStartsWithAndDniStartsWithAdmin(boolean activo, String cognoms, String dni, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.activo= :activo AND u.id_rol<3 AND u.nombre LIKE CONCAT(:nom,'%') AND u.apellidos LIKE CONCAT(:cognoms,'%') AND u.dni LIKE CONCAT(:dni,'%')", nativeQuery=true)
	public Page<Usuari> findByActivoAndNombreStartsWithAndApellidosStartsWithAndDniStartsWithAdmin(boolean activo, String nom, String cognoms, String dni, Pageable pageable);
	
	
	
	// HISTÓRICOS CON ROL
	
	// Passa exactament igual amb el camp id_rol que el que hem esmentat abans.
	
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
	
	
	
	
	
	// HISTÓRICOS SIN ROL PETICIÓN DE TASKMAN
	
	public Page<Usuari> findByNombreStartsWith(String nom, Pageable pageable);
	public Page<Usuari> findByApellidosStartsWith(String cognoms, Pageable pageable);	
	public Page<Usuari> findByDniStartsWith(String dni, Pageable pageable);
	public Page<Usuari> findByNombreStartsWithAndApellidosStartsWith(String nombre, String apellidos, Pageable pageable);
	public Page<Usuari> findByNombreStartsWithAndDniStartsWith(String nombre, String dni, Pageable pageable);
	public Page<Usuari> findByApellidosStartsWithAndDniStartsWith(String apellido, String dni, Pageable pageable);
	public Page<Usuari> findByNombreStartsWithAndApellidosStartsWithAndDniStartsWith(String nombre, String apellidos, String dni, Pageable pageable);
	
	
	// HISTÓRICOS SIN ROL PETICIÓN DE ADMINISTRADORES
	
	@Query(value="SELECT * FROM usuaris u WHERE u.id_rol<3", nativeQuery=true)
	public Page<Usuari> findAllAdmin(Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE NOT u.id_rol<3 AND u.nombre LIKE CONCAT(:nom,'%')", nativeQuery=true)
	public Page<Usuari> findByNombreStartsWithAdmin(String nom, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE NOT u.id_rol<3 AND u.apellidos LIKE CONCAT(:cognoms,'%')", nativeQuery=true)
	public Page<Usuari> findByApellidosStartsWithAdmin(String cognoms, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE NOT u.id_rol<3 AND u.dni LIKE CONCAT(:dni,'%')", nativeQuery=true)
	public Page<Usuari> findByDniStartsWithAdmin(String dni, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE NOT u.id_rol<3 AND u.nombre LIKE CONCAT(:nom,'%') AND u.apellidos LIKE CONCAT(:cognoms,'%')", nativeQuery=true)
	public Page<Usuari> findByNombreStartsWithAndApellidosStartsWithAdmin(String nom, String cognoms, Pageable pageable);

	@Query(value="SELECT * FROM usuaris u WHERE NOT u.id_rol<3 AND u.nombre LIKE CONCAT(:nom,'%') AND u.dni LIKE CONCAT(:dni,'%')", nativeQuery=true)
	public Page<Usuari> findByNombreStartsWithAndDniStartsWithAdmin(String nom, String dni, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.id_rol<3 AND u.apellidos LIKE CONCAT(:cognoms,'%') AND u.dni LIKE CONCAT(:dni,'%')", nativeQuery=true)
	public Page<Usuari> findByApellidosStartsWithAndDniStartsWithAdmin(String cognoms, String dni, Pageable pageable);
	
	@Query(value="SELECT * FROM usuaris u WHERE u.id_rol<3 AND u.nombre LIKE CONCAT(:nom,'%') AND u.apellidos LIKE CONCAT(:cognoms,'%') AND u.dni LIKE CONCAT(:dni,'%')", nativeQuery=true)
	public Page<Usuari> findByNombreStartsWithAndApellidosStartsWithAndDniStartsWithAdmin(String nom, String cognoms, String dni, Pageable pageable);
	
	
	
	// OTROS	

	public Optional<Usuari> findByDni(String dni);
	
	// LOGIN
	
	public Optional<Usuari> findByUsername(String username);
	
	// NOTIFICACIÓ ACTUAL
	
	@Query(value="SELECT * FROM usuaris u WHERE u.id_notificacion= :notificacion", nativeQuery=true)
	public Optional<Usuari> findByNotificacionActual(Long notificacion);
	
	
	

}
