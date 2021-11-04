package com.grup8.taskman.app.services.imatges;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImatgesHandlerService implements IImatgesHandlerService {
	
	public static final String DIRECTORIO_UPLOADS="uploads";	

	@Override
	public String copy(MultipartFile archivo) throws IOException {
		String uniqueFilename=UUID.randomUUID().toString()+"_" + archivo.getOriginalFilename();
		Path rootPath=getPath(uniqueFilename);	
		Files.copy(archivo.getInputStream(), rootPath);	
		return uniqueFilename;
	}

	@Override
	public boolean delete(String nombreArchivo) {
		Path rootPath=getPath(nombreArchivo);
		File archivo=rootPath.toFile();
		if(archivo.exists() && archivo.canRead()) {
			
			if(archivo.delete())return true;
		}
		
		return false;
	}
	
	public Path getPath(String nombreArchivo) {
		
		return Paths.get(DIRECTORIO_UPLOADS).resolve(nombreArchivo).toAbsolutePath();
	}

}
