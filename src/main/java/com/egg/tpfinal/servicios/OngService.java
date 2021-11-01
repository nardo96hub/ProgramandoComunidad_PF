package com.egg.tpfinal.servicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.egg.tpfinal.entidades.*;
import com.egg.tpfinal.repositorios.OngRepository;

@Service
public class OngService {
	
	
	@Autowired
	private OngRepository ONGRepo;
	
	public void guardarOng(ONG ong,String marca, String nombre_rep, String apellido_rep,Usuario usuario) {
		
		ong.setMarca(marca);
		ong.setNombre_rep(nombre_rep);
		ong.setApellido_rep(apellido_rep);
		ong.setUsuario(usuario);
		ong.setAlta(true);
				
		//ONGRepo.save(ong);
				
	}
	
	public void borrarONG(String ID) {
		 EditarONGActivo(ID);
	}
	
	private void EditarONGActivo(String ID) {
		ONG ong = ONGRepo.buscarPorID(ID);	
		
		if (ong != null) {
			ong.setAlta(!ong.getAlta());
			ONGRepo.save(ong);
		}
		
	}
	
	public List<ONG> listartodaslasONG() {
		return ONGRepo.findAll();
	}

	public List<ONG> listarONGactivas() {
		return ONGRepo.findActive();
	}
	
	public void editarOng(String ID, String marca, String nombre_rep, String apellido_rep,Usuario usuario){
		
		//ONG ong = ONGRepo.buscarPorID(ID);
	
		guardarOng(ong, marca, nombre_rep, apellido_rep, usuario);
		
	}
	
	public ONG getONG(String ID) {
		return ONGRepo.buscarPorID(ID);
	}
	
	
	
}
