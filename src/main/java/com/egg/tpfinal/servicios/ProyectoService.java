package com.egg.tpfinal.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.egg.tpfinal.entidades.Developer;
import com.egg.tpfinal.entidades.ONG;
import com.egg.tpfinal.entidades.Proyecto;
import com.egg.tpfinal.entidades.Usuario;
import com.egg.tpfinal.repositorios.OngRepository;
import com.egg.tpfinal.repositorios.ProyectoRepository;

@Service
public class ProyectoService {

	@Autowired
	private ProyectoRepository ProyectoRepo;

	@Autowired
	private OngService ONGservi;

	@Transactional(readOnly = true)
	public List<Proyecto> listarTodosProyecto() {
		return ProyectoRepo.findAll();
	}

	@Transactional(readOnly = true)
	public List<Proyecto> listarProyectosActivos() {
		return ProyectoRepo.buscarPorAlta();
	}
	@Transactional(readOnly = true)
	public List<Proyecto> listarProyectosBusquedaActivos(String b) {
		return ProyectoRepo.busqueda("%"+b+"%");
	}

	@Transactional
	public void EditarProyectoActivo(Long ID) {
		Proyecto proyecto = buscarPorID(ID);
		if (proyecto != null) {
			proyecto.setAlta(!proyecto.getAlta());
			//ProyectoRepo.save(proyecto);
		}
	}
	@Transactional
	public void borrarProyecto(Long ID) {
		EditarProyectoActivo(ID);                     //revisar utilidad de este método
	}

	@Transactional
	public void EliminarProyectoActivo(Long ID) {
		Proyecto proyecto = buscarPorID(ID);
		if (proyecto != null && proyecto.getAlta()==true) {
			proyecto.setAlta(!proyecto.getAlta());
			//ProyectoRepo.save(proyecto);
		}
	}
	
	@Transactional
	public void EliminarProyecto(Long ID) {
		EliminarProyectoActivo(ID);                     //revisar utilidad de este método
	}
	
	@Transactional
	public void editarProyecto(Long ID, String titulo, String cuerpo, Date fecha, List<Developer> developer, ONG ong) throws Exception {
		Proyecto proyecto = buscarPorID(ID);
		validarDatos(titulo, cuerpo, ong);
		guardarProyecto(proyecto, titulo, cuerpo, fecha, developer, ong);
	}

	@Transactional
	public void guardarProyecto(Proyecto proyecto, String titulo, String cuerpo, Date fecha, List<Developer> developer,
			ONG ong)  {
		ong.setPublicaciones(new ArrayList<Proyecto>());
		proyecto.setTitulo(titulo);
		proyecto.setCuerpo(cuerpo);
		proyecto.setFecha_post(fecha);
		proyecto.setDeveloper(developer);
		proyecto.setOng(ong);
		proyecto.setAdmitir_deve(true);
		proyecto.setAlta(true);
		ong.addProyecto(proyecto);
		//ONGservi.saveOng(ong); //esta de mas 
		proyecto.setOng(ong);
		ProyectoRepo.save(proyecto);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })     
	public void crearProyecto(String titulo, String cuerpo, Date fecha, List<Developer> developer, ONG ong) throws Exception {
		Proyecto proyecto = new Proyecto();
		validarDatos(titulo, cuerpo, ong);
		ong.setPublicaciones(new ArrayList<Proyecto>());
		ong.addProyecto(proyecto);
		guardarProyecto(proyecto, titulo, cuerpo, fecha, developer, ong);
		
		
	}

	@Transactional(readOnly = true)
	public Proyecto buscarPorID(Long ID) {
		Optional<Proyecto> p = ProyectoRepo.findById(ID);
		return p.get();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })     
	public void postularse(Developer deveAux, Long idProyecto) throws Exception {
		Proyecto proyecto = buscarPorID(idProyecto);
		List<Developer> postulados = proyecto.getDeveloper();
		if (!postulados.contains(deveAux) && postulados.size() < 2 && proyecto.getAdmitir_deve()) { //Cambiar a 9
			postulados.add(deveAux);
			proyecto.setDeveloper(postulados);
			if (postulados.size() >= 2) {				//Cambiar a 9
				proyecto.setAdmitir_deve(false);
				
			}
			//ineproyecto.getOng().getPublicaciones().forEach((e) -> System.out.println(e.getTitulo()));
			
			ProyectoRepo.save(proyecto);
		} else {
			throw new Exception("no puede unirse a este proyecto");
		}
	}
	
	
	
	public void validarDatos(String titulo, String cuerpo, ONG ong) throws Exception {
		if(titulo.isEmpty() ||!( titulo.length()>4 && titulo.length()<40)) {
			throw new Exception("Ingreso un titulo nulo o tamaño<4 o >20");
		}
		if(cuerpo.isEmpty() && !(cuerpo.length()>20 && cuerpo.length()<4000)) {
			throw new Exception("Ingreso un cuerpo nulo o tamaño<20 o >4000");
		}
		if(ong==null) {
			throw new Exception("Proyecto no se logro cargar con exito");
		}
	}
}
