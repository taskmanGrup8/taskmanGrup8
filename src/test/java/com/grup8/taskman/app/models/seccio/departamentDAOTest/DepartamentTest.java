package com.grup8.taskman.app.models.seccio.departamentDAOTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.grup8.taskman.app.domain.departaments.Departament;
import com.grup8.taskman.app.services.departament.IDepartamentService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
class DepartamentTest {

	@Autowired
	IDepartamentService seccioService;

	/*
	 * public Seccio findByCodi(String codi); public Seccio
	 * findByNom(String nom); public void delete(Seccio seccio);
	 */

	@Test
	void saveTest() {

		// Creem una nova secció i la guardem

		Departament seccio = getSeccio();
		seccioService.save(seccio);

		// Ara la recuperem buscant per codi (codi i nom són únics a aquesta taula)

		Departament seccioBuscada = seccioService.findByCodi(seccio.getCodigo());

		// Si el nom de seccio i seccioBuscada coincideixen llavos s'ha guardat
		// correctament.

		assertEquals(seccio.getNombre(), seccioBuscada.getNombre());

	}

	@Test
	void findAllTest() {

		// Primer eliminem tots els registres de la base de dades

		seccioService.deleteAll();

		// Afegim un registre

		seccioService.save(getSeccio());

		// Ara el tamany de la llista ha de ser 1

		assertEquals(1, seccioService.findAll().size());

	}

	@Test
	void findByIdTest() {

		// Primer eliminem tots els registres de la base de dades

		seccioService.deleteAll();

		// Afegim un registre d'aquesta manera sabem que nomès hi ha un.

		seccioService.save(getSeccio());

		// Ara busquem tots els elements i guardem el primer

		Departament seccio = seccioService.findAll().get(0);
		
		// Realitzem la busqueda per l'id guardat

		Departament seccioBuscada = seccioService.findById(seccio.getId());

		// Comprobem que tenen el mateix codi o el mateix nom ja que són únics a la base
		// de dades

		assertEquals(seccio.getCodigo(), seccioBuscada.getCodigo());
	}
	
	
	@Test
	void findByCodiTest() {

		// Primer eliminem tots els registres de la base de dades

		seccioService.deleteAll();

		// Afegim un registre d'aquesta manera sabem que nomès hi ha un.

		seccioService.save(getSeccio());

		// Ara busquem tots els elements i guardem el primer

		Departament seccio = seccioService.findAll().get(0);
		
		// Realitzem la busqueda per l'id guardat

		Departament seccioBuscada = seccioService.findByCodi(seccio.getCodigo());

		// Comprobem que tenen el mateix nom ja que són únics a la base
		// de dades

		assertEquals(seccio.getNombre(), seccioBuscada.getNombre());
	}
	
	
	@Test
	void findByNomTest() {

		// Primer eliminem tots els registres de la base de dades

		seccioService.deleteAll();

		// Afegim un registre d'aquesta manera sabem que nomès hi ha un.

		seccioService.save(getSeccio());

		// Ara busquem tots els elements i guardem el primer

		Departament seccio = seccioService.findAll().get(0);
		
		// Realitzem la busqueda per l'id guardat

		Departament seccioBuscada = seccioService.findByNom(seccio.getNombre());

		// Comprobem que tenen el mateix codi ja que són únics a la base
		// de dades

		assertEquals(seccio.getCodigo(), seccioBuscada.getCodigo());
	}
	
	@Test
	void deleteTest() {

		// Primer eliminem tots els registres de la base de dades

		seccioService.deleteAll();

		// Afegim un registre d'aquesta manera sabem que nomès hi ha un.

		seccioService.save(getSeccio());

		// Ara busquem tots els elements per saber el tamany de la llista

		int tamany = seccioService.findAll().size();
		
		// Guardem el primer element que sabem que existeix perquè com a mínim hem creat un-
		
		Departament seccio=seccioService.findAll().get(0);
		
		// Esborrem l'element guardat
		
		seccioService.delete(seccio);

		// comprobem que el tamany de la llista ha disminuit en un

		assertEquals(tamany-1, seccioService.findAll().size());
	}
	
	@Test
	void deleteAllTest() {
		
		// Afegim diverses seccions a la base de dades
		
		Departament seccio = new Departament();
		seccio.setCodigo("PRD");
		seccio.setNombre("PRODUCCIÓN");
		
		Departament seccio2 = new Departament();
		seccio2.setCodigo("VER");
		seccio2.setNombre("VERIFICACIÓN");
		
		Departament seccio3 = new Departament();
		seccio3.setCodigo("CON");
		seccio3.setNombre("CONTABILIDAD");
		
		seccioService.save(seccio);
		seccioService.save(seccio2);
		seccioService.save(seccio3);
		
		// Esborrem tot i ens assegurem que el tamany de la llista es 0
		
		seccioService.deleteAll();
		assertEquals(0, seccioService.findAll().size());
		
		
	}
	
	
	
	
	
	
	
	
	

	public Departament getSeccio() {

		Departament seccio = new Departament();
		seccio.setCodigo("ALM");
		seccio.setNombre("ALMACÉN");
		return seccio;
	}

}
