package com.egg.tpfinal.servicios;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egg.tpfinal.entidades.*;
import com.egg.tpfinal.repositorios.DeveloperRepository;
import com.egg.tpfinal.repositorios.FotoRepository;

@Service
public class DeveloperService {

	@Autowired
	private DeveloperRepository DevRepo;

	@Autowired
	private UsuarioService userServi;

	@Autowired
	private FotoRepository fotoRepo;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void guardarDeveloper(Developer dev, Usuario usuario, String tel, String nombre, String apellido, Foto foto,
			List<Tecnologias> tec) {
		dev.setNombre(nombre);
		dev.setApellido(apellido);
		dev.setAlta(true);
		dev.setTecnologias(tec);// ver como se arma en el front
		dev.setTelefono(tel);
		dev.setUsuario(usuario);
		dev.setFoto(foto);
		if (foto != null) {
			fotoRepo.save(foto);
		}
//		userRepo.save(dev.getUsuario());
		userServi.saveUsuario(usuario);
		DevRepo.save(dev);
	}

	@Transactional
	public void borrarDeveloper(Long ID) {
		EditarDeveloperActivo(ID);
	}

	@Transactional
	public void editarDeveloper(Long ID, Usuario usuario, String nombre, String apellido, String tel, Foto foto,
			List<Tecnologias> tec) {
		Developer dev = getDeveloper(ID);
		guardarDeveloper(dev, usuario, nombre, apellido, tel, foto, tec);
	}

	@Transactional
	public void crearDeveloper(Usuario usuario, String nombre, String apellido, String tel, Foto foto,
			List<Tecnologias> tec) throws Exception {
		Developer dev = DevRepo.buscarPorEmail(usuario.getEmail());
		if (dev == null) {
			dev = new Developer();
			guardarDeveloper(dev, usuario, tel, nombre, apellido, foto, tec);
		} else {
			throw new Exception("el email ya existe");
		}
	}

	@Transactional(readOnly = true)
	public List<Developer> listarTodosDeveloper() {
		return DevRepo.findAll();
	}

	@Transactional(readOnly = true)
	public List<Developer> listarDeveloperActivos() {
		return DevRepo.listarDeveloperActivos();
	}

	@Transactional
	public void EditarDeveloperActivo(Long ID) {
		Developer dev = getDeveloper(ID);
		if (dev != null) {
			dev.setAlta(!dev.getAlta());
			DevRepo.save(dev);
		}
	}

	@Transactional(readOnly = true)
	public Developer getDeveloper(Long ID) {
		Optional<Developer> d = DevRepo.findById(ID);
		return d.get();
	}

	@Transactional
	public void saveDeveloper(Developer developer) {
		DevRepo.save(developer);
	}

	@Transactional(readOnly = true)
	public Developer getDeveloperporIdUser(Long idUser) {
		return DevRepo.buscarPorIdUsuario(idUser);
	}

}
