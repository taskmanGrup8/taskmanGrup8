package com.grup8.taskman.app.domain.departaments;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="departaments")
public class Departament implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	
	@Column(name="codigo", unique=true, length=3, nullable=false, updatable=true )
	private String codigo;
	
	
	@Column(name="nombre", unique=true, length=20, nullable=false, updatable=true )
	private String nombre;
	
	
	
	public Departament() {
		
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
	@Override
	public boolean equals(Object obj) {
		
		if(this==obj)return true;
		if(obj==null)return false;
		if(getClass() !=obj.getClass())return false;
		Departament departament=(Departament) obj;
		if(nombre==null) {
			
			if(departament.getNombre()!=null)return false;
		}else if(!nombre.equals(departament.getNombre())) {
			
			return false;
		}
		
		return true;
	}
	@Override
	public int hashCode() {
		final int prime=31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
		
	}
	
	
	
}


