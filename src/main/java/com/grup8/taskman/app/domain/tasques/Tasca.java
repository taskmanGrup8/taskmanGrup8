package com.grup8.taskman.app.domain.tasques;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
/**
 * Classe que representa una plantilla de una tasca que posteriorment serà utilitzada per crear ordres d'execució.
 * Està relacionada amb la taula tasques de la base de dades. 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
@Entity
@Table(name="tasques")
public class Tasca implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// ATRIBUTS

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	// Indiquem que codigo no pot ser null i que és únic a la base de dades. també indiquem que la validació no pot ser ni nul 
	// ni espais en blanc.
	@NotBlank
	@Column(name="codigo", unique=true, length=3, nullable=false)
	private String codigo;
	
	// Indiquem que nombre no pot ser null i que és únic a la base de dades. també indiquem que la validació no pot ser ni nul 
	// ni espais en blanc.
	@NotBlank
	@Column(name="nombre", unique=true, length=40, nullable=false)
	private String nombre;	
	
	// Indiquem que descripció no pot ser null a la base de dades. també indiquem que la validació no pot ser ni nul 
	// ni espais en blanc.
	@NotBlank
	@Column(name="descripcion", nullable=false)
	private String descripcion;	
		
	// Indiquem que tiempo no pot ser null a la base de dades.		
	@Column(name="tiempo", nullable=false)
	private int tiempoEstimado;
	
	// Indiquem que cíclica no pot ser null a la base de dades., Per defecte és false
	@Column(name="ciclica", nullable=false)
	private boolean ciclica=false;
	
	// Indiquem que temps no pot ser null a la base de dades, mentre cíclica sigui false el valor estarà establit a -1
	@Column(name="tiempoCiclo", nullable=false)
	private int tiempoCiclo=0;
	
	// Establim la relació one to many amb FaseConTiempo i la mapejem a l'atribut tasca.
	@OneToMany(mappedBy="tasca", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private List<FaseConTiempo> fasesConTiempo;
	
	// Anotem amb transient perque hibernate ignori aquest camp en crear la base de dades.
	@Transient
	private List<Fase> fases;

	// CONSTRUCTOR
	
	public Tasca() {
		
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

	public int getTiempoEstimado() {
		return tiempoEstimado;
	}

	public void setTiempoEstimado(int tiempoEstimado) {
		this.tiempoEstimado = tiempoEstimado;
	}	
	
	public List<FaseConTiempo> getFasesConTiempo() {
		return fasesConTiempo;
	}

	public void setFasesConTiempo(List<FaseConTiempo> fasesConTiempo) {
		this.fasesConTiempo = fasesConTiempo;
	}

	public List<Fase> getFases() {
		return fases;
	}

	public void setFases(List<Fase> fases) {
		this.fases = fases;
	}
	

	public boolean isCiclica() {
		return ciclica;
	}

	public void setCiclica(boolean ciclica) {
		this.ciclica = ciclica;
	}

	public int getTiempoCiclo() {
		return tiempoCiclo;
	}

	public void setTiempoCiclo(int tiempoCiclo) {
		this.tiempoCiclo = tiempoCiclo;
	}

	// MÈTODES
	
	/**
	 * Mètode que calcula el temps estimat de la tasca a partir de les fases amb temps.
	 */
	public void calcularTiempoEstimado() {
		
		int tiempo=0;
		for(FaseConTiempo fase: fasesConTiempo) {
			
			tiempo+=fase.getTiempoEstimado();
		}
		
		this.setTiempoEstimado(tiempo);
	}
	
	/**
	 * Mètode que converteix el contingut de l'atribut codigo a majúscules abans de guardar a la base de dades
	 */
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
		Tasca tasca=(Tasca) obj;
		
		// Si l'atribut nombre és null i l'atribut nombre d'obj no llavors retornem false
		if(nombre==null) {
			
			if(tasca.getNombre()!=null)return false;
			
		// En cas contrari si nombre no és igual a l'atribut nombre d'obj retornem false 	
		}else if(!nombre.equals(tasca.getNombre())) {
			
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
