package com.grup8.taskman.app.domain.empreses;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="empreses")
public class Empresa implements Serializable {
	
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	@Column(name="nombre", nullable=false)
	private String nombre;	
			
	@NotBlank
	@Pattern(regexp="([0-9]{8}[A-Z]{1})|([A-Z]{1}[0-9]{8})")	
	@Column(name="cif", unique=true, nullable=false)
	private String cif;
	
	@NotBlank
	@Column(name="direccion", nullable=false)
	private String direccion;
	
	@Column(name="logo")
	private String logo="";
	
	
	public Empresa() {
		super();
		
	}

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
	
	
	
	

}
