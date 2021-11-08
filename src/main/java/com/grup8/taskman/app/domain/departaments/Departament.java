package com.grup8.taskman.app.domain.departaments;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import com.grup8.taskman.app.domain.usuaris.Usuari;

/**
 * Classe que mapejem als camps de la taula departaments i que anotarem amb les validacions que han de tenir als 
 * formularis. Està implementada amb JPA i anotada amb javax.Validation
 * @author Sergio Estebam Gutiérrez
 * @version 1.0.0
 *
 */

@Entity // Indiquem que volem mapejar la classe a la base de dades
@Table(name="departaments") // Associem la classe amb la taula departaments de la base de dades
public class Departament implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ATRIBUTS
	
	
	// Attribut que s'autogenerarà i que marquem con id a la base de dades
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	// Indiquem que codigo no pot ser null i que és únic a la base de dades. també indiquem que la validació no pot ser ni nul 
	// ni espais en blanc.
	@NotBlank
	@Column(name="codigo", unique=true, length=3, nullable=false, updatable=true )
	private String codigo;
	
	// Indiquem que nombre no pot ser null i que és únic a la base de dades. també indiquem que la validació no pot ser ni nul 
	// ni espais en blanc.
	@NotBlank
	@Column(name="nombre", unique=true, length=20, nullable=false, updatable=true )
	private String nombre;	
	
	// Marquen la llista de usuaris com una relació many to many entre departaments i usuaris perquè un departament
	// pot tenir més d'un usuari i aquest pot tenir més d'un departament. A més donem fetch lazy perquè poden haver molts
	// usuaris i no cal descarregar-ho tot de cop.
	@ManyToMany(mappedBy="departaments", fetch=FetchType.LAZY)
	private List<Usuari> usuaris;
	
	// CONSTRUCTOR
	
	public Departament() {		
		
		usuaris=new ArrayList<>();
	}
	
	// GETTERS I SETTERS
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
	
	public List<Usuari> getUsuaris() {
		return usuaris;
	}
	public void setUsuaris(List<Usuari> usuaris) {
		this.usuaris = usuaris;
	}
	
	//MÈTODES
	
	/**
	 * Mètode que afegeix un usuari a la llista d'usuaris
	 * @param usuari Usuari que afegim a la llista
	 */
	public void addUsuari(Usuari usuari) {
		
		usuaris.add(usuari);
	}
	
	/**
	 * Mètode que elimina un usuari de la llista d'usuaris
	 * @param usuari Usuari que volem eliminar de la llista
	 */
	public void removeUsuari(Usuari usuari) {
		
		usuaris.remove(usuari);
	}	
	
	/**
	 * Marquem aquest mètode amb @prePersist perquè volem que abans de guardar a la
	 * base de dades passi codigo a majúscules.
	 */
	@PrePersist
	public void prePersist() {
		
		this.codigo=codigo.toUpperCase();
	}
	
	/**
	 * Fem override del mètode equals perque volem que les comparacions siguin personalitzades pel nom del departament
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
		Departament departament=(Departament) obj;
		
		// Si l'atribut nombre és null i l'atribut nombre d'obj no llavors retornem false
		if(nombre==null) {
			
			if(departament.getNombre()!=null)return false;
			
		// En cas contrari si nombre no és igual a l'atribut nombre d'obj retornem false 	
		}else if(!nombre.equals(departament.getNombre())) {
			
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


