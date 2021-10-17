package com.grup8.taskman.app.models.rol.rolDAOTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.grup8.taskman.app.domain.usuaris.Rol;
import com.grup8.taskman.app.services.rol.IRolService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
class RolTest {

	@Autowired
	IRolService rolService;
	
	
	@Test
	void findAllTest() {

		/* Com que qualsevol membre del grup pot modificar import.sql, dono per fet
		   que no sé quina quantitat de registren hi han, per tant, per tal de poder
		   fer la prova agafaré el tamany de la llista actual, afegiré un nou registre
		   i tornaré a mesurar la llista, si surt un més serà correcte.
		*/
				
		List<Rol>lista=rolService.findAll();
		int tamany=lista.size();
		System.out.println("Tamany: " + tamany);
		Rol rol=new Rol();
		rol.setCodigo("ROLE_OTHER");
		rol.setNombre("Altre rol");
		rolService.save(rol);		
		assertEquals(tamany+1, rolService.findAll().size());	
		 
		
	}
	
	@Test
	void findByIdTest() {
		
		/* Com que no sabem les modificacions de la base de dades el que farem serà
		agafar les dades del primer registre mitjançant la llista que ens torna tots
		i desprès realitzar la busqueda per l'id d'aquest mateix registre per comparar.
		*/
		
		List<Rol> lista=rolService.findAll();
		
		// Si a la llista no hi ha cap creem un si no agafem el primer
		
		Rol rol=new Rol();
		if(lista.size()==0) {
			
			rol.setCodigo("codigo");
			rol.setNombre("nombre");
			rolService.save(rol); // A la base de dades autómaticament el seu id tindrà el valor 1 
			rol.setId(new Long(1));
			
		}else {
			
			rol=lista.get(0);
		}
		
		/*
		 * Ara ja tenim el rol i l'id que voldrem buscar i comparar
		 * */
		
		Rol rolBuscado=rolService.findById(rol.getId());
		assertEquals(rol.getCodigo(), rolBuscado.getCodigo());// Tant codigo com nombre son unique a la base de dades per tant si coincideix està bé
	}

}
