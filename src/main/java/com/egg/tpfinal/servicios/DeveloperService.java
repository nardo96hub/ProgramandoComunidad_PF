package com.egg.tpfinal.servicios;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.egg.tpfinal.entidades.*;
import com.egg.tpfinal.repositorios.DeveloperRepository;

@Service
public class DeveloperService {


	@Autowired
	private DeveloperRepository DevRepo;
	
	public void guardarDeveloper(Developer dev, Usuario usuario, String tel, String nombre, String apellido, Foto foto, List<Tecnologias> tec) {
		dev.setNombre(nombre);
		dev.setApellido(apellido);
		dev.setAlta(true);
		dev.setTecnologias(tec);// ver como se arma en el front
		dev.setTelefono(tel);
		dev.setUsuario(usuario);
		dev.setFoto(null);
		DevRepo.save(dev);
	}
	
	public void borrarDeveloper(Long ID) {
		EditarDeveloperActivo(ID);
	}
	
	public void editarDeveloper(Long ID, Usuario usuario, String nombre, String apellido, String tel, Foto foto, List<Tecnologias> tec) {
		Developer dev = getDeveloper(ID);
		guardarDeveloper(dev, usuario, nombre, apellido, tel, foto, tec);
	}
	
	public void crearDeveloper(Usuario usuario, String nombre, String apellido, String tel, Foto foto, List<Tecnologias> tec) throws Exception {
		Developer dev = new Developer();
		guardarDeveloper(dev, usuario, tel, nombre, apellido, foto, tec);
	}
	
	public List<Developer> listarTodosDeveloper() {
		return DevRepo.findAll();
	}
	
	public List<Developer> listarDeveloperActivos() {
		return DevRepo.listarDeveloperActivos();
	}
	
	public void EditarDeveloperActivo(Long ID) {
		Developer dev = getDeveloper(ID);
		
		if (dev != null) {
			dev.setAlta(!dev.getAlta());
			DevRepo.save(dev);
		}
	}
	
	public Developer getDeveloper(Long ID) {
		Optional<Developer> d=DevRepo.findById(ID);
		return d.get();
	}
	
	
}
