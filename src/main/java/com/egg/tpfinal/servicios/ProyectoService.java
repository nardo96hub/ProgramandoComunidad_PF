package com.egg.tpfinal.servicios;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.egg.tpfinal.entidades.Developer;
import com.egg.tpfinal.entidades.ONG;
import com.egg.tpfinal.entidades.Proyecto;
import com.egg.tpfinal.repositorios.ProyectoRepository;

@Service 
public class ProyectoService {

	@Autowired
	private ProyectoRepository ProyectoRepo;
	
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
		proyecto.setTitulo(titulo);
		proyecto.setCuerpo(cuerpo);
		proyecto.setFecha_post(fecha);
		proyecto.setDeveloper(developer);
		proyecto.setOng(ong);
		proyecto.setAdmitir_deve(true);
		proyecto.setAlta(true);
		ProyectoRepo.save(proyecto);
	}
	
	public void crearProyecto(String titulo, String cuerpo, Date fecha, List<Developer> developer, ONG ong) {
		Proyecto proyecto = new Proyecto();
		guardarProyecto(proyecto, titulo, cuerpo, fecha, developer, ong);
	}
	
	public Proyecto buscarPorID(Long ID) {
		Optional<Proyecto> p = ProyectoRepo.findById(ID);
		return p.get();
	}
	
}
