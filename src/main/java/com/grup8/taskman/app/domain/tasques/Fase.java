package com.grup8.taskman.app.domain.tasques;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.grup8.taskman.app.domain.departaments.Departament;

/**
 * Classe que representa una fase d'una tasca a realitzar. Cada fase pertany a un departament.
 * Està relacionada amb la taula fases de la base de dades
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */

@Entity
@Table(name="fases")
public class Fase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// ATRIBUTS
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	// Indiquem que codigo no pot ser null i que és únic a la base de dades i té tamany 3. també indiquem que la validació no pot ser ni null 
	// ni espais en blanc.
	@NotBlank
	@Column(name="codigo", length=3, unique=true, nullable=false)
	private String codigo;
	
	// Indiquem que nombre no pot ser null i és únic a la base de dades i té tamany 40. També indiquem que la validació no pot ser ni null 
	// ni espais en blanc.
	@NotBlank
	@Column(name="nombre", length=40, unique=true, nullable=false)
	private String nombre;	
	
	// Indiquem que descripció no pot ser null a la base de dades. També indiquem que la validació no pot ser ni null
	// ni espais en blanc.
	@NotBlank
	@Column(name="descripcion", nullable=false)
	private String descripcion;		
	
	// Associem a la base de dades la taula fases amb departaments. També indiquem que la validació no pot ser null
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_departament")
	private Departament departament;

	// CONSTRUCTOR
	
	public Fase() {
	
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	

	public Departament getDepartament() {
		return departament;
	}

	public void setDepartament(Departament departament) {
		this.departament = departament;
	}
	
	// MÈTODES
	
	// Abans de de guardar a la base de dades convertim el contingut de codigo a majúscules.
	@PrePersist
	public void prePersist() {
		
		this.codigo=codigo.toUpperCase();
	}
	
	
	/**
	 * Fem override del mètode equals perque volem que les comparacions siguin personalitzades pel nom de la fase
	 * que és únic a la base de dades.
	 */
	@Override
	public boolean equals(Object obj) {
		
		// Si obj és el mateix objecte que this retornem true
		if(this==obj)return true;
		// si obj és null retornem false
		if(obj==null)return false;
		
		// Si obj no es de la mateixa classe Departament retornem false
		if(getClass() !=obj.getClass())return false;
		
		// Fem cast d'obj
		Fase fase=(Fase) obj;
		
		// Si l'atribut nombre és null i l'atribut nombre d'obj no llavors retornem false
		if(nombre==null) {
			
			if(fase.getNombre()!=null)return false;
			
		// En cas contrari si nombre no és igual a l'atribut nombre d'obj retornem false 	
		}else if(!nombre.equals(fase.getNombre())) {
			
			return false;
		}
		
		// Retornem true perquè els dos noms son iguals
		return true;
	}
	
	/**
	 * Fem override del métode hashCode perquè sigui personalitzat al camp nombre
	 */
	@Override
	public int hashCode() {
		final int prime=31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
		
	}	

}
