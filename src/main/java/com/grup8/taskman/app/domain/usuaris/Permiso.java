package com.grup8.taskman.app.domain.usuaris;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
/**
 * Classe que equival a un permís d'usuari a la base de dades.
 * Está mapejada a la taula authorities. Afegim una constraint on indiquem que la dupla "user_id", "authority"
 * tenen que ser úniques. 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 */

@Entity
@Table(name="authorities", uniqueConstraints= {@UniqueConstraint(columnNames= {"user_id", "authority"})})
public class Permiso implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// ATRIBUTS
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="authority")
	private String authority;
	
	// CONSTRUCTOR

	public Permiso() {
		
	}		
	
	// SETTERS I GETTERS

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}	

}
