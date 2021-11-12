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
	public List<Proyecto> listarTodosProyecto() { 						//query JPA- muestra todos 
		return ProyectoRepo.findAll();
	}

	@Transactional(readOnly = true)
		public List<Proyecto> listarProyectosActivos() {				//muestra proyectos activos, con query REPO
		return ProyectoRepo.buscarPorAlta();
	}

	@Transactional
	public void EditarProyectoActivo(Long ID) {
		Proyecto proyecto = buscarPorID(ID);				
		if (proyecto != null) {											// invierte valor de variab ALTA
			proyecto.setAlta(!proyecto.getAlta());
			
		}
	}

	@Transactional
	public void borrarProyecto(Long ID) {								//pone ALTA=FALSE 
		EditarProyectoActivo(ID);
	}

	@Transactional														//actaliza inform de Proyecto
	public void editarProyecto(Long ID, String titulo, String cuerpo, Date fecha, List<Developer> developer, ONG ong) throws Exception {
		Proyecto proyecto = buscarPorID(ID);
		validarDatos(titulo, cuerpo, ong);								//valida datos recibidos y guarda en BBDD		
		guardarProyecto(proyecto, titulo, cuerpo, fecha, developer, ong);
	}

	@Transactional
	public void guardarProyecto(Proyecto proyecto, String titulo, String cuerpo, Date fecha, List<Developer> developer,
			ONG ong)  {

		ong.setPublicaciones(new ArrayList<Proyecto>());
		proyecto.setTitulo(titulo);
		proyecto.setCuerpo(cuerpo);
		proyecto.setFecha_post(fecha);
		proyecto.setDeveloper(developer);								//seteo de valores
		proyecto.setOng(ong);
		proyecto.setAdmitir_deve(true);
		proyecto.setAlta(true);
		ong.addProyecto(proyecto);
		proyecto.setOng(ong);
		ProyectoRepo.save(proyecto);									// guarda en BBDD
		
		
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })     
	public void crearProyecto(String titulo, String cuerpo, Date fecha, List<Developer> developer, ONG ong) throws Exception {
		Proyecto proyecto = new Proyecto();
		validarDatos(titulo, cuerpo, ong);    									//valida y setea datos
		ong.setPublicaciones(new ArrayList<Proyecto>());
		ong.addProyecto(proyecto);
		guardarProyecto(proyecto, titulo, cuerpo, fecha, developer, ong);		//guarda el NUEVO PROYECTO
		
		
	}

	@Transactional(readOnly = true)
	public Proyecto buscarPorID(Long ID) {
		Optional<Proyecto> p = ProyectoRepo.findById(ID);							//consulta JPA x ID
		return p.get();												//es optional xq es lo q devuelve la query JPA
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })     
	public void postularse(Developer deveAux, Long idProyecto) throws Exception {				
		Proyecto proyecto = buscarPorID(idProyecto);								//busca el proyecto x query REPO
		List<Developer> postulados = proyecto.getDeveloper();						//devuelve lista ebntera
		if (!postulados.contains(deveAux) && postulados.size() < 2 && proyecto.getAdmitir_deve()) { //(Cambiar a 9)
			postulados.add(deveAux);							//si el devep q se quiere postular no esta, y no excede el max N°DEVP
			proyecto.setDeveloper(postulados);					//si cumple lo anterior, lo guarda
			if (postulados.size() >= 2) {				//Cambiar a 9
				proyecto.setAdmitir_deve(false);				//si se lleno cant de devep, se pone el fase admitir deves en proyecto			
				
			}
			//ineproyecto.getOng().getPublicaciones().forEach((e) -> System.out.println(e.getTitulo()));   //forech en 1 LINEA
			
			ProyectoRepo.save(proyecto);						//guarda proyecto de nuevo con cambios(admitir y develp cant)
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
