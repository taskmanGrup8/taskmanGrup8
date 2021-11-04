package com.grup8.taskman.app.services.imatges;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IImatgesHandlerService {
	
	public String copy(MultipartFile archivo) throws IOException;
	
	public boolean delete(String nombreArchivo);

}
