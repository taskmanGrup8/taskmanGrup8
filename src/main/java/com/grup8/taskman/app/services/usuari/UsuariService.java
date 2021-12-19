package com.grup8.taskman.app.services.usuari;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup8.taskman.app.domain.usuaris.Permiso;
import com.grup8.taskman.app.domain.usuaris.Rol;
import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.repository.usuari.IUsuariDao;

/**
 * Classe que implementa la interficie IUsuariService i que retorna els
 * resultats del DAO. Està marcada com Service per poder ser injectada
 * posteriorment. Els diferents mètodes estan anotats amb @Transactional per
 * indicar que fan accions sobre la base de dades. Les que tenen l'atribut
 * readOnly=true indiquen que nomès són de consulta.
 * Cadascú dels mètodes que fa una consulta sense rol ha de tenir cura si 
 * qui fa la comanda té el rol taskman o no perquè quan per exemple fa una consulta de
 * tots els usuaris, si no tens rol taskman llavors no pots veure els que tenen
 * rol superior a administrador.
 * 
 * També implementa l'interface UserDetailsService necessària pel login
 * 
 * @author Sergio Esteban Gutiérrez
 * @version 1.0.1
 *
 */

@Service
public class UsuariService implements IUsuariService, UserDetailsService {

	// Injectem la interfície del repository
	@Autowired
	IUsuariDao usuariDao;

	/**
	 * Mètode que busca tots els usuaris de la taula usuaris.
	 * 
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna els elements trobats en un Page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findAll(Pageable pageable) {
		
		if(Usuari.USUARIAUTENTICAT.getRol().getId()==Rol.TASKMAN) {

			return usuariDao.findAll(pageable);
			
		}else {
			
			return usuariDao.findAllAdmin(pageable);
		}
		
		
	}

	/**
	 * Mètode que guarda/actualitza l'usuari passat pel paràmetre.
	 * 
	 * @param usuari Usuari que volem guardar
	 * @return Retorna l'usuari guardat
	 */
	@Override
	@Transactional
	public Usuari save(Usuari usuari) {

		return usuariDao.save(usuari);
	}

	/**
	 * Mètode que busca a la taula usuaris els registres que el seu camp nombre
	 * comenci per nom i que el seu camp apellidos comenci per cognoms.
	 * 
	 * @param nom      String per la qual ha de començar el camp nombre que volem
	 *                 buscar
	 * @param cognoms  String per la qual ha de començar el camp apellidos que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByNombreStartsWithAndApellidosStartsWith(String nombre, String apellidos,
			Pageable pageable) {
		
		if(Usuari.USUARIAUTENTICAT.getRol().getId()==Rol.TASKMAN) {
			
			return usuariDao.findByNombreStartsWithAndApellidosStartsWith(nombre, apellidos, pageable);
			
		}else {

			return usuariDao.findByNombreStartsWithAndApellidosStartsWithAdmin(nombre, apellidos, pageable);
		
		}

	}

	/**
	 * Mètode que busca a la taula usuaris el registre que el seu camp dni sigui
	 * "dni".
	 * 
	 * @param dni Dni de l'usuari que volem trobar
	 * @return Retorna l'usuari trobat o null si no el troba.
	 */
	@Override
	@Transactional(readOnly = true)
	public Usuari findByDni(String dni) {

		return usuariDao.findByDni(dni).orElse(null);

	}

	/**
	 * Mètode que busca a la taula usuaris els que tenen id igual al passat per
	 * paràmetre.
	 * 
	 * @param id Id de l'usuari que volem buscar
	 * @return Retorna l'usuari trobat o null si no en troba cap.
	 */
	@Override
	@Transactional(readOnly = true)
	public Usuari findById(Long id) {

		return usuariDao.findById(id).orElse(null);

	}

