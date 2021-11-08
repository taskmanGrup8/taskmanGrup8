package com.grup8.taskman.app.services.imatges;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interfície que gestiona les imatges del sistema.
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */
public interface IImatgesHandlerService {
	
	/**
	 * Mètode que guarda l'arxiu passat per paràmetre
	 * @param archivo Arxiu que volem guardar
	 * @return Retorna el path de l'arxiu guardat
	 * @throws IOException Llença una excepció si no pot guardar l'arxiu
	 */
	public String copy(MultipartFile archivo) throws IOException;
	
	/**
	 * Mètode que esborra un arxiu del sistema
	 * @param nombreArchivo Nom del l'arxiu que volem eliminar
	 * @return Retorna true si s'ha pogut esborrar i fals en cas contrari
	 */
	public boolean delete(String nombreArchivo);

}
