package com.grup8.taskman.app.domain.usuaris;

public class FiltreUsuaris {
	
	public static final int USUARIOACTIVOROLNO=0;
	public static final int USUARIOACTIVOROLSI=1;
	public static final int USUARIOHISTORICOROLNO=2;
	public static final int USUARIOHISTORICOROLSI=3;
	public static final int USUARIOACTIVONOMBREROLSI=4;
	public static final int USUARIOACTIVONOMBREROLNO=5;
	public static final int USUARIOHISTORICONOMBREROLSI=6;
	public static final int USUARIOHISTORICONOMBREROLNO=7;	
	public static final int USUARIOACTIVOAPELLIDOROLSI=8;
	public static final int USUARIOACTIVOAPELLIDOROLNO=9;
	public static final int USUARIOHISTORICOAPELLIDOROLSI=10;
	public static final int USUARIOHISTORICOAPELLIDOROLNO=11;
	public static final int USUARIOACTIVODNIROLSI=12;
	public static final int USUARIOACTIVODNIROLNO=13;
	public static final int USUARIOHISTORICODNIROLSI=14;
	public static final int USUARIOHISTORICODNIROLNO=15;	
	public static final int USUARIOACTIVONOMBREYAPELLIDOROLSI=16;
	public static final int USUARIOACTIVONOMBREYAPELLIDOROLNO=17;	
	public static final int USUARIOHISTORICONOMBREYAPELLIDOROLSI=18;
	public static final int USUARIOHISTORICONOMBREYAPELLIDOROLNO=19;
	public static final int USUARIOACTIVONOMBREYDNIROLSI=20;
	public static final int USUARIOACTIVONOMBREYDNIROLNO=21;	
	public static final int USUARIOHISTORICONOMBREYDNIROLSI=22;
	public static final int USUARIOHISTORICONOMBREYDNIROLNO=23;
	public static final int USUARIOACTIVOAPELLIDOYDNIROLSI=24;
	public static final int USUARIOACTIVOAPELLIDOYDNIROLNO=25;	
	public static final int USUARIOHISTORICOAPELLIDOYDNIROLSI=26;
	public static final int USUARIOHISTORICOAPELLIDOYDNIROLNO=27;
	public static final int USUARIOACTIVONOMBREAPELLIDOYDNIROLSI=28;
	public static final int USUARIOACTIVONOMBREAPELLIDOYDNIROLNO=29;	
	public static final int USUARIOHISTORICONOMBREAPELLIDOYDNIROLSI=30;
	public static final int USUARIOHISTORICONOMBREAPELLIDOYDNIROLNO=31;
	
	
	
			

	private String nom="";
	private boolean nomFiltrat = false;
	private String cognoms="";
	private boolean cognomsFiltrats = false;
	private boolean filtrat = false;
	private String dni="";
	private boolean dniFiltrat=false;
	private Long rol=new Long(0);
	private int historic=0;
	

	

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		
		
