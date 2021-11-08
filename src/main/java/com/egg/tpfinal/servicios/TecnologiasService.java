package com.egg.tpfinal.servicios;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.egg.tpfinal.entidades.Tecnologias;
import com.egg.tpfinal.repositorios.TecnologiaRepository;

@Service
public class TecnologiasService {
	@Autowired
	private TecnologiaRepository RepoTec;
	
	@Transactional
	public void guardarTecnologias(String lenguaje) throws Exception {
		
		Tecnologias tec=RepoTec.buscarPorLenguaje(lenguaje);
		lenguaje = lenguaje.toUpperCase();
		if(tec==null) {
			tec=new Tecnologias();
			tec.setLenguaje(lenguaje);
			RepoTec.save(tec);
		}//else throw new  Exception("Ya existe la tecnologia");
		
	}
	
	//Por qué no toma transactional read only true
	public List<Tecnologias> listarTecnologias(){
		return RepoTec.findAll();
	}
	
	//Por qué no toma transactional read only true
	public List<Tecnologias> listarTecnologiasUnicas(){
		return RepoTec.listarLenguajes();
	}
}
