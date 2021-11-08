package com.grup8.taskman.app.services.imatges;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Classe dedicada a gestionar les imatges al sistema. Està marcada com service per poder-la injectar
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.0
 *
 */

@Service
public class ImatgesHandlerService implements IImatgesHandlerService {
	
	public static final String DIRECTORIO_UPLOADS="uploads"; // Directori on es guarden les imatges
	
	// NOTA: Més endavant, si dones temps serìa bo crear un sistema de configuració perquè l'usuari
	// superadministrador poguès canviar aquest directori
	
	
	@Override
	public String copy(MultipartFile archivo) throws IOException {
		
		// Abans de copiar donem un nom unique a l'arxiu per si qualsevol altre usuari volguès pujar
		// un arxiu amb el mateix nom, ens assegurem que no es repeteix.
		String uniqueFilename=UUID.randomUUID().toString()+"_" + archivo.getOriginalFilename();
		
		// Obtenim el path absolut de l'arxiu
		Path rootPath=getPath(uniqueFilename);
		
		// Copiem l'arxiu al path obtingut.
		Files.copy(archivo.getInputStream(), rootPath);	
		
		// Retornem el nom de l'arxiu
		return uniqueFilename;
	}

	@Override
	public boolean delete(String nombreArchivo) {
		
		// Obtenim el path absolut de l'arxiu
		Path rootPath=getPath(nombreArchivo);
		
		// Passem el path a File
		File archivo=rootPath.toFile();
		
		// Si l'arxiu existeix i es pot llegir llavors l'esborrem i retornem true
		if(archivo.exists() && archivo.canRead()) {
			
			if(archivo.delete())return true;
		}
		
		// Si no hem esborrat res retornem false
		return false;
	}
	
	/**
	 * Mètode que obté el path absolut de l'arxiu passat com paràmetre
	 * @param nombreArchivo Nom del arxiu del qual volem el path
	 * @return Retorna el Path de l'arxiu
	 */
	private Path getPath(String nombreArchivo) {
		
		return Paths.get(DIRECTORIO_UPLOADS).resolve(nombreArchivo).toAbsolutePath();
	}

}
