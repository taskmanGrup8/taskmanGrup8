package com.grup8.taskman.app.domain.tasques;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
/**
 * Classe que representa una especificació de les classes genèriques que estarà associada a una tasca en particular
 * i a la qual podrem establir un temps estimat.
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
@Entity
@Table(name="fases_con_tiempo")
public class FaseConTiempo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// ATRIBUTS

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	// Indiquem que tiempo no pot ser null a la base de dades. també indiquem que la validació no pot ser null.
	
	@NotNull
	@Column(name="tiempo", nullable=false)
	private int tiempoEstimado;
	
	// Establim la relació amb la taula fases mitjançant l'id de fase. Es una relació many to one perquè d'una fase podem fer moltes fases amb temps.
	@ManyToOne(fetch = FetchType.EAGER, cascade= {CascadeType.REFRESH, CascadeType.MERGE})
	@JoinColumn(name="id_fase")
	private Fase fase;
	
	// Establim una relació many to one amb tasca. Una tasca pot tenir multiples fases amb temps
	@ManyToOne(fetch = FetchType.EAGER)	
	private Tasca tasca;
	
	@OneToMany(mappedBy="fase",  cascade=CascadeType.ALL)
	private List<FaseExecutable> fasesExecutables;
	
	
	// SETTERS I GETTERS	
	
	public int getTiempoEstimado() {
		return tiempoEstimado;
	}

	public void setTiempoEstimado(int tiempoEstimado) {
		this.tiempoEstimado = tiempoEstimado;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	
	
	public Fase getFase() {
		return fase;
	}

	public void setFase(Fase fase) {
		this.fase = fase;
	}	
	

	public Tasca getTasca() {
		return tasca;
	}

	public void setTasca(Tasca tasca) {
		this.tasca = tasca;
	}
	
	
	
	
	// MÈTODES

	public List<FaseExecutable> getFasesExecutables() {
		return fasesExecutables;
	}

	public void setFasesExecutables(List<FaseExecutable> fasesExecutables) {
		this.fasesExecutables = fasesExecutables;
	}

	/**
	 * Mètode que a partir de la llista de fases rebuda per paràmetre genera una llista de fases amb temps
	 * @param fases Llista de fases que utilitzarem per generar la nova llista
	 * @return Retorna la llista de fases amb temps generada
	 */
	public static List<FaseConTiempo> generarLista(List<Fase> fases){
		
		List<FaseConTiempo> fasesConTiempo=new ArrayList<>();
		// Per cada fase afegint una nova fase amb temps.
		for(Fase fase: fases) {
			
			FaseConTiempo faseConTiempo=new FaseConTiempo();
			faseConTiempo.setFase(fase);
			fasesConTiempo.add(faseConTiempo);
			
		}
		
		// Retornem la llista.
		return fasesConTiempo;
	}	

}
