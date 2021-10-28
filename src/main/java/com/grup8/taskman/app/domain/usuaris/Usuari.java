package com.grup8.taskman.app.domain.usuaris;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import com.grup8.taskman.app.domain.departaments.Departament;


@Entity
@Table(name="usuaris")
public class Usuari implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@NotBlank
	@Column(name="nombre", nullable=false)
	private String nombre;
	
	@NotBlank
	@Column(name="apellidos", nullable=false)
	private String apellidos;
	
	@Pattern(regexp="[0-9]{8}[A-Z]{1}")
	@Column(name="dni", unique=true, length=9, nullable=false)
	private String dni;
	
	@Pattern(regexp="[0-9]{9}")	
	@Column(name="telefono", length=9)
	private String telefono;
			
	@Column(name="password", length=4, nullable=false)
	private String password;
			
	@Column(name="activo")
	private boolean activo;
	
	@Column(name="privacidad_firmada")
	private boolean privacidadFirmada;
	
	@NotBlank
	@Email
	@Column(name="email")
	private String email;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_rol")
	private Rol rol;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade= {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name="usuari_departament",
			joinColumns=@JoinColumn(name="id_usuario"),
			inverseJoinColumns=@JoinColumn(name="id_departament")
			)
	private List<Departament> departaments;

	public Usuari() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}	
	
	public void addDepartament(Departament departament) {
		
		departaments.add(departament);
	}
	
	public void addDepartaments(List<Departament> departaments) {
		
		for(Departament d: departaments) {
			
			addDepartament(d);
		}
	}
	
	public void removeDepartament(Departament departament) {
		
		departaments.remove(departament);
	}	

	public boolean isPrivacidadFirmada() {
		return privacidadFirmada;
	}

	public void setPrivacidadFirmada(boolean privacidadFirmada) {
		this.privacidadFirmada = privacidadFirmada;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Departament> getDepartaments() {
		return departaments;
	}

	public void setDepartaments(List<Departament> departaments) {
		this.departaments = departaments;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(this==obj)return true;
		if(obj==null)return false;
		if(getClass() !=obj.getClass())return false;
		Usuari usuari=(Usuari) obj;
		if(dni==null) {
			
			if(usuari.getDni()!=null)return false;
		}else if(!dni.equals(usuari.getDni())) {
			
			return false;
		}
		
		return true;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		return result;

	}

}
