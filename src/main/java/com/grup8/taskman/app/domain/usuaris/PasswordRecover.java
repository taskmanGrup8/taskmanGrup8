package com.grup8.taskman.app.domain.usuaris;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="password_recover")
public class PasswordRecover implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// CONSTANTS
	
	public static final String LINKMAIL="localhost:8095/recover/recoverPassword/";
	
	// ATRIBUTS
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_user")
	private Usuari usuari;
	
	@Column(name="link", unique=true)
	private String link;
	
	@Column(name="data")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	// SETTERS i GETTERS 
	
	public Long getId() {
		return id;
	}		
	public void setId(Long id) {
		this.id = id;
	}
	public Usuari getUsuari() {
		return usuari;
	}
	public void setUsuari(Usuari usuari) {
		this.usuari = usuari;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	

}
