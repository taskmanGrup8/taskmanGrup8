package com.grup8.taskman.app.domain.tasques;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.grup8.taskman.app.services.tasques.IOrdenService;
import com.grup8.taskman.app.util.Utilidades;

public class FiltreOrdres {
	
	// CONSTANTS
	
	// CONSTANTS
	
	public static Date fechaInicioDefecto=Utilidades.calcularData(-15);
	public static Date fechaFinDefecto=Utilidades.calcularData(15);
	public static FiltreOrdres filtreOrdre;
	private static final int SENSEFILTRE=0;
	private static final int FILTRATPERTASCA=1;
	private static final int FILTRATPERDATES=2;	
	private static final int FILTRATPERNOTIFICADES=3;
	private static final int FILTRATPERTASCAIDATES=4;	
	private static final int FILTRATPERTASCAINOTIFICADES=5;	
	private static final int FILTRATPERDATESINOTIFICADES=6;	
	private static final int FILTRATPERTASCADATESINOTIFICADES=7;
	
	
	
	
	// ATRIBUTS

	private String nomTasca = "";
	private boolean nomFiltrat = false;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataInici = fechaInicioDefecto;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataFi=fechaFinDefecto;
	private boolean datesFiltrades = false;	
	private int notificadas = 0;
	private boolean notificadesFiltrades = false;
	
	// SETTERS I GETTERS
	
	
	
	// MÈTODES
	
	public FiltreOrdres() {
		
		
	}
	
	public String getNomTasca() {
		return nomTasca;
	}

	public void setNomTasca(String nomTasca) {
		this.nomTasca = nomTasca;
		if(this.nomTasca==null || this.nomTasca.isEmpty()) {
			
			nomFiltrat=false;
		}else {
			
			nomFiltrat=true;
		}
	}

	public boolean isNomFiltrat() {
		return nomFiltrat;
	}

	public void setNomFiltrat(boolean nomFiltrat) {
		this.nomFiltrat = nomFiltrat;
	}

	public Date getDataInici() {
		return dataInici;
	}

	public void setDataInici(Date dataInici) {
		this.dataInici = dataInici;
		if(this.dataInici==fechaInicioDefecto) {
			
			datesFiltrades=false;
		}else {
			
			datesFiltrades=true;
		}
	}

	public Date getDataFi() {
		return dataFi;
	}

	public void setDataFi(Date dataFi) {
		this.dataFi = dataFi;
		
		if(this.dataFi==fechaFinDefecto) {
			
			datesFiltrades=false;
		}else {
			
			datesFiltrades=true;
		}
	}

	public boolean isDatesFiltrades() {
		return datesFiltrades;
	}

	public void setDatesFiltrades(boolean datesFiltrades) {
		this.datesFiltrades = datesFiltrades;
	}
	
	public int getNotificadas() {
		return notificadas;
	}

	public void setNotificadas(int notificadas) {
		this.notificadas = notificadas;
		if(this.notificadas==0) {
			
			notificadesFiltrades=false;
		}else {
			
			notificadesFiltrades=true;
		}
	}

	public boolean isNotificadesFiltrades() {
		return notificadesFiltrades;
	}

	public void setNotificadesFiltrades(boolean notificadesFiltrades) {
		this.notificadesFiltrades = notificadesFiltrades;
	}

	private int getFiltreOrdre() {
		
				
		if(!this.isNomFiltrat() && !this.isDatesFiltrades() && !this.isNotificadesFiltrades())return SENSEFILTRE;
		
		if(this.isNomFiltrat() && !this.isDatesFiltrades() && !this.isNotificadesFiltrades())return FILTRATPERTASCA;
		if(!this.isNomFiltrat() && this.isDatesFiltrades() && !this.isNotificadesFiltrades())return FILTRATPERDATES;		
		if(!this.isNomFiltrat() && !this.isDatesFiltrades() && this.isNotificadesFiltrades())return FILTRATPERNOTIFICADES;
		
		if(this.isNomFiltrat() && this.isDatesFiltrades() && !this.isNotificadesFiltrades())return FILTRATPERTASCAIDATES;		
		if(this.isNomFiltrat() && !this.isDatesFiltrades() && this.isNotificadesFiltrades())return FILTRATPERTASCAINOTIFICADES;		
		if(!this.isNomFiltrat() && this.isDatesFiltrades() && this.isNotificadesFiltrades())return FILTRATPERDATESINOTIFICADES;		
	
		if(this.isNomFiltrat() && this.isDatesFiltrades() && this.isNotificadesFiltrades())return FILTRATPERTASCADATESINOTIFICADES;		
		
		return SENSEFILTRE;
	}
	
