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

/**
 * 
 * Classe que representa cadascuna de les fases que pot realitzar un usuari. Està mapejada a la taula fases_executables 
 * i les validacions estan anotades amb la interfície javax.Validation.
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */

@Entity
@Table(name="fases_executables")
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
		
	// Establim una relació many to one amb FaseConTiempo. Els tipus de cascade responen a que no volem eliminar la fase amb temps si eliminem la fase executable.
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE})	
	private FaseConTiempo fase;
	
	// Establim una relació many to one amb Orden. Els tipus de cascade responen a que no volem eliminar l'ordre si eliminem la fase executable.
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})	
	private Orden orden;
	
	// Camp que indica si la fase ja ha estat realitzada
	@Column(name="notificada", nullable=false)
	private boolean notificada;
	
	// Camp que indica si la fase està bloquejada o no. Si està bloquejada no es pot iniciar.
	@Column(name="bloqueada" , nullable=false)
	private boolean bloqueada;
	
	// Establim una relació one to many amb notificacions indicant que si eliminem una fase executable també eliminem totes les notificacions associades.
	@OneToMany(mappedBy="fase",  cascade=CascadeType.ALL)
	private List<Notificacion> notificaciones;
	
	
	// CONSTRUCTORS
	
	public FaseExecutable(FaseConTiempo fase, Orden orden) {
		
		this.fase = fase;
		this.orden = orden;
		this.notificada=false;
		this.bloqueada=true;
		this.notificaciones=new ArrayList<>();
	}
	
	public FaseExecutable() {}
	
	// SETTERS I GETTERS

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
	
	// Mètodes
	
	/**
	 * Mètode static que calcula la quantitat pendent de realitzar d'una fase a partir de les seves notificacions
	 * @param fase Fase de la qual volem calcular la quantitat pendent
	 * @return Retorna la quantitat pendent de notificar.
	 */
	public static int calcularCantidadPendiente(FaseExecutable fase) {
		
		int totalOrden=fase.getOrden().getCantidad();
		int totalNotificado=0;
		for(Notificacion notificacion: fase.getNotificaciones()) {
			
			totalNotificado+=notificacion.getCantidad();
		}
		
		
		return totalOrden-totalNotificado;
		
	}
	
	/**
	 * Mètode que comprova si s'ha de marcar com notificada una fase executable o no. 
	 * @param fase Fase que volem comprovar
	 * @return Retorna true si la quantitat pendent de notificar és zero, en cas contrari retorna false
	 */
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
	
	/**
	 * Mètode que comprova si la següent fase ha de ser desbloquejada o no. En cas afirmatiu la desbloqueja i la retorna.
	 * @param fase Fase executable que volem comprobar
	 * @return Si hi ha una fase després de la fase rebuda per paràmetre la desbloqueja i la retorna, en cas contrari retorna null.
	 */
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

	/**
	 * Mètode que retorna una llista de fases executables que és generan automàticament a partir de la llista de fases executables que té una ordre ciclica.
	 * @param fases Fases executables que volem clonar per la nova ordre
	 * @param orden Ordre a la que estaran associades les noves fases executables
	 * @return Retorna la llista de fases executables creades
	 */
	public static List<FaseExecutable> generarFasesOrdenCiclica(List<FaseExecutable> fases, Orden orden) {
		
		List<FaseExecutable> fasesExecutables=new ArrayList<FaseExecutable>();
		
		for(FaseExecutable fase: fases)fasesExecutables.add(generarFaseExecutable(fase, orden));
		
		return fasesExecutables;
		
		
	}
	
	/**
	 * Mètode que crea una nova FaseExecutable a partir de les dades rebudes al paràmetre fase i que l'associa a l'ordre rebuda per paràmetre
	 * En crear la fase automàticament la bloqueja i la marca com no notificada.
	 * @param fase Fase executable de la qual extreurem la fase amb temps associada.
	 * @param orden Ordre que estarà associada a la fase executable
	 * @return Retorna la Fase Executable creada.
	 */
	private static FaseExecutable generarFaseExecutable(FaseExecutable fase, Orden orden) {
		
		FaseExecutable faseExecutable=new FaseExecutable();
		faseExecutable.setBloqueada(true);
		faseExecutable.setFase(fase.getFase());
		faseExecutable.setNotificada(false);
		faseExecutable.setOrden(orden);
		
		return faseExecutable;
	}

}
