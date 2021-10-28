package com.grup8.taskman.app.services.usuari;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grup8.taskman.app.domain.usuaris.Usuari;
import com.grup8.taskman.app.repository.usuari.IUsuariDao;

@Service
public class UsuariService implements IUsuariService {
	
	@Autowired
	IUsuariDao usuariDao;


	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findAll(Pageable pageable) {
		
		return usuariDao.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Usuari usuari) {
		
		usuariDao.save(usuari);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByNombreStartsWithAndApellidosStartsWith(String nombre, String apellidos, Pageable pageable) {
		
		return usuariDao.findByNombreStartsWithAndApellidosStartsWith(nombre, apellidos, pageable);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Usuari findByDni(String dni) {
		
		return usuariDao.findByDni(dni).orElse(null);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Usuari findById(Long id) {
		
		return usuariDao.findById(id).orElse(null);
		
	}	

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByActivo(boolean activo, Pageable pageable) {
		
		return usuariDao.findByActivo(activo, pageable);
	}

	@Override
	@Transactional
	public void deleteAll() {
		
		usuariDao.deleteAll();
		
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByNombreStartsWith(String nom, Pageable pageable) {
		
		return usuariDao.findByNombreStartsWith(nom, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByApellidosStartsWith(String cognoms, Pageable pageable) {
		
		return usuariDao.findByApellidosStartsWith(cognoms, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByActivoAndRol(boolean activo, Long rol, Pageable pageable) {
		
		return usuariDao.findByActivoAndRol(activo, rol, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByActivoAndNombreStartsWith(boolean activo, String nom, Pageable pageable) {
		
		return usuariDao.findByActivoAndNombreStartsWith(activo, nom, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByActivoAndRolAndNombreStartsWith(boolean activo, Long rol, String nom, Pageable pageable) {
		
		return usuariDao.findByActivoAndRolAndNombreStartsWith(activo, rol, nom, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByActivoAndApellidosStartsWith(boolean activo, String cognoms, Pageable pageable) {
		
		return usuariDao.findByActivoAndApellidosStartsWith(activo, cognoms, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByActivoAndRolAndApellidosStartsWith(boolean activo, Long rol, String cognoms,
			Pageable pageable) {
		
		return usuariDao.findByActivoAndRolAndApellidosStartsWith(activo, rol, cognoms, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByActivoAndDniStartsWith(boolean activo, String dni, Pageable pageable) {
		
		return usuariDao.findByActivoAndDniStartsWith(activo, dni, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByActivoAndRolAndDniStartsWith(boolean activo, Long rol, String dni, Pageable pageable) {
		
		return usuariDao.findByActivoAndRolAndDniStartsWith(activo, rol, dni, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByActivoAndNombreStartsWithAndApellidosStartsWith(boolean activo, String nom,
			String cognoms, Pageable pageable) {
		
		return usuariDao.findByActivoAndNombreStartsWithAndApellidosStartsWith(activo, nom, cognoms, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByActivoAndRolAndNombreStartsWithAndApellidosStartsWith(boolean activo, Long rol,
			String nom, String cognoms, Pageable pageable) {
		
		return usuariDao.findByActivoAndRolAndNombreStartsWithAndApellidosStartsWith(activo, rol, nom, cognoms, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByActivoAndApellidosStartsWithAndDniStartsWith(boolean activo, String cognoms, String dni,
			Pageable pageable) {
		
		return usuariDao.findByActivoAndApellidosStartsWithAndDniStartsWith(activo, cognoms, dni, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByActivoAndRolAndApellidosStartsWithAndDniStartsWith(boolean activo, Long rol,
			String cognoms, String dni, Pageable pageable) {
		
		return usuariDao.findByActivoAndRolAndApellidosStartsWithAndDniStartsWith(activo, rol, cognoms, dni, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByActivoAndNombreStartsWithAndDniStartsWith(boolean activo, String nom, String dni,
			Pageable pageable) {
		
		return usuariDao.findByActivoAndNombreStartsWithAndDniStartsWith(activo, nom, dni, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByActivoAndRolAndNombreStartsWithAndDniStartsWith(boolean activo, Long rol, String nom,
			String dni, Pageable pageable) {
		
		return usuariDao.findByActivoAndRolAndNombreStartsWithAndDniStartsWith(activo, rol, nom, dni, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByActivoAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(boolean activo,
			String nom, String cognoms, String dni, Pageable pageable) {
		
		return usuariDao.findByActivoAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(activo, nom, cognoms, dni, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByActivoAndRolAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(boolean activo,
			Long rol, String nom, String cognoms, String dni, Pageable pageable) {
		
		return usuariDao.findByActivoAndRolAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(activo, rol, nom, cognoms, dni, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByRol(Long rol, Pageable pageable) {
		return usuariDao.findByRol(rol, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByRolAndNombreStartsWith(Long rol, String nom, Pageable pageable) {
		return usuariDao.findByRolAndNombreStartsWith(rol, nom, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByRolAndApellidosStartsWith(Long rol, String cognoms, Pageable pageable) {
		return usuariDao.findByRolAndApellidosStartsWith(rol, cognoms, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByRolAndDniStartsWith(Long rol, String dni, Pageable pageable) {
		
		return usuariDao.findByRolAndDniStartsWith(rol, dni, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByDniStartsWith(String dni, Pageable pageable) {
		return usuariDao.findByDniStartsWith(dni, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByRolAndNombreStartsWithAndApellidosStartsWith(Long rol, String nom, String cognoms,
			Pageable pageable) {
		return usuariDao.findByRolAndNombreStartsWithAndApellidosStartsWith(rol, nom, cognoms, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByRolAndNombreStartsWithAndDniStartsWith(Long rol, String dni, String nom,
			Pageable pageable) {
		return usuariDao.findByRolAndNombreStartsWithAndDniStartsWith(rol, nom, dni, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Usuari> findByNombreStartsWithAndDniStartsWith(String nombre, String dni, Pageable pageable) {
		
		return usuariDao.findByNombreStartsWithAndDniStartsWith(nombre, dni, pageable);
	}

	@Override
	public Page<Usuari> findByApellidosStartsWithAndDniStartsWith(String apellido, String dni, Pageable pageable) {
		
		return usuariDao.findByApellidosStartsWithAndDniStartsWith(apellido, dni, pageable);
	}

	@Override
	public Page<Usuari> findByNombreStartsWithAndApellidosStartsWithAndDniStartsWith(String nombre, String apellidos,
			String dni, Pageable pageable) {
		
		return usuariDao.findByNombreStartsWithAndApellidosStartsWithAndDniStartsWith(nombre, apellidos, dni, pageable);
	}

	@Override
	public Page<Usuari> findByRolAndApellidosStartsWithAndDniStartsWith(Long rol, String cognoms, String dni,
			Pageable pageable) {
		
		return usuariDao.findByRolAndApellidosStartsWithAndDniStartsWith(rol, cognoms, dni, pageable);
	}

	@Override
	public Page<Usuari> findByRolAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(Long rol,
			String nom, String cognoms, String dni, Pageable pageable) {
		
		return usuariDao.findByRolAndNombreStartsWithAndApellidosStartsWithAndDniStartsWith(rol, nom, cognoms, dni, pageable);
	}
	
	

	

}
