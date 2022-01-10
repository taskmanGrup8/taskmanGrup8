package com.grup8.taskman.app.domain.tasques;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.grup8.taskman.app.util.Utilidades;

/**
 * Classe que representa una tasca executable per un usuari. Esta mapejada a la taula ordres mitjançant JPA 
 * i anotada amb validacions de la interfície javax.Validation.
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */

@Entity
@Table(name = "ordres")
public class Orden implements Serializable, Comparable<Orden> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ATRIBUTS

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	// Creem una associació many to one amb tasca indicant un tipus de cascade pel qual si eliminem l'ordre no eliminem la tasca.
	@NotNull
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private Tasca tasca;

	// Atribut que indica la quantitat ha realitzar a aquesta ordre, no pot ser null
	@NotNull
	@Column(name = "cantidad", nullable = false)
	private int cantidad;

	// Camp que indica si l'ordre ja ha estat realitzada o no
	@Column(name = "notificada", nullable = false)
	private boolean notificada;

	// Creem una relació one to many amb FaseExecutable indicant que si eliminem l'ordre, totes les fases executables associades també s'eliminen.
	@OneToMany(mappedBy = "orden", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private List<FaseExecutable> fases;

	// Creem el camp dataFin que només guarda la data a la base de dades
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dataFin")
	@Temporal(TemporalType.DATE)
	private Date dataFin;

	// Camp que estableix la prioritat de l'ordre. No pot ser null
	@NotNull
	@Column(name = "prioridad")
	private Prioridad prioridad;
	
	// Camp que indica si una ordre és cíclica o no
	@Column(name="ciclica")
	private boolean ciclica;
	
	// Atribut marcat con transient perquè no es guardi a la base de dades. Es un atribut d'ajuda per saber si una ordre te fases desbloquejades o no.
	@Transient
	private boolean bloqueada;

	// CONSTRUCTOR
	public Orden() {

		this.notificada = false;
		this.ciclica=false;
		fases = new ArrayList<FaseExecutable>();
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

	public boolean isCiclica() {
		return ciclica;
	}

	public void setCiclica(boolean ciclica) {
		this.ciclica = ciclica;
	}	
	

	public boolean isBloqueada() {
		return bloqueada;
	}

	public void setBloqueada(boolean bloqueada) {
		this.bloqueada = bloqueada;
	}
	
	// MÈTODES
	
	/**
	 * Mètode que genera les Fases executables de l'ordre a partir de les fases amb temps associades a la tasca que conté.
	 * Un cop creades desbloqueja la primera de les fases.
	 */
	public void generarFasesExecutables() {

		
		for (FaseConTiempo fase : tasca.getFasesConTiempo()) {

			FaseExecutable faseExecutable = new FaseExecutable(fase, this);
			this.fases.add(faseExecutable);
		}
		
		fases.get(0).setBloqueada(false);
	}

	/**
	 * Mètode que sobreescribim per que les comparaciones les faci per la data de finalització i després per la prioritat.
	 */
	@Override
	public int compareTo(Orden o) {

		int result = -1;
		if (Utilidades.restarFechas(this.dataFin, o.getDataFin()) < 0) {

			result = 1;
		} else if (Utilidades.restarFechas(this.dataFin, o.getDataFin()) == 0) {

			if (this.prioridad.ordinal() > o.getPrioridad().ordinal()) {

				result = 1;
			} else if (this.prioridad.ordinal() == o.getPrioridad().ordinal()) {

				result = 0;
			}
		}

		return result;

	}

	/**
	 * Mètode que comproba si s'ha de reparar l'estat del camp notificada.
	 * @param orden Ordre que volem comprobar
	 * @return Si l'ordre està notificada i alguna fase no l'esta retorna true. Si una ordre no està notificada i totes les
	 * seves fases si ho estan retorna true, en qualsevol altre cas retorna false.
	 */
	public static boolean comprobarNotificacion(Orden orden) {

		if (orden.isNotificada()) {

			for (FaseExecutable fase : orden.getFases()) {

				if (!fase.isNotificada())
					return true;
			}

		} else {

			for (FaseExecutable fase : orden.getFases()) {

				if (!fase.isNotificada())
					return false;
			}
			
			return true;
		}

		return false;

	}

	/**
	 * Mètode que comprova si s'ha de reparar bloquejos a les fases executables de l'ordre rebuda.	 * 
	 * @param orden Ordre que volem comprovar.
	 * @return Si cap fase està notificada i la següent està bloquejada retorna true. Si cap fase no està
	 * notificada i la següent no està bloquejada retorna true. A la resta de casos retorna false.
	 */
	public static boolean comprobarBloqueos(Orden orden) {

		
		List<FaseExecutable> fasesOrdenadas=orden.getFases();
		
		Collections.sort(fasesOrdenadas,  new Comparator<FaseExecutable>() {

			@Override
			public int compare(FaseExecutable o1, FaseExecutable o2) {
				
				return o1.getId().compareTo(o2.getId());
			}
			
			
		});		
		
		for (int i = 0; i < fasesOrdenadas.size(); i++) {

			if(i<fasesOrdenadas.size()-1) {
				if (fasesOrdenadas.get(i).isNotificada() && fasesOrdenadas.get(i + 1).isBloqueada())
					return true;
				if (!fasesOrdenadas.get(i).isNotificada() && !fasesOrdenadas.get(i + 1).isBloqueada())
					return true;
			}
		}

		return false;
	}
	
	/**
	 * Mètode que repara els bloquejos de les fases executables de l'ordre rebuda.
	 * @param orden Ordre que volem reparar
	 */
	public static void repararBloqueos(Orden orden) {
		
		List<FaseExecutable> fasesOrdenadas=orden.getFases();
	
		//Ordenem les fases per id.
		Collections.sort(fasesOrdenadas,  new Comparator<FaseExecutable>() {

			@Override
			public int compare(FaseExecutable o1, FaseExecutable o2) {
				
				return o1.getId().compareTo(o2.getId());
			}
			
			
		});		
		
		// Cada cop com a màxim haurem de reparar una perquè sempre que hi ha un canvi es comprova

		for (int i = 0; i < orden.getFases().size(); i++) {

			// Ens assegurem de no superar l'index màxim quan fem i+1, si estem a la darrera no hem de reparar res
			if(i<orden.getFases().size()-1) {
				// Si la fase actual està notificada i la següent està bloquejada
				if (fasesOrdenadas.get(i).isNotificada() && fasesOrdenadas.get(i + 1).isBloqueada()) {

					// desbloquejem la següent fase de la llista i canviem la llista de l'ordre rebuda per la llista reparada.
					fasesOrdenadas.get(i + 1).setBloqueada(false);
					orden.setFases(fasesOrdenadas);
					// Com que ja hem reparat sortim
					return;
				}

				// Si la fase no està notificada i la següent no està bloquejada
				if (!fasesOrdenadas.get(i).isNotificada() && !fasesOrdenadas.get(i + 1).isBloqueada()) {
					// Bloquejem la següent fase i actualitzem la llista de fases de l'ordre rebuda.
					fasesOrdenadas.get(i + 1).setBloqueada(true);
					orden.setFases(fasesOrdenadas);
					// Com que ja hem reparat sortim
					return;

				}
			}
		}

	}
	
	/**
	 * Mètode que repara la notificació de l'ordre rebuda en funció de les fases.
	 * @param orden Ordre que volem reparar
	 */
	public static void repararNotificacion(Orden orden) {
		
		// Si l'ordre està notificada
		if (orden.isNotificada()) {

			for (FaseExecutable fase : orden.getFases()) {

				// Si alguna fase no està notificada marquem l'ordre com no notificada
				if (!fase.isNotificada())
					orden.setNotificada(false);
			}

		} else {
			
			// Si la fase no està notificada i alguna fase no està notificada sortim, en cas contrari notifiquem l'ordre.

			for (FaseExecutable fase : orden.getFases()) {

				if (!fase.isNotificada())
					return;
			}
			
			orden.setNotificada(true);
		}

		return;
		
	}
	
	/**
	 * Mètode que genera una nova ordre cíclica
	 * @param ordenRecibida Ordre cíclica a partir de la qual en creem la nova
	 * @return Retornem la nova ordre cíclica
	 */
	public static Orden generarOrdenCiclica(Orden ordenRecibida) {
		
		Orden orden=new Orden();
		orden.setCantidad(ordenRecibida.getCantidad());
		orden.setCiclica(true);
		Date fechaFin=ordenRecibida.getDataFin();
		orden.setDataFin(Utilidades.sumarDiasAFecha(fechaFin, ordenRecibida.getTasca().getTiempoCiclo()));
		orden.setNotificada(false);
		orden.setPrioridad(ordenRecibida.getPrioridad());
		orden.setTasca(ordenRecibida.getTasca());
		orden.setFases(FaseExecutable.generarFasesOrdenCiclica(ordenRecibida.getFases(), orden));
		orden.getFases().get(0).setBloqueada(false);		
		
		return orden;
	}

}