	/**
	 * Mètode que busca a la taula usuaris els registres dels usuaris actius.
	 * 
	 * @param actiu    Boleà que indica si l'usuari està en actiu o no
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByActivo(boolean activo, Pageable pageable) {
		
		if(Usuari.USUARIAUTENTICAT.getRol().getId()==Rol.TASKMAN) {
			
			return usuariDao.findByActivo(activo, pageable);
			
		}else {		

			return usuariDao.findByActivoAdmin(activo, pageable);
		}
	}

	/**
	 * Mètode que elimina tots els usuaris de la taula usuaris
	 */
	@Override
	@Transactional
	public void deleteAll() {

		usuariDao.deleteAll();

	}

	/**
	 * Mètode que busca a la taula usuaris els registres que el seu camp nombre
	 * comenci per nom.
	 * 
	 * @param nom      String per la qual ha de començar el camp nombre que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByNombreStartsWith(String nom, Pageable pageable) {

		if(Usuari.USUARIAUTENTICAT.getRol().getId()==Rol.TASKMAN) {
			return usuariDao.findByNombreStartsWith(nom, pageable);
		}else {
			return usuariDao.findByNombreStartsWithAdmin(nom, pageable);
		}
	}

	/**
	 * Mètode que busca a la taula usuaris que el seu camp apellidos comenci per
	 * cognoms.
	 * 
	 * @param cognoms  String per la qual ha de començar el camp apellidos que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByApellidosStartsWith(String cognoms, Pageable pageable) {
		
		if(Usuari.USUARIAUTENTICAT.getRol().getId()==Rol.TASKMAN) {
			return usuariDao.findByApellidosStartsWith(cognoms, pageable);
		}else {
			return usuariDao.findByApellidosStartsWithAdmin(cognoms, pageable);
		}
	}

	/**
	 * Mètode que busca a la taula usuaris els registres dels usuaris actius amb el
	 * rol amb id passat per paràmetre.
	 * 
	 * @param actiu    Boleà que indica si l'usuari està en actiu o no
	 * @param rol      Id del rol que volem buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByActivoAndRol(boolean activo, Long rol, Pageable pageable) {

		return usuariDao.findByActivoAndRol(activo, rol, pageable);
	}

	/**
	 * Mètode que busca a la taula usuaris els registres dels usuaris actius que el
	 * seu camp nombre comenci per nom.
	 * 
	 * @param actiu    Boleà que indica si l'usuari està en actiu o no
	 * @param nom      String per la qual ha de començar el camp nombre que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByActivoAndNombreStartsWith(boolean activo, String nom, Pageable pageable) {

		if(Usuari.USUARIAUTENTICAT.getRol().getId()==Rol.TASKMAN) {
			return usuariDao.findByActivoAndNombreStartsWith(activo, nom, pageable);
		}else {
			return usuariDao.findByActivoAndNombreStartsWithAdmin(activo, nom, pageable);
		}
	}

	/**
	 * Mètode que busca a la taula usuaris els registres dels usuaris actius amb el
	 * rol amb id passat per paràmetre, que el seu camp nombre comenci per nom.
	 * 
	 * @param actiu    Boleà que indica si l'usuari està en actiu o no
	 * @param rol      Id del rol que volem buscar
	 * @param nom      String per la qual ha de començar el camp nombre que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByActivoAndRolAndNombreStartsWith(boolean activo, Long rol, String nom, Pageable pageable) {

		return usuariDao.findByActivoAndRolAndNombreStartsWith(activo, rol, nom, pageable);
	}

	/**
	 * Mètode que busca a la taula usuaris els registres dels usuaris actius que el
	 * seu camp apellidos comenci per cognoms.
	 * 
	 * @param actiu    Boleà que indica si l'usuari està en actiu o no
	 * @param cognoms  String per la qual ha de començar el camp apellidos que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByActivoAndApellidosStartsWith(boolean activo, String cognoms, Pageable pageable) {

		if(Usuari.USUARIAUTENTICAT.getRol().getId()==Rol.TASKMAN) {
			return usuariDao.findByActivoAndApellidosStartsWith(activo, cognoms, pageable);
		}else {
			return usuariDao.findByActivoAndApellidosStartsWithAdmin(activo, cognoms, pageable);
		}
	}

	/**
	 * Mètode que busca a la taula usuaris els registres dels usuaris actius amb el
	 * rol amb id passat per paràmetre, que el seu camp apellidos comenci per
	 * cognoms.
	 * 
	 * @param actiu    Boleà que indica si l'usuari està en actiu o no
	 * @param rol      Id del rol que volem buscar
	 * @param cognoms  String per la qual ha de començar el camp apellidos que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByActivoAndRolAndApellidosStartsWith(boolean activo, Long rol, String cognoms,
			Pageable pageable) {

		return usuariDao.findByActivoAndRolAndApellidosStartsWith(activo, rol, cognoms, pageable);
	}

	/**
	 * Mètode que busca a la taula usuaris els registres dels usuaris actius que el
	 * seu camp dni comenci per dni.
	 * 
	 * @param actiu    Boleà que indica si l'usuari està en actiu o no
	 * @param dni      String per la qual ha de començar el camp dni que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByActivoAndDniStartsWith(boolean activo, String dni, Pageable pageable) {

		if(Usuari.USUARIAUTENTICAT.getRol().getId()==Rol.TASKMAN) {
			return usuariDao.findByActivoAndDniStartsWith(activo, dni, pageable);
		}else {
			return usuariDao.findByActivoAndDniStartsWithAdmin(activo, dni, pageable);
		}
	}

	/**
	 * Mètode que busca a la taula usuaris els registres dels usuaris actius amb el
	 * rol amb id passat per paràmetre que el seu camp dni comenci per dni.
	 * 
	 * @param actiu    Boleà que indica si l'usuari està en actiu o no
	 * @param rol      Id del rol que volem buscar
	 * @param dni      String per la qual ha de començar el camp dni que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByActivoAndRolAndDniStartsWith(boolean activo, Long rol, String dni, Pageable pageable) {

		return usuariDao.findByActivoAndRolAndDniStartsWith(activo, rol, dni, pageable);
	}

	/**
	 * Mètode que busca a la taula usuaris els registres dels usuaris actius que el
	 * seu camp nombre comenci per nom i que el seu camp apellidos comenci per
	 * cognoms.
	 * 
	 * @param actiu    Boleà que indica si l'usuari està en actiu o no
	 * @param nom      String per la qual ha de començar el camp nombre que volem
	 *                 buscar
	 * @param cognoms  String per la qual ha de començar el camp apellidos que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByActivoAndNombreStartsWithAndApellidosStartsWith(boolean activo, String nom,
			String cognoms, Pageable pageable) {

		if(Usuari.USUARIAUTENTICAT.getRol().getId()==Rol.TASKMAN) {
			return usuariDao.findByActivoAndNombreStartsWithAndApellidosStartsWith(activo, nom, cognoms, pageable);
		}else {
			return usuariDao.findByActivoAndNombreStartsWithAndApellidosStartsWithAdmin(activo, nom, cognoms, pageable);
		}
	}

	/**
	 * Mètode que busca a la taula usuaris els registres dels usuaris actius amb el
	 * rol amb id passat per paràmetre, que el seu camp nombre comenci per nom i que
	 * el seu camp apellidos comenci per cognoms.
	 * 
	 * @param actiu    Boleà que indica si l'usuari està en actiu o no
	 * @param rol      Id del rol que volem buscar
	 * @param nom      String per la qual ha de començar el camp nombre que volem
	 *                 buscar
	 * @param cognoms  String per la qual ha de començar el camp apellidos que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByActivoAndRolAndNombreStartsWithAndApellidosStartsWith(boolean activo, Long rol,
			String nom, String cognoms, Pageable pageable) {

		return usuariDao.findByActivoAndRolAndNombreStartsWithAndApellidosStartsWith(activo, rol, nom, cognoms,
				pageable);
	}

	/**
	 * Mètode que busca a la taula usuaris els registres dels usuaris actius que el
	 * seu camp apellidos comenci per cognoms i que el seu camp dni comenci per dni.
	 * 
	 * @param actiu    Boleà que indica si l'usuari està en actiu o no
	 * @param cognoms  String per la qual ha de començar el camp apellidos que volem
	 *                 buscar
	 * @param dni      String per la qual ha de començar el camp dni que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByActivoAndApellidosStartsWithAndDniStartsWith(boolean activo, String cognoms, String dni,
			Pageable pageable) {

		if(Usuari.USUARIAUTENTICAT.getRol().getId()==Rol.TASKMAN) {
			return usuariDao.findByActivoAndApellidosStartsWithAndDniStartsWith(activo, cognoms, dni, pageable);
		}else {
			return usuariDao.findByActivoAndApellidosStartsWithAndDniStartsWithAdmin(activo, cognoms, dni, pageable);
		}
	}

	/**
	 * Mètode que busca a la taula usuaris els registres dels usuaris actius amb el
	 * rol amb id passat per paràmetre, que el seu camp apellidos comenci per
	 * cognoms i que el seu camp dni comenci per dni.
	 * 
	 * @param actiu    Boleà que indica si l'usuari està en actiu o no
	 * @param rol      Id del rol que volem buscar
	 * @param cognoms  String per la qual ha de començar el camp apellidos que volem
	 *                 buscar
	 * @param dni      String per la qual ha de començar el camp dni que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByActivoAndRolAndApellidosStartsWithAndDniStartsWith(boolean activo, Long rol,
			String cognoms, String dni, Pageable pageable) {

		return usuariDao.findByActivoAndRolAndApellidosStartsWithAndDniStartsWith(activo, rol, cognoms, dni, pageable);
	}

	/**
	 * Mètode que busca a la taula usuaris els registres dels usuaris actius que el
	 * seu camp nombre comenci per nom i que el seu camp dni comenci per dni.
	 * 
	 * @param actiu    Boleà que indica si l'usuari està en actiu o no
	 * @param nom      String per la qual ha de començar el camp nombre que volem
	 *                 buscar
	 * @param dni      String per la qual ha de començar el camp dni que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByActivoAndNombreStartsWithAndDniStartsWith(boolean activo, String nom, String dni,
			Pageable pageable) {

		if(Usuari.USUARIAUTENTICAT.getRol().getId()==Rol.TASKMAN) {
			return usuariDao.findByActivoAndNombreStartsWithAndDniStartsWith(activo, nom, dni, pageable);
		}else {
			return usuariDao.findByActivoAndNombreStartsWithAndDniStartsWithAdmin(activo, nom, dni, pageable);
		}
	}

	/**
	 * Mètode que busca a la taula usuaris els registres dels usuaris actius amb el
	 * rol amb id passat per paràmetre, que el seu camp nombre comenci per nom i que
	 * el seu camp dni comenci per dni.
	 * 
	 * @param actiu    Boleà que indica si l'usuari està en actiu o no
	 * @param rol      Id del rol que volem buscar
	 * @param nom      String per la qual ha de començar el camp nombre que volem
	 *                 buscar
	 * @param dni      String per la qual ha de començar el camp dni que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByActivoAndRolAndNombreStartsWithAndDniStartsWith(boolean activo, Long rol, String nom,
			String dni, Pageable pageable) {

		return usuariDao.findByActivoAndRolAndNombreStartsWithAndDniStartsWith(activo, rol, nom, dni, pageable);
	}

	/**
	 * Mètode que busca a la taula usuaris els registres dels usuaris actius que el
	 * seu camp nombre comenci per nom, que el seu camp apellidos comenci per
	 * cognoms i que el seu camp dni comenci per dni.
	 * 
	 * @param actiu    Boleà que indica si l'usuari està en actiu o no
	 * @param nom      String per la qual ha de començar el camp nombre que volem
	 *                 buscar
	 * @param cognoms  String per la qual ha de començar el camp apellidos que volem
	 *                 buscar
	 * @param dni      String per la qual ha de començar el camp dni que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByActivoAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(boolean activo,
			String nom, String cognoms, String dni, Pageable pageable) {

		if(Usuari.USUARIAUTENTICAT.getRol().getId()==Rol.TASKMAN) {
			return usuariDao.findByActivoAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(activo, nom, cognoms,
				dni, pageable);
		}else {
			return usuariDao.findByActivoAndNombreStartsWithAndApellidosStartsWithAndDniStartsWithAdmin(activo, nom, cognoms,
					dni, pageable);
		}
	}

	/**
	 * Mètode que busca a la taula usuaris els registres dels usuaris actius amb el
	 * rol amb id passat per paràmetre, que el seu camp nombre comenci per nom, que
	 * el seu camp apellidos comenci per cognoms i que el seu camp dni comenci per
	 * dni.
	 * 
	 * @param actiu    Boleà que indica si l'usuari està en actiu o no
	 * @param rol      Id del rol que volem buscar
	 * @param nom      String per la qual ha de començar el camp nombre que volem
	 *                 buscar
	 * @param cognoms  String per la qual ha de començar el camp apellidos que volem
	 *                 buscar
	 * @param dni      String per la qual ha de començar el camp dni que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByActivoAndRolAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(boolean activo,
			Long rol, String nom, String cognoms, String dni, Pageable pageable) {

		return usuariDao.findByActivoAndRolAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(activo, rol, nom,
				cognoms, dni, pageable);
	}

	/**
	 * Mètode que busca a la taula usuaris els registres amb el rol amb id passat
	 * per paràmetre.
	 * 
	 * @param rol      Id del rol que volem buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByRol(Long rol, Pageable pageable) {
		return usuariDao.findByRol(rol, pageable);
	}

	/**
	 * Mètode que busca a la taula usuaris els registres amb el rol amb id passat
	 * per paràmetre, que el seu camp nombre comenci per nom.
	 * 
	 * @param rol      Id del rol que volem buscar
	 * @param nom      String per la qual ha de començar el camp nombre que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByRolAndNombreStartsWith(Long rol, String nom, Pageable pageable) {
		return usuariDao.findByRolAndNombreStartsWith(rol, nom, pageable);
	}

	/**
	 * Mètode que busca a la taula usuaris els registres amb el rol amb id passat
	 * per paràmetre i que el seu camp apellidos comenci per cognoms.
	 * 
	 * @param rol      Id del rol que volem buscar
	 * @param cognoms  String per la qual ha de començar el camp apellidos que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByRolAndApellidosStartsWith(Long rol, String cognoms, Pageable pageable) {
		return usuariDao.findByRolAndApellidosStartsWith(rol, cognoms, pageable);
	}

	/**
	 * Mètode que busca a la taula usuaris els registres amb el rol amb id passat
	 * per paràmetre i que el seu camp dni comenci per dni.
	 * 
	 * @param rol      Id del rol que volem buscar
	 * @param dni      String per la qual ha de començar el camp dni que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByRolAndDniStartsWith(Long rol, String dni, Pageable pageable) {

		return usuariDao.findByRolAndDniStartsWith(rol, dni, pageable);
	}

	/**
	 * Mètode que busca a la taula usuaris els registres que el seu camp dni comenci
	 * per dni.
	 * 
	 * @param dni      String per la qual ha de començar el camp dni que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByDniStartsWith(String dni, Pageable pageable) {
		
		if(Usuari.USUARIAUTENTICAT.getRol().getId()==Rol.TASKMAN) {
			return usuariDao.findByDniStartsWith(dni, pageable);
		}else {
			return usuariDao.findByDniStartsWithAdmin(dni, pageable);
		}
	}

	/**
	 * Mètode que busca a la taula usuaris els registres amb el rol amb id passat
	 * per paràmetre, que el seu camp nombre comenci per nom i que el seu camp
	 * apellidos comenci per cognoms.
	 * 
	 * @param rol      Id del rol que volem buscar
	 * @param nom      String per la qual ha de començar el camp nombre que volem
	 *                 buscar
	 * @param cognoms  String per la qual ha de començar el camp apellidos que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByRolAndNombreStartsWithAndApellidosStartsWith(Long rol, String nom, String cognoms,
			Pageable pageable) {
		return usuariDao.findByRolAndNombreStartsWithAndApellidosStartsWith(rol, nom, cognoms, pageable);
	}

	/**
	 * Mètode que busca a la taula usuaris els registres amb el rol amb id passat
	 * per paràmetre, que el seu camp nombre comenci per nom i que el seu camp dni
	 * comenci per dni.
	 * 
	 * @param rol      Id del rol que volem buscar
	 * @param nom      String per la qual ha de començar el camp nombre que volem
	 *                 buscar
	 * @param dni      String per la qual ha de començar el camp dni que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByRolAndNombreStartsWithAndDniStartsWith(Long rol, String dni, String nom,
			Pageable pageable) {
		return usuariDao.findByRolAndNombreStartsWithAndDniStartsWith(rol, nom, dni, pageable);
	}

	/**
	 * Mètode que busca a la taula usuaris els registres que el seu camp nombre
	 * comenci per nom i que el seu camp dni comenci per dni.
	 * 
	 * @param nom      String per la qual ha de començar el camp nombre que volem
	 *                 buscar
	 * @param dni      String per la qual ha de començar el camp dni que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByNombreStartsWithAndDniStartsWith(String nombre, String dni, Pageable pageable) {

		if(Usuari.USUARIAUTENTICAT.getRol().getId()==Rol.TASKMAN) {
			return usuariDao.findByNombreStartsWithAndDniStartsWith(nombre, dni, pageable);
		}else {
			return usuariDao.findByNombreStartsWithAndDniStartsWithAdmin(nombre, dni, pageable);
		}
	}

	/**
	 * Mètode que busca a la taula usuaris els registres que el seu camp apellidos
	 * comenci per cognoms i que el seu camp dni comenci per dni.
	 * 
	 * @param cognoms  String per la qual ha de començar el camp apellidos que volem
	 *                 buscar
	 * @param dni      String per la qual ha de començar el camp dni que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByApellidosStartsWithAndDniStartsWith(String apellido, String dni, Pageable pageable) {

		if(Usuari.USUARIAUTENTICAT.getRol().getId()==Rol.TASKMAN) {
			return usuariDao.findByApellidosStartsWithAndDniStartsWith(apellido, dni, pageable);
		}else {
			return usuariDao.findByApellidosStartsWithAndDniStartsWithAdmin(apellido, dni, pageable);
		}
	}

	/**
	 * Mètode que busca a la taula usuaris els registres que el seu camp nombre
	 * comenci per nom, que el seu camp apellidos comenci per cognoms i que el seu
	 * camp dni comenci per dni.
	 * 
	 * @param nom      String per la qual ha de començar el camp nombre que volem
	 *                 buscar
	 * @param cognoms  String per la qual ha de començar el camp apellidos que volem
	 *                 buscar
	 * @param dni      String per la qual ha de començar el camp dni que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Usuari> findByNombreStartsWithAndApellidosStartsWithAndDniStartsWith(String nombre, String apellidos,
			String dni, Pageable pageable) {

		if(Usuari.USUARIAUTENTICAT.getRol().getId()==Rol.TASKMAN) {
			return usuariDao.findByNombreStartsWithAndApellidosStartsWithAndDniStartsWith(nombre, apellidos, dni, pageable);
		}else {
			return usuariDao.findByNombreStartsWithAndApellidosStartsWithAndDniStartsWithAdmin(nombre, apellidos, dni, pageable);
		}
	}

	/**
	 * Mètode que busca a la taula usuaris els registres amb el rol amb id passat
	 * per paràmetre, que el seu camp apellidos comenci per cognoms i que el seu
	 * camp dni comenci per dni.
	 * 
	 * @param rol      Id del rol que volem buscar
	 * @param cognoms  String per la qual ha de començar el camp apellidos que volem
	 *                 buscar
	 * @param dni      String per la qual ha de començar el camp dni que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	public Page<Usuari> findByRolAndApellidosStartsWithAndDniStartsWith(Long rol, String cognoms, String dni,
			Pageable pageable) {

		return usuariDao.findByRolAndApellidosStartsWithAndDniStartsWith(rol, cognoms, dni, pageable);
	}	
	

	/**
	 * Mètode que busca a la taula usuaris els registres amb el rol amb id passat
	 * per paràmetre, que el seu camp nombre comenci per nom, que el seu camp
	 * apellidos comenci per cognoms, que el seu camp dni comenci per dni.
	 * 
	 * @param rol      Id del rol que volem buscar
	 * @param nom      String per la qual ha de començar el camp nombre que volem
	 *                 buscar
	 * @param cognoms  String per la qual ha de començar el camp apellidos que volem
	 *                 buscar
	 * @param dni      String per la qual ha de començar el camp dni que volem
	 *                 buscar
	 * @param pageable Pageable que farem servir per realitzar la paginació.
	 * @return Retorna un page amb els elements trobats.
	 */
	@Override
	public Page<Usuari> findByRolAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(Long rol, String nom,
			String cognoms, String dni, Pageable pageable) {

		return usuariDao.findByRolAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(rol, nom, cognoms, dni,
				pageable);
	}

