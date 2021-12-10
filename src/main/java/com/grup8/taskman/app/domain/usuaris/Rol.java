package com.grup8.taskman.app.domain.usuaris;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe que mapejem als camps de la taula rols. Està implementada amb JPA. Servirá per controlar el rol de cada
 * usuari que determinarà quines accions pot realitzar a l'aplicació
 * @author Sergio Estebam Gutiérrez
 * @version 1.0.1
 *
 */

@Entity // Indiquem que volem mapejar la classe a la base de dades
@Table(name="rols") // Associem la classe amb la taula empreses de la base de dades
public class Rol implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// CONSTANTS
	
	public static final int ADMINISTRADOR=1;
	public static final int USUARI=2;
	public static final int SUPERADMINISTRADOR=3;
	public static final int TASKMAN=4;

	// ATRIBUTS
	
	
	// Attribut que s'autogenerarà i que marquem con id a la base de dades
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	// Indiquem que codigo no pot ser null a la base de dades, és únic, no es pot actualitzar i té un tamany màxim de 20 dígits.
	@Column(name="codigo", unique=true, length=15, nullable=false, updatable=false )
	private String codigo;
	
	// Indiquem que nombre no pot ser null a la base de dades, és únic, no es pot actualitzar i té un tamany màxim de 20 dígits.		
	@Column(name="nombre", unique=true, length=25, nullable=false, updatable=false )
	private String nombre;
	
	
	// CONSTRUCTOR
	
	public Rol() {
		
	}	
	
	// SETTERS I GETTERS
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	

}
