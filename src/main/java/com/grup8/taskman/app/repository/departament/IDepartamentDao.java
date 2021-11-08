package com.grup8.taskman.app.repository.departament;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.grup8.taskman.app.domain.departaments.Departament;

/**
 * Intefície que extend de CrudRepository. CrudRepository ja té implementades diverses funcionalitats.
 * A aquesta inteficie afegim altres funcionalitat que volem que tingui
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 */
public interface IDepartamentDao extends CrudRepository<Departament, Long> {
	
	/**
	 * Mètode que busca per codi. Spring enten findBy seguit pel nom del camp com una busqueda simple ja implementada.
	 * @param codigo Codi que volem buscar
	 * @return Retorna un optional amb el resultat de la cerca.
	 */
	public Optional<Departament> findByCodigo(String codigo);
	
	/**
	 * Mètode que busca a la base de dades el registre amb el nom passat per paràmetre
	 * @param nombre Nom que volem buscar.
	 * @return Retorna un optional amb el resultat de la cerca.
	 */
	public Optional<Departament> findByNombre(String nombre);
	
	/**
	 * Mètode que busca a la base de dades una llista dels elements de la taula codigo que comencin pel paràmetre indicat.
	 * StartsWith es reconegut com un LIKE.
	 * @param codigo String que volem buscar a la base de dades
	 * @return Retorna la llista d'elements que comencen per l'String passat per paràmetre.
	 */
	public List<Departament> findByCodigoStartsWith(String codigo);
	
	/**
	 * Mètode que busca a la base de dades una llista dels elements de la taula nombre que comencin pel paràmetre indicat.
	 * @param nombre String que volem buscar a la base de dades
	 * @return Retorna la llista d'elements que comencen per l'String passat per paràmetre.
	 */
	public List<Departament> findByNombreStartsWith(String nombre);
	

}
