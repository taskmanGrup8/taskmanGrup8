package com.grup8.taskman.app.domain.tasques;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="fase_executable")
public class FaseExecutable implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// ATRIBUTS
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
		
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE})	
	private FaseConTiempo fase;
	
	@ManyToOne(cascade=CascadeType.ALL)	
	private Orden orden;
	
	@Column(name="notificada", nullable=false)
	private boolean notificada;
	
	@Column(name="bloqueada" , nullable=false)
	private boolean bloqueada;
	
	@OneToMany(mappedBy="fase",  cascade={CascadeType.PERSIST, CascadeType.MERGE})
	private List<Notificacion> notificaciones;
	
	public FaseExecutable(FaseConTiempo fase, Orden orden) {
		
		this.fase = fase;
		this.orden = orden;
		this.notificada=false;
		this.bloqueada=true;
		this.notificaciones=new ArrayList<>();
	}
	
	public FaseExecutable() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FaseConTiempo getFase() {
		return fase;
	}

	public void setFase(FaseConTiempo fase) {
		this.fase = fase;
	}

	public Orden getOrden() {
		return orden;
	}

	public void setOrden(Orden orden) {
		this.orden = orden;
	}

	public boolean isNotificada() {
		return notificada;
	}

	public void setNotificada(boolean notificada) {
		this.notificada = notificada;
	}

	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}

	public boolean isBloqueada() {
		return bloqueada;
	}

	public void setBloqueada(boolean bloqueada) {
		this.bloqueada = bloqueada;
	}
	
	
	
	

}
