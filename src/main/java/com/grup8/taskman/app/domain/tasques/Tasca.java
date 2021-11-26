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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="tasques")
public class Tasca implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	// Indiquem que nombre no pot ser null i que és únic a la base de dades. també indiquem que la validació no pot ser ni nul 
	// ni espais en blanc.
	@NotBlank
	@Column(name="descripcion", nullable=false)
	private String descripcion;	
		
	// Indiquem que tiempo no pot ser null a la base de dades.		
	@Column(name="tiempo", nullable=false)
	private int tiempoEstimado;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade= {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name="fase_tasca",			
			joinColumns=@JoinColumn(name="id_fase"),
			inverseJoinColumns=@JoinColumn(name="id_tasca")						
			)
	private List<Fase> fases;

	public Tasca() {
		
	}

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

	public List<Fase> getFases() {
		return fases;
	}

	public void setFases(List<Fase> fases) {
		this.fases = fases;
	}
	
	public void calcularTiempoEstimado() {
		
		int tiempo=0;
		for(Fase fase: fases) {
			
			tiempo+=fase.getTiempoEstimado();
		}
		
		this.setTiempoEstimado(tiempo);
	}
	
	
	
	

}
