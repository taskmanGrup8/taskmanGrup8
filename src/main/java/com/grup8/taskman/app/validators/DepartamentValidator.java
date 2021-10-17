package com.grup8.taskman.app.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.grup8.taskman.app.domain.departaments.Departament;
import com.grup8.taskman.app.services.departament.IDepartamentService;

@Component
public class DepartamentValidator implements Validator {

	@Autowired
	IDepartamentService departamentService;
	
	@Override
	public boolean supports(Class<?> clazz) {
				
		return Departament.class.isAssignableFrom(clazz) ;
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Departament departament=(Departament) target;
		
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codigo",  "NotEmpty.departament.codigo");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre",  "NotEmpty.departament.nombre");	
		
		
		if(departament.getCodigo().length()>3) {			
					
			errors.rejectValue("codigo", "departament.codigo.maximo");
		}
		

		if(departament.getNombre().length()>20) {
			
			System.out.println("He entrado aquí");
			
			errors.rejectValue("nombre", "departament.nombre.maximo");
			
		}		
		
		Departament departamentPerCodi=departamentService.findByCodi(departament.getCodigo());
		
		if(departamentPerCodi!=null) {
			
			
			
			if(departament.getId()!=departamentPerCodi.getId()) {
				errors.rejectValue("codigo", "departament.codigoExistente");			
			}
			
		}	
		
		Departament departamentPerNom=departamentService.findByNom(departament.getNombre());
		
		if(departamentPerNom!=null) {
			
			if(departament.getId()!=departamentPerNom.getId()) {
				errors.rejectValue("nombre", "departament.nombreExistente");
			}
		
		}		
		
		
	}
	

}