	/**
	 * Fem override sobre el mètode loadUserByUsername de la interfície UserDetailsService
	 * que carrega l'usuari amb els seus permisos que seran gestionats a la classe de configuració de seguretat.
	 * @param username Username de l'usuari que buscarem.
	 * @return Retornem una instancia de la classe User amb l'username, el password, si encara està actiu i les autoritzacions.
	 * @throws UsernameNotFoundException
	 */
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// Busquem l'usuari
		Usuari usuari = usuariDao.findByUsername(username).orElse(null);

		// Si no el trobem llencem l'error
		if (usuari == null)
			throw new UsernameNotFoundException("Username " + username + " no existeix al sistema");

		// Creem una llista de GrantedAuthority que dona les diferents autoritzacions
		List<GrantedAuthority> permisos = new ArrayList<GrantedAuthority>();
		
		// Per cada permis a la llista de permisos de l'usuari creem una nova autorització.
		for (Permiso permiso : usuari.getPermisos()) {

			permisos.add(new SimpleGrantedAuthority(permiso.getAuthority()));
		}
		
		// Si no haguessim creat cap`llavors llencem un error perquè no hem assignat rol
		if(permisos.isEmpty())throw new UsernameNotFoundException("Error: " + username + " no té rol assignat");

		// Retornem una instancia de la classe User amb l'username, el password, si encara està actiu i les autoritzacions.
		return new User(username, usuari.getPassword(), usuari.isActivo(), true, true, true, permisos);

	}

	/**
	 * Mètode que busca a la base de dades un usuari mitjançant el seu username
	 * @param username Username a buscar a la base de dades
	 * @return Retorna l'usuari trobat o null si no en troba cap
	 */
	@Override
	@Transactional(readOnly = true)
	public Usuari findByUsername(String username) {
		
		return usuariDao.findByUsername(username).orElse(null);
	}

	@Override
	public Usuari findByNotificacionActual(Long notificacion) {
		
		return usuariDao.findByNotificacionActual(notificacion).orElse(null);
	}

}
