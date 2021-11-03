package com.egg.tpfinal.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egg.tpfinal.entidades.Tecnologias;
import com.egg.tpfinal.repositorios.TecnologiaRepository;

@Service
public class TecnologiasService {
	@Autowired
	private TecnologiaRepository RepoTec;
	
	public void guardarTecnologias(String lenguaje) throws Exception {
		Tecnologias tec=RepoTec.buscarPorLenguaje(lenguaje);
		
		if(tec==null) {
			tec=new Tecnologias();
			tec.setLenguaje(lenguaje);
			RepoTec.save(tec);
		}//else throw new  Exception("Ya existe la tecnologia");
		
	}
	public List<Tecnologias> listarTecnologias(){
		
		return RepoTec.findAll();
	}
	public List<Tecnologias> listarTecnologiasUnicas(){
		return RepoTec.listarLenguajes();
	}
}
