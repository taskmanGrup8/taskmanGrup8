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
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.grup8.taskman.app.util.Utilidades;

@Entity
@Table(name = "orden")
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

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private Tasca tasca;

	@NotNull
	@Column(name = "cantidad", nullable = false)
	private int cantidad;

	@Column(name = "notificada", nullable = false)
	private boolean notificada;

	@OneToMany(mappedBy = "orden", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private List<FaseExecutable> fases;

	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dataFin")
	@Temporal(TemporalType.DATE)
	private Date dataFin;

	@NotNull
	@Column(name = "prioridad")
	private Prioridad prioridad;
	
	@Column(name="ciclica")
	private boolean ciclica;

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

	public void generarFasesExecutables() {

		for (FaseConTiempo fase : tasca.getFasesConTiempo()) {

			FaseExecutable faseExecutable = new FaseExecutable(fase, this);
			this.fases.add(faseExecutable);
		}
		
		fases.get(0).setBloqueada(false);
	}

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

	public static boolean comprobarBloqueos(Orden orden) {

		
		List<FaseExecutable> fasesOrdenadas=orden.getFases();
		
		Collections.sort(fasesOrdenadas,  new Comparator<FaseExecutable>() {

			@Override
			public int compare(FaseExecutable o1, FaseExecutable o2) {
				
				return o1.getId().compareTo(o2.getId());
			}
			
			
		});		
		
		for (int i = 0; i < fasesOrdenadas.size(); i++) {

			if (fasesOrdenadas.get(i).isNotificada() && fasesOrdenadas.get(i + 1).isBloqueada())
				return true;
			if (!fasesOrdenadas.get(i).isNotificada() && !fasesOrdenadas.get(i + 1).isBloqueada())
				return true;
		}

		return false;
	}

	public static void repararBloqueos(Orden orden) {
		
	List<FaseExecutable> fasesOrdenadas=orden.getFases();
		
		Collections.sort(fasesOrdenadas,  new Comparator<FaseExecutable>() {

			@Override
			public int compare(FaseExecutable o1, FaseExecutable o2) {
				
				return o1.getId().compareTo(o2.getId());
			}
			
			
		});		
		

		for (int i = 0; i < orden.getFases().size(); i++) {

			if (fasesOrdenadas.get(i).isNotificada() && fasesOrdenadas.get(i + 1).isBloqueada()) {

				fasesOrdenadas.get(i + 1).setBloqueada(false);
				orden.setFases(fasesOrdenadas);
				return;
			}

			if (!fasesOrdenadas.get(i).isNotificada() && !fasesOrdenadas.get(i + 1).isBloqueada()) {

				fasesOrdenadas.get(i + 1).setBloqueada(true);
				orden.setFases(fasesOrdenadas);
				return;

			}
		}

	}
	
	public static void repararNotificacion(Orden orden) {
		
		
		if (orden.isNotificada()) {

			for (FaseExecutable fase : orden.getFases()) {

				if (!fase.isNotificada())
					orden.setNotificada(false);
			}

		} else {

			for (FaseExecutable fase : orden.getFases()) {

				if (!fase.isNotificada())
					return;
			}
			
			orden.setNotificada(true);
		}

		return;
		
	}
	
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
