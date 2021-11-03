package com.egg.tpfinal.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.egg.tpfinal.entidades.Developer;
import com.egg.tpfinal.entidades.ONG;
import com.egg.tpfinal.entidades.Proyecto;
import com.egg.tpfinal.entidades.Usuario;
import com.egg.tpfinal.servicios.DeveloperService;
import com.egg.tpfinal.servicios.OngService;
import com.egg.tpfinal.servicios.ProyectoService;
import com.egg.tpfinal.servicios.UsuarioService;

@Controller
@RequestMapping("proyect")
public class ProyectoControlador {
	
	@Autowired
	private ProyectoService proyecServi;
	@Autowired
	private UsuarioService userServi;
	@Autowired
	private OngService OngServi;
	
	//@Autowired
	//private DeveloperService devServi;
	
	@GetMapping("/publicarproyecto")
	public String registrar() {
		
		return "publishproyectTest.html";
	}
	
	@PostMapping("/procesarform")
	public String crearProyecto(/*@RequestParam String email_usuario,*/ @RequestParam String cuerpo, @RequestParam String titulo) {
		//el email se obtendra por sesion o por json o otro
		
		String email_usuario="user1@gmail.com"; 
		//reemplazar linea anterior cuando la seguridad o token o json o alternativa este hecha
		Usuario user = userServi.getUsuarioEmail(email_usuario);
		//hacer validacion de usuario null
		ONG ong = OngServi.buscarONGporidUsuario(user.getId_usuario()).get(); //arreglar en futuras versiones
		Date date = new Date();
		List<Developer> list = new ArrayList<Developer>();
		proyecServi.crearProyecto(titulo, cuerpo, date, list, ong);
		return "redirect:/";
	}
	
	@GetMapping()
	public String mostrarproyectos(ModelMap mod) {
		List<Proyecto> lp = proyecServi.listarProyectosActivos();
		mod.addAttribute("listaProyecto", lp);
		return "";
	}
	
	
	
}