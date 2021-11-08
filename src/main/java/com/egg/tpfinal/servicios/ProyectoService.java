package com.egg.tpfinal.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.tpfinal.entidades.Developer;
import com.egg.tpfinal.entidades.ONG;
import com.egg.tpfinal.entidades.Proyecto;
import com.egg.tpfinal.entidades.Usuario;
import com.egg.tpfinal.repositorios.ProyectoRepository;

@Service 
public class ProyectoService {

	@Autowired
	private ProyectoRepository ProyectoRepo;
	@Autowired
	private OngService ONGservi;
	
	public List<Proyecto> listarTodosProyecto() {
		return ProyectoRepo.findAll();
	}
	
	public List<Proyecto> listarProyectosActivos() {
		return ProyectoRepo.buscarPorAlta();
	}
	
	public void EditarProyectoActivo(Long ID) {
		Proyecto proyecto = buscarPorID(ID);
		if(proyecto != null) {
			proyecto.setAlta(!proyecto.getAlta());
		 ProyectoRepo.save(proyecto);
		}
	}

	public void borrarProyecto(Long ID) {
		EditarProyectoActivo(ID);
	}
	
	public void editarProyecto(Long ID, String titulo, String cuerpo, Date fecha, List<Developer> developer, ONG ong) {
		Proyecto proyecto = buscarPorID(ID);
		guardarProyecto(proyecto, titulo, cuerpo, fecha, developer, ong);
	}
	
	
	public void guardarProyecto(Proyecto proyecto, String titulo, String cuerpo, Date fecha, List<Developer> developer, ONG ong) {
	//	ong.setPublicaciones(new ArrayList<Proyecto>());
		
		proyecto.setTitulo(titulo);
		proyecto.setCuerpo(cuerpo);
		proyecto.setFecha_post(fecha);
		proyecto.setDeveloper(developer);
		
		proyecto.setAdmitir_deve(true);
		proyecto.setAlta(true);
	//	ong.addProyecto(proyecto);
		//ONGservi.saveOng(ong);
		proyecto.setOng(ong);
		
		ProyectoRepo.save(proyecto);
	}
	
	public void crearProyecto(String titulo, String cuerpo, Date fecha, List<Developer> developer, ONG ong) {
		Proyecto proyecto = new Proyecto();
		
		guardarProyecto(proyecto, titulo, cuerpo, fecha, developer, ong);
	}
	@Transactional(readOnly = true)
	public Proyecto buscarPorID(Long ID) {
		Optional<Proyecto> p = ProyectoRepo.findById(ID);
		return p.get();
	}

	@Transactional
	public void postularse( Developer deveAux, Long idProyecto) throws Exception{
		
		Proyecto proyecto = buscarPorID(idProyecto);
		List<Developer> postulados= proyecto.getDeveloper();
		 if( !postulados.contains(deveAux) && postulados.size()<9 && proyecto.getAdmitir_deve()) {
			 
			 postulados.add(deveAux);
			 proyecto.setDeveloper(postulados);
			 if(postulados.size()>=9) {
				 proyecto.setAdmitir_deve(false);
			 }
			 
			 
			 ProyectoRepo.save(proyecto);
		 }else {
			 throw new Exception("no puede unirse a este proyecto");
		 }
		
	
		
		
		
	}
	
}
