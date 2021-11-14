package com.grup8.taskman.app.domain.empreses;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Classe que mapejem als camps de la taula empreses i que anotarem amb les validacions que han de tenir als 
 * formularis. Està implementada amb JPA i anotada amb javax.Validation
 * @author Sergio Estebam Gutiérrez
 * @version 1.0.0
 *
 */

@Entity // Indiquem que volem mapejar la classe a la base de dades
@Table(name="empreses") // Associem la classe amb la taula empreses de la base de dades
public class Empresa implements Serializable {
	
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// ATRIBUTS
	
	
	// Attribut que s'autogenerarà i que marquem con id a la base de dades
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	// Indiquem que nombre no pot ser null a la base de dades. també indiquem que la validació no pot ser ni null 
	// ni espais en blanc.
	@NotBlank
	@Column(name="nombre", nullable=false)
	private String nombre;	
	
	// Indiquem que el camp cif dels formularis ha de tenir el patró indicat. No pot ser ni null ni espais en blanc.
	// A la base de dades serà unique i no podrà ser null	
	@Pattern(regexp="([0-9]{8}[A-Z]{1})|([A-Z]{1}[0-9]{8})")	
	@Column(name="cif", unique=true, length=9, nullable=false)
	private String cif;
	
	// Indiquem que direccion no pot ser null a la base de dades. també indiquem que la validació no pot ser ni null 
	// ni espais en blanc.
	@NotBlank
	@Column(name="direccion", nullable=false)
	private String direccion;
	
	// Indiquem que localidad no pot ser null a la base de dades. també indiquem que la validació no pot ser ni null 
	// ni espais en blanc.
	@NotBlank
	@Column(name="localidad", nullable=false)
	private String localidad;
	
	// Indiquem que provincia no pot ser null a la base de dades. també indiquem que la validació no pot ser ni null 
	// ni espais en blanc.
	@NotBlank
	@Column(name="provincia", nullable=false)
	private String provincia;
	
	// Indiquem que direccion no pot ser null a la base de dades i té 5 dígits. també indiquem que la validació no pot ser ni null 
	// ni espais en blanc i a més ha de tenir 5 caracters que han de ser números.
		
	@Pattern(regexp="([0-9]{5})")
	@Column(name="cpostal", length=5, nullable=false)
	private String cpostal;
	
	// Indiquem que telefono no pot ser null a la base de dades i té 9 dígits. També indiquem que la validació no pot ser ni null
	// ni espais en blanc i a més ha de tenir 9 caracters que han de ser números
	
	@Pattern(regexp="[0-9]{9}")	
	@Column(name="telefono", length=9, nullable=false)
	private String telefono;
	
	// Indiquem que email no pot ser null a la base de dades. També indiquem que la validació no pot ser ni null
	// ni espais en blanc i a més ha de tenir format de correu electrònic.
	@NotBlank
	@Email
	@Column(name="email", nullable=false)
	private String email;
	
	// Camp on guardarem el nom de l'arxiu del logo
	@Column(name="logo")
	private String logo="";
	
	
	// CONSTRUCTOR
	
	
	public Empresa() {
				
	}
	
	// SETTERS I GETTERS

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpostal() {
		return cpostal;
	}

	public void setCpostal(String cpostal) {
		this.cpostal = cpostal;
	}

}
