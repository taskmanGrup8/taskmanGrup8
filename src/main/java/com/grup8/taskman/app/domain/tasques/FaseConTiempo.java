package com.grup8.taskman.app.domain.tasques;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="fases_con_tiempo")
public class FaseConTiempo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	// Indiquem que tiempo no pot ser null a la base de dades. també indiquem que la validació no pot ser ni null.2
	
	@NotNull
	@Column(name="tiempo", nullable=false)
	private int tiempoEstimado=0;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_fase")
	private Fase fase;
	
	
	@ManyToOne(fetch = FetchType.EAGER)	
	private Tasca tasca;
	
	
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

	public static List<FaseConTiempo> generarLista(List<Fase> fases){
		
		List<FaseConTiempo> fasesConTiempo=new ArrayList<>();
		
		for(Fase fase: fases) {
			
			FaseConTiempo faseConTiempo=new FaseConTiempo();
			faseConTiempo.setFase(fase);
			fasesConTiempo.add(faseConTiempo);
			
		}
		
		
		return fasesConTiempo;
	}
	
	

}
