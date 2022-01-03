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
	
	@OneToMany(mappedBy="fase",  cascade=CascadeType.ALL)
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
	
	public static int calcularCantidadPendiente(FaseExecutable fase) {
		
		int totalOrden=fase.getOrden().getCantidad();
		int totalNotificado=0;
		for(Notificacion notificacion: fase.getNotificaciones()) {
			
			totalNotificado+=notificacion.getCantidad();
		}
		
		
		return totalOrden-totalNotificado;
		
	}
	
	public static boolean comprobarNotificacion(FaseExecutable fase) {
		
		boolean result=false;
		int pendiente=calcularCantidadPendiente(fase);
		
		if(pendiente==0) {
			
			if(!fase.isNotificada())result=true;
			
		}else {
			
			if(fase.isNotificada())result=true;
			
		}
		
		return result;
	}
	
	public static FaseExecutable desbloquearSiguienteFase(FaseExecutable fase){
		
		
		if(fase!=null) {
			
			Orden orden=fase.getOrden();
			for(int i=0; i<orden.getFases().size(); i++) {
				
				if(orden.getFases().get(i).getId()==fase.getId()) {
					
					if(i+1>=orden.getFases().size()) {
						return null;
					}else {
						
						orden.getFases().get(i+1).setBloqueada(false);
						return orden.getFases().get(i+1);
					}
					
				}
			}
		}
		
		return null;
	}

	public static List<FaseExecutable> generarFasesOrdenCiclica(List<FaseExecutable> fases, Orden orden) {
		
		List<FaseExecutable> fasesExecutables=new ArrayList<FaseExecutable>();
		
		for(FaseExecutable fase: fases)fasesExecutables.add(generarFaseExecutable(fase, orden));
		
		return fasesExecutables;
		
		
	}
	
	private static FaseExecutable generarFaseExecutable(FaseExecutable fase, Orden orden) {
		
		FaseExecutable faseExecutable=new FaseExecutable();
		faseExecutable.setBloqueada(true);
		faseExecutable.setFase(fase.getFase());
		faseExecutable.setNotificada(false);
		faseExecutable.setOrden(orden);
		
		return faseExecutable;
	}

}
