package com.egg.tpfinal.servicios;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egg.tpfinal.entidades.*;
import com.egg.tpfinal.repositorios.OngRepository;

import enumeracion.Rol;

@Service
public class OngService {


	@Autowired
	private OngRepository ONGRepo;
	@Autowired
	private UsuarioService ServiUsu;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void guardarOng(ONG ong,String marca, String nombre_rep, String apellido_rep,Usuario usuario) {
		
		ong.setMarca(marca);
		ong.setNombre_rep(nombre_rep);
		ong.setApellido_rep(apellido_rep);
		ong.setUsuario(usuario);
		ong.setAlta(true);
		
		ServiUsu.saveUsuario(usuario);
		
		
		ONGRepo.save(ong);
				
	}
	
	public void borrarONG(Long ID) {
		 EditarONGActivo(ID);
	}
	
	private void EditarONGActivo(Long ID) {
		ONG ong = getONG(ID);	
		
		if (ong != null) {
			ong.setAlta(!ong.getAlta());
			ONGRepo.save(ong);
		}
		
	}
	
	public List<ONG> listartodaslasONG() {
		return ONGRepo.findAll();
	}

	public List<ONG> listarONGactivas() {
		return ONGRepo.listarONGactivas();
	}
	
	public void editarOng(Long ID, String marca, String nombre_rep, String apellido_rep,Usuario usuario){
		
		ONG ong = getONG(ID);
	
		guardarOng(ong, marca, nombre_rep, apellido_rep, usuario);
		
	}
	
	public ONG getONG(Long ID) {
		Optional<ONG> ong=ONGRepo.findById(ID);
		return ong.get();
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void crearOng(Usuario usuario,String marca,String nombre_rep,String apellido_rep) throws Exception {
		ONG ong=ONGRepo.marcaOng(marca);//Se puede cambiar por email usuario
		if(ong==null) {
			ong=new ONG();
			guardarOng(ong, marca, nombre_rep, apellido_rep, usuario);
			
		}else {
			throw new Exception("Ya se encuentra la Ong en BDD");
		}
	}
	public void saveOng(ONG ong) {
		
		 

		ONGRepo.save(ong);
	}

	public Optional<ONG> buscarONGporidUsuario(Long ID) {
	//	return ONGRepo.buscarONGporidUsuario(ID);
		return ONGRepo.findById(ID);
		
	}
}
