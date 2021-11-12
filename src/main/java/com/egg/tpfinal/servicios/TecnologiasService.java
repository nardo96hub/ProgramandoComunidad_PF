package com.egg.tpfinal.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.tpfinal.entidades.Tecnologias;
import com.egg.tpfinal.repositorios.TecnologiaRepository;

@Service
public class TecnologiasService {
	@Autowired
	private TecnologiaRepository RepoTec;
	
	@Transactional
	public void guardarTecnologias(String lenguaje) throws Exception {

		validar(lenguaje);
		lenguaje = lenguaje.toUpperCase();							//pone en MAYUS 
		if(!lenguaje.isEmpty()) {									//si NO esta vacío, crea una nueva TEC y setea el lenguaje
			Tecnologias tec = new Tecnologias();
			tec.setLenguaje(lenguaje);
			RepoTec.save(tec);											//guarda la nueva TEC
		} else {
			throw new Exception("Campo obligatorio");
		}
	}
	
	public void validar(String lenguaje) throws Exception {
		if(lenguaje.isEmpty()) { 		//devuelve true si hay doble comillas/espacios (isEmpty no lo hace)
			throw new Exception("Campo obligatorio");
		}
	}
	
	@Transactional(readOnly = true)
	public List<Tecnologias> listarTecnologias() { 					//query JPA
		return RepoTec.findAll();
	}
	
	@Transactional(readOnly=true)
	public List<Tecnologias> listarTecnologiasUnicas() {		//query de repo
		return RepoTec.listarLenguajes();
	}
}
