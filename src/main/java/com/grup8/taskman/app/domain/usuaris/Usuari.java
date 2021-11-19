package com.grup8.taskman.app.domain.usuaris;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import com.grup8.taskman.app.domain.departaments.Departament;

/**
 * Classe que mapejem als camps de la taula usuaris i que anotarem amb les validacions que han de tenir als 
 * formularis. Està implementada amb JPA i anotada amb javax.Validation
 * @author Sergio Estebam Gutiérrez
 * @version 1.0.0
 *
 */

@Entity // Indiquem que volem mapejar la classe a la base de dades
@Table(name="usuaris") // Associem la classe amb la taula usuaris de la base de dades
public class Usuari implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Usuari USUARIAUTENTICAT=null;
	
	// ATRIBUTS
	
	
	// Attribut que s'autogenerarà i que marquem con id a la base de dades
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	
	// Indiquem que nombre no pot ser null a la base de dades. també indiquem que la validació no pot ser ni null 
	// ni espais en blanc.
	@NotBlank
	@Column(name="nombre", nullable=false)
	private String nombre;
	
	// Indiquem que apellidos no pot ser null a la base de dades. també indiquem que la validació no pot ser ni null 
	// ni espais en blanc.
	@NotBlank
	@Column(name="apellidos", nullable=false)
	private String apellidos;
	
	// Indiquem que el camp dni dels formularis ha de tenir el patró indicat. No pot ser ni null ni espais en blanc.
	// A la base de dades serà unique i no podrà ser null i tindrà un tamany de 9 digits.
	@Pattern(regexp="[0-9]{8}[A-Z]{1}")
	@Column(name="dni", unique=true, length=9, nullable=false)
	private String dni;
	
	// Indiquem que telefono no pot ser null a la base de dades i té 9 dígits. També indiquem que la validació no pot ser ni null
	// ni espais en blanc i a més ha de tenir 9 caracters que han de ser números
	@Pattern(regexp="[0-9]{9}")	
	@Column(name="telefono", length=9)
	private String telefono;
	
	// Indiquem que telefono no pot ser null a la base de dades i té 4 dígits.
	@Column(name="password", length=60, nullable=false)
	private String password;
	
	@Column(name="username", unique=true, length=30, nullable=false)
	private String username;
	
	// Camp per determinar si un usuari encara està en actiu a l'empresa.
	@Column(name="activo")
	private boolean activo;
	
	// Aquest camp servirà si l'usuari ha signat la privacitat, sempre que no estigui signada, en iniciar sessió li saltarà
	@Column(name="privacidad_firmada")
	private boolean privacidadFirmada;
	
	// Indiquem que email no pot ser null a la base de dades. També indiquem que la validació no pot ser ni null
	// ni espais en blanc i a més ha de tenir format de correu electrònic.
	@NotBlank
	@Email
	@Column(name="email", nullable=false)
	private String email;
	
	// Indiquem la relació que té usuaris amb rol. Com que nomès tindrà un rol posem fetch EAGER i l'unim a la comumna id de rol
	// No pot ser null perquè volem que tots els usuaris tinguin rol.
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_rol")
	private Rol rol;	
	
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private List<Permiso> permisos;
	
	/* Indiquem la relació que té usuaris amb departaments. En aquest cas un usuari pot tenir molts departaments i un departament
	* pot tenir molts usuaris per tant es many to many. Aquesta part la fem EAGER perque un usuari no tindrà molts departaments.
	* No pot ser cascade all perque no volem que quan s'esborri un departament s'esborri també l'usuari.
	* Unim les tables departaments i usuaris mitjançant una nova taula que es dirà usuari_departament i que contindrà
	* l'id de l'usuari i l'id del departament.
	*/
	
	@ManyToMany(fetch = FetchType.EAGER, cascade= {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name="usuari_departament",
			joinColumns=@JoinColumn(name="id_usuario"),
			inverseJoinColumns=@JoinColumn(name="id_departament")
			)
	private List<Departament> departaments;

	// CONSTRUCTOR
	
	public Usuari() {
		
		permisos=new ArrayList<Permiso>();
	}
	
	// SETTERS I GETTERS

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

	public List<Permiso> getPermisos() {
		return permisos;
	}

	public void setPermiso(List<Permiso> permisos) {
		this.permisos = permisos;
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
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	// Modifiquem equals perquè ho faci amb el dni que és unic per cada usuari
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

	// Modifiquem hashcode perquè ho faci amb el dni que és unic per cada usuari
	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		return result;

	}
	
	public void eliminarPermisos() {
		
		permisos.clear();
	}
	
	public void assignarPermissos() {
		
		Permiso permisUsuari=new Permiso();
		permisUsuari.setAuthority("ROLE_USER");
		Permiso permisAdministrador=new Permiso();
		permisUsuari.setAuthority("ROLE_ADMIN");
		Permiso permisSuperAdministrador=new Permiso();
		permisUsuari.setAuthority("ROLE_SUPER");
		
		switch(Math.toIntExact(rol.getId())) {
		
		case Rol.ADMINISTRADOR:
			permisos.add(permisUsuari);
			permisos.add(permisAdministrador);
			break;
		case Rol.USUARI:
			permisos.add(permisUsuari);
			break;
		case Rol.SUPERADMINISTRADOR:
			permisos.add(permisUsuari);
			permisos.add(permisAdministrador);
			permisos.add(permisSuperAdministrador);
			break;
		default:
			permisos.add(permisUsuari);
			break;
		
		
		
		}
	}

}