	public List<Orden> getOrdres(IOrdenService ordenService){
		
		List<Orden>ordenes=new ArrayList<>();
			
		
		int opcion=this.getFiltreOrdre();
		Date dataAnterior=Utilidades.calcularData(-15);
		Date dataPosterior=Utilidades.calcularData(+15);
		List<Orden>entreFechas=ordenService.findByEntreDates(dataAnterior, dataPosterior);
		List<Orden>llistaNotificades=null;
		List<Orden>perTasca=null;
		
		switch(opcion) {
		
		case SENSEFILTRE:
			
			llistaNotificades=ordenService.findByNotificada(false);
			llistaNotificades.retainAll(entreFechas);
			ordenes=llistaNotificades;		
			break;
			
		case FILTRATPERTASCA:
		
			perTasca=ordenService.findByNomTasca(nomTasca);
			perTasca.retainAll(entreFechas);
			ordenes=perTasca;			
			break;
			
		case FILTRATPERDATES:
		
			ordenes=ordenService.findByEntreDates(dataInici, dataFi);
			break;	
			
		case FILTRATPERNOTIFICADES:
			
			
			if(notificadas==1) {
				
				llistaNotificades=ordenService.findByNotificada(true);
			}else {
				
				llistaNotificades=ordenService.findByNotificada(false);
			}
			
			llistaNotificades.retainAll(entreFechas);
			ordenes=llistaNotificades;			
			break;
			
		case FILTRATPERTASCAIDATES:
			
			perTasca=ordenService.findByNomTasca(nomTasca);
			entreFechas=ordenService.findByEntreDates(dataInici, dataFi);
			perTasca.retainAll(entreFechas);
			ordenes=perTasca;			
			break;		
			
		case FILTRATPERTASCAINOTIFICADES:
			
			perTasca=ordenService.findByNomTasca(nomTasca);
			if(notificadas==1) {
				
				llistaNotificades=ordenService.findByNotificada(true);
			}else {
				
				llistaNotificades=ordenService.findByNotificada(false);
			}
			
			perTasca.retainAll(llistaNotificades);
			perTasca.retainAll(entreFechas);
			ordenes=perTasca;			
			break;		
			
		case FILTRATPERDATESINOTIFICADES:
						
			if(notificadas==1) {
				
				llistaNotificades=ordenService.findByNotificada(true);
			}else {
				
				llistaNotificades=ordenService.findByNotificada(false);
			}
			
			
			entreFechas=ordenService.findByEntreDates(dataInici, dataFi);
			
			
			llistaNotificades.retainAll(entreFechas);
			ordenes=llistaNotificades;
			break;		
			
		case FILTRATPERTASCADATESINOTIFICADES:
			
			perTasca=ordenService.findByNomTasca(nomTasca);
			if(notificadas==1) {
				
				llistaNotificades=ordenService.findByNotificada(true);
			}else {
				
				llistaNotificades=ordenService.findByNotificada(false);
			}
			
			entreFechas=ordenService.findByEntreDates(dataInici, dataFi);
			
			llistaNotificades.retainAll(entreFechas);
			llistaNotificades.retainAll(perTasca);
			ordenes=llistaNotificades;
			break;	
		
		default:
			llistaNotificades=ordenService.findByNotificada(false);
			llistaNotificades.retainAll(entreFechas);
			ordenes=llistaNotificades;		
			break;
		
		}
		
		
		return ordenes;
	}


}