			this.nom=nom;
		
		
		if (this.nom == null || this.nom.isEmpty()) {
			setNomFiltrat(false);
		} else {
			setNomFiltrat(true);
		}
	}

	public String getCognoms() {
		return cognoms;
	}

	public void setCognoms(String cognoms) {
		
		
			this.cognoms = cognoms;
		
		

		if (this.cognoms == null || this.cognoms.isEmpty()) {
			setCognomsFiltrats(false);
		} else {
			setCognomsFiltrats(true);
		}
	}

	public boolean isFiltrat() {
		return filtrat;
	}

	public void setFiltrat(boolean filtrat) {
		this.filtrat = filtrat;
	}

	public boolean isNomFiltrat() {
		return nomFiltrat;
	}

	public void setNomFiltrat(boolean nomfiltrat) {
		this.nomFiltrat = nomfiltrat;
	}

	public boolean isCognomsFiltrats() {
		return cognomsFiltrats;
	}

	public void setCognomsfiltrats(boolean cognomsfiltrats) {
		this.cognomsFiltrats = cognomsfiltrats;
	}

	public void setCognomsFiltrats(boolean cognomsFiltrats) {
		this.cognomsFiltrats = cognomsFiltrats;
	}	

	public int getHistoric() {
		return historic;
	}

	public void setHistoric(int historic) {
		
		
		
			this.historic = historic;
		
		
		if(historic==0) {
			setFiltrat(false);
		}else {
			
			setFiltrat(true);
		}
	}
	
	

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		
		
			this.dni = dni;
		
		if (this.dni == null || this.dni.isEmpty()) {
			setDniFiltrat(false);
		} else {
			setDniFiltrat(true);
		}
		
		
	}

	public boolean isDniFiltrat() {
		return dniFiltrat;
	}

	public void setDniFiltrat(boolean dniFiltrat) {
		this.dniFiltrat = dniFiltrat;
	}

	public Long getRol() {
		return rol;
	}

	public void setRol(Long rol) {
		
		
			this.rol = rol;
		
		
	}
		
	public int getFiltreUsuaris() {

		int result = -1;

		// Si es activo y da igual el rol
		if (!isFiltrat() && getRol() == 0) {

			// Si tiene nombre o apellido o dni
			if (isNomFiltrat() || isCognomsFiltrats() || isDniFiltrat()) {

				// Si todos están filtrados
				if (isNomFiltrat() && isCognomsFiltrats() && isDniFiltrat()) {

					// En este caso serà activo, rol no, nombre, apellido y dni

					result = FiltreUsuaris.USUARIOACTIVONOMBREAPELLIDOYDNIROLNO;
				}

				else if (isNomFiltrat() && isCognomsFiltrats()
						&& !isDniFiltrat()) {

					// En este caso será activo, rol no, nombre y apellido

					result = FiltreUsuaris.USUARIOACTIVONOMBREYAPELLIDOROLNO;
				} else if (isNomFiltrat() && !isCognomsFiltrats()
						&& isDniFiltrat()) {

					// En este caso será activo, rol no, nombre y dni

					result = FiltreUsuaris.USUARIOACTIVONOMBREYDNIROLNO;
				} else if (!isNomFiltrat() && isCognomsFiltrats()
						&& isDniFiltrat()) {

					// En este caso será activo, rol no, apellido y dni

					result = FiltreUsuaris.USUARIOACTIVOAPELLIDOYDNIROLNO;
				} else if (isNomFiltrat() && !isCognomsFiltrats()
						&& !isDniFiltrat()) {

					// En este caso será activo, rol no y nombre

					result = FiltreUsuaris.USUARIOACTIVONOMBREROLNO;
				} else if (!isNomFiltrat() && !isCognomsFiltrats()
						&& isDniFiltrat()) {

					// En este caso será activo, rol no y dni

					result = FiltreUsuaris.USUARIOACTIVODNIROLNO;
				} else if (!isNomFiltrat() && isCognomsFiltrats()
						&& !isDniFiltrat()) {

					// En este caso será activo, rol no y apellido

					result = FiltreUsuaris.USUARIOACTIVOAPELLIDOROLNO;
				}

			} else {

				// En este caso es activo y no rol

				result = FiltreUsuaris.USUARIOACTIVOROLNO;
			}

			// Si es historico y da igual el rol
		} else if (isFiltrat() && getRol() == 0) {

			// Si tiene nombre o apellido o dni
			if (isNomFiltrat() || isCognomsFiltrats() || isDniFiltrat()) {

				// Si todos están filtrados
				if (isNomFiltrat() && isCognomsFiltrats() && isDniFiltrat()) {

					// En este caso serà historico, rol no, nombre, apellido y dni

					result = FiltreUsuaris.USUARIOHISTORICONOMBREAPELLIDOYDNIROLNO;
				}

				else if (isNomFiltrat() && isCognomsFiltrats()
						&& !isDniFiltrat()) {

					// En este caso será historico, rol no, nombre y apellido

					result = FiltreUsuaris.USUARIOHISTORICONOMBREYAPELLIDOROLNO;
				} else if (isNomFiltrat() && !isCognomsFiltrats()
						&& isDniFiltrat()) {

					// En este caso será historico, rol no, nombre y dni

					result = FiltreUsuaris.USUARIOHISTORICONOMBREYDNIROLNO;
				} else if (!isNomFiltrat() && isCognomsFiltrats()
						&& isDniFiltrat()) {

					// En este caso será historico, rol no, apellido y dni

					result = FiltreUsuaris.USUARIOHISTORICOAPELLIDOYDNIROLNO;
				} else if (isNomFiltrat() && !isCognomsFiltrats()
						&& !isDniFiltrat()) {

					// En este caso será historico, rol no y nombre

					result = FiltreUsuaris.USUARIOHISTORICONOMBREROLNO;
				} else if (!isNomFiltrat() && !isCognomsFiltrats()
						&& isDniFiltrat()) {

					// En este caso será historico, rol no y dni

					result = FiltreUsuaris.USUARIOHISTORICODNIROLNO;
				} else if (!isNomFiltrat() && isCognomsFiltrats()
						&& !isDniFiltrat()) {

					// En este caso será historico, rol no y apellido

					result = FiltreUsuaris.USUARIOHISTORICOAPELLIDOROLNO;
				}

			} else {

				// En este caso es historico y no rol

				result = FiltreUsuaris.USUARIOHISTORICOROLNO;
			}

			// Si es activo y no da igual el rol
		} else if (!isFiltrat() && getRol() != 0) {

			// Si tiene nombre o apellido o dni
			if (isNomFiltrat() || isCognomsFiltrats() || isDniFiltrat()) {

				// Si todos están filtrados
				if (isNomFiltrat() && isCognomsFiltrats() && isDniFiltrat()) {

					// En este caso serà activo, rol si, nombre, apellido y dni

					result = FiltreUsuaris.USUARIOACTIVONOMBREAPELLIDOYDNIROLSI;
				}

				else if (isNomFiltrat() && isCognomsFiltrats()
						&& !isDniFiltrat()) {

					// En este caso será activo, rol si, nombre y apellido

					result = FiltreUsuaris.USUARIOACTIVONOMBREYAPELLIDOROLSI;
				} else if (isNomFiltrat() && !isCognomsFiltrats()
						&& isDniFiltrat()) {

					// En este caso será activo, rol si, nombre y dni

					result = FiltreUsuaris.USUARIOACTIVONOMBREYDNIROLSI;
				} else if (!isNomFiltrat() && isCognomsFiltrats()
						&& isDniFiltrat()) {

					// En este caso será activo, rol si, apellido y dni

					result = FiltreUsuaris.USUARIOACTIVOAPELLIDOYDNIROLSI;
				} else if (isNomFiltrat() && !isCognomsFiltrats()
						&& !isDniFiltrat()) {

					// En este caso será activo, rol si y nombre

					result = FiltreUsuaris.USUARIOACTIVONOMBREROLSI;
				} else if (!isNomFiltrat() && !isCognomsFiltrats()
						&& isDniFiltrat()) {

					// En este caso será activo, rol si y dni

					result = FiltreUsuaris.USUARIOACTIVODNIROLSI;
				} else if (!isNomFiltrat() && isCognomsFiltrats()
						&& !isDniFiltrat()) {

					// En este caso será activo, rol si y apellido

					result = FiltreUsuaris.USUARIOACTIVOAPELLIDOROLSI;
				}

			} else {

				// En este caso es activo y si rol

				result = FiltreUsuaris.USUARIOACTIVOROLSI;
			}

			// Si es historico y no da igual el rol
		} else {

			// Si tiene nombre o apellido o dni
			if (isNomFiltrat() || isCognomsFiltrats() || isDniFiltrat()) {

				// Si todos están filtrados
				if (isNomFiltrat() && isCognomsFiltrats() && isDniFiltrat()) {

					// En este caso serà historico, rol si, nombre, apellido y dni

					result = FiltreUsuaris.USUARIOHISTORICONOMBREAPELLIDOYDNIROLSI;
				}

				else if (isNomFiltrat() && isCognomsFiltrats()
						&& !isDniFiltrat()) {

					// En este caso será historico, rol si, nombre y apellido

					result = FiltreUsuaris.USUARIOHISTORICONOMBREYAPELLIDOROLSI;
				} else if (isNomFiltrat() && !isCognomsFiltrats()
						&& isDniFiltrat()) {

					// En este caso será historico, rol si, nombre y dni

					result = FiltreUsuaris.USUARIOHISTORICONOMBREYDNIROLSI;
				} else if (!isNomFiltrat() && isCognomsFiltrats()
						&& isDniFiltrat()) {

					// En este caso será historico, rol si, apellido y dni

					result = FiltreUsuaris.USUARIOHISTORICOAPELLIDOYDNIROLSI;
				} else if (isNomFiltrat() && !isCognomsFiltrats()
						&& !isDniFiltrat()) {

					// En este caso será historico, rol si y nombre

					result = FiltreUsuaris.USUARIOHISTORICONOMBREROLSI;
				} else if (!isNomFiltrat() && !isCognomsFiltrats()
						&& isDniFiltrat()) {

					// En este caso será historico, rol si y dni

					result = FiltreUsuaris.USUARIOHISTORICODNIROLSI;
				} else if (!isNomFiltrat() && isCognomsFiltrats()
						&& !isDniFiltrat()) {

					// En este caso será historico, rol si y apellido

					result = FiltreUsuaris.USUARIOHISTORICOAPELLIDOROLSI;
				}

			} else {

				// En este caso es historico y si rol

				result = FiltreUsuaris.USUARIOHISTORICOROLSI;
			}

		}
		
	

		return result;

	}

	
	


}
