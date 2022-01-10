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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.util.Utilidades;

/**
 * Classe que representa una porció acabada de una fase executable per part d'un usuari.
 * Està mapejada a la taula notificacions de la base de dades mitjançant JPA.
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */

@Entity
@Table(name="notificacions")
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
	
	// Data i hora en que comença la notificació
	@Column(name="dataInici")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInici;
	
	// Data i hora en que termina la notificació
	@Column(name="dataFin")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFin;
	
	// Relació many to one amb usuari indicant que si eliminem la notificació l'usuari no s'elimina. Usuari es quí ha realitzat la notificació
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private Usuari usuari;
	
	// Quantitat de fase realitzada en aquesta notificació, no pot ser null
	@NotNull
	@Column(name="cantidad", nullable=false)
	private int cantidad;
	
	// Relació many to one amb fase executable indicant que si eliminem la notificació la fase executable no s'elimina. És la fase a qui correspon la notificació.
	@ManyToOne(cascade= {CascadeType.MERGE, CascadeType.PERSIST})	
	private FaseExecutable fase;
	
	// Atribut anotat amb @Transient perquè no estigui a la base de dades. Servirà per calcular el temps trigat a la notificació.
	@Transient
	private long tiempo;
	
	// CONSTRUCTOR
	
	public Notificacion() {
		
		this.dataInici=new Date();
	}
	
	// SETTERS I GETTERS
	
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
	
	public long getTiempo() {
		return tiempo;
	}

	public void setTiempo(long tiempo) {
		this.tiempo = tiempo;
	}
	
	// MÈTODES

	/**
	 * Mètode que calcula el temps que ha passat entre la data d'inici i la data de finalització de la notificació calculat 
	 * en minuts i l'assigna a l'atribut tiempo.
	 */
	public void calculaTiempo() {
		
		tiempo=Utilidades.restarFechasEnMinutos(dataFin, dataInici);
	}
	
	/**
	 * Mètode static que crea una nova notificació amb els paràmetres rebuts
	 * @param usuari Usuari que fa la notificació
	 * @param fase Fase executable a qui pertany la notificació
	 * @return Retorna la nova notificació creada.
	 */
	public static Notificacion crear(Usuari usuari, FaseExecutable fase) {
		
		Notificacion notificacion=new Notificacion();
		notificacion.setUsuari(usuari);
		notificacion.setFase(fase);
		notificacion.setCantidad(0);
		
		return notificacion;
	}
	

}
