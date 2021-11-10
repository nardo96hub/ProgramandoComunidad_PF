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
		//Tecnologias tec = RepoTec.buscarPorLenguaje(lenguaje);
		validar(lenguaje);
		lenguaje = lenguaje.toUpperCase();
		if(!lenguaje.isEmpty()) {
			Tecnologias tec = new Tecnologias();
			tec.setLenguaje(lenguaje);
			RepoTec.save(tec);
		} else {
			throw new Exception("Campo obligatorio");
		}
	}
	
	public void validar(String lenguaje) throws Exception {
		if(lenguaje.isBlank()) { //devuelve true si hay doble comillas/espacios (isEmpty no lo hace)
			throw new Exception("Campo obligatorio");
		}
	}
	
	@Transactional(readOnly = true)
	public List<Tecnologias> listarTecnologias() {
		return RepoTec.findAll();
	}
	
	@Transactional(readOnly=true)
	public List<Tecnologias> listarTecnologiasUnicas() {
		return RepoTec.listarLenguajes();
	}
}
