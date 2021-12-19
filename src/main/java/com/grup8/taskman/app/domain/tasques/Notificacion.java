package com.grup8.taskman.app.domain.tasques;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.grup8.taskman.app.domain.usuaris.Usuari;

@Entity
@Table(name="notificacion")
public class Notificacion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// ATRIBUTS
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="dataInici")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInici;
	
	@Column(name="dataFin")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFin;
	
	@ManyToOne	
	private Usuari usuari;
	
	@Column(name="cantidad", nullable=false)
	private int cantidad;
	
	@ManyToOne(cascade=CascadeType.ALL)	
	private FaseExecutable fase;
	
	// CONSTRUCTOR
	
	public Notificacion() {
		
		this.dataInici=new Date();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDataInici() {
		return dataInici;
	}
	public void setDataInici(Date dataInici) {
		this.dataInici = dataInici;
	}
	public Date getDataFin() {
		return dataFin;
	}
	public void setDataFin(Date dataFin) {
		this.dataFin = dataFin;
	}
	public Usuari getUsuari() {
		return usuari;
	}
	public void setUsuari(Usuari usuari) {
		this.usuari = usuari;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public FaseExecutable getFase() {
		return fase;
	}
	public void setFase(FaseExecutable fase) {
		this.fase = fase;
	}	
	

}
