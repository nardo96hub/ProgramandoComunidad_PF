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
			List<Tecnologias> tec) throws Exception  {
		
		validar(usuario,tel,nombre,apellido, foto,tec);
		
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
			List<Tecnologias> tec) throws Exception {
		Developer dev = getDeveloper(ID);
		validar(usuario,tel,nombre,apellido, foto,tec);
		guardarDeveloper(dev, usuario, nombre, apellido, tel, foto, tec);
	}

	@Transactional
	public void crearDeveloper(Usuario usuario, String nombre, String apellido, String tel, Foto foto,
			List<Tecnologias> tec) throws Exception {
		Developer dev = DevRepo.buscarPorEmail(usuario.getEmail());
		
		validar(usuario,tel,nombre,apellido, foto,tec);
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

	public void validar(Usuario usuario, String tel, String nombre, String apellido, Foto foto,
			List<Tecnologias> tec) throws Exception {
		//ACOMODAR ORDEN DE IF´SSSSSSSSSS
		if(usuario==null) { 
			throw new Exception("usuario no creado");
		}
		if(usuario.getEmail().isBlank()) {
			throw new Exception("email no válido");
		}
		if(usuario.getContrasena().isBlank()) {
			throw new Exception("contraseña no válida");
		}
		if(tel.isBlank() || tel.length()<6 /*|| tel.matches("a-zA-Z")*/) {
			throw new Exception("teléfono no válido (tamaño: mínimo 6 caracteres - sólo se admiten números)");
		}
		if(nombre.isBlank() || nombre.length()<2 || nombre.length()>20 /*|| !(nombre.matches("a-zA-Z"))*/) {
			throw new Exception("nombre no válido (tamaño: mínimo 2 caracteres / máximo= 20 caracteres - sólo se admiten letras)");
		}
		if(apellido.isBlank() || apellido.length()<2 || apellido.length()>20 /*|| !(apellido.matches("a-zA-Z"))*/) {
			throw new Exception("apeliido no válido (tamaño: mínimo 2 caracteres / máximo= 20 caracteres - sólo se admiten letras)");
		}
		if(foto == null) {
			throw new Exception("foto no añadida");
		}
		if(tec==null ||tec.size()==0) { 
			throw new Exception("no ingresó compas de tecnologías");
		}

	}
}
