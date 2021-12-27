package com.grup8.taskman.app.domain.tasques;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="orden")
public class Orden implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	// ATRIBUTS
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@ManyToOne(cascade= {CascadeType.MERGE, CascadeType.PERSIST})	
	private Tasca tasca;
	
	@NotNull
	@Column(name="cantidad", nullable=false)
	private int cantidad;
	
	@Column(name="notificada", nullable=false)
	private boolean notificada;
	
	@OneToMany(mappedBy="orden", fetch=FetchType.LAZY, cascade= {CascadeType.ALL})
	private List<FaseExecutable> fases;
	
	@FutureOrPresent
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="dataFin")
	@Temporal(TemporalType.DATE)
	private Date dataFin;
	
	@NotNull
	@Column(name="prioridad")
	private Prioridad prioridad;
	
	// CONSTRUCTOR
	public Orden() {
		
		this.notificada=false;
		fases=new ArrayList<FaseExecutable>();		
	}
	
	// SETTERS I GETTERS
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Tasca getTasca() {
		return tasca;
	}
	public void setTasca(Tasca tasca) {
		this.tasca = tasca;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public boolean isNotificada() {
		return notificada;
	}
	public void setNotificada(boolean notificada) {
		this.notificada = notificada;
	}
	public List<FaseExecutable> getFases() {
		return fases;
	}
	public void setFases(List<FaseExecutable> fases) {
		this.fases = fases;
	}
	public Date getDataFin() {
		return dataFin;
	}
	public void setDataFin(Date dataFin) {
		this.dataFin = dataFin;
	}
	public Prioridad getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(Prioridad prioridad) {
		this.prioridad = prioridad;
	}	
	
	public void generarFasesExecutables() {
		
		for(FaseConTiempo fase: tasca.getFasesConTiempo()) {
			
			FaseExecutable faseExecutable=new FaseExecutable(fase, this);
			this.fases.add(faseExecutable);
		}
	}
	
	
	
	

}
