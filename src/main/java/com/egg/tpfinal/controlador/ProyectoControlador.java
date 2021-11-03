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
import com.egg.tpfinal.servicios.OngService;
import com.egg.tpfinal.servicios.ProyectoService;
import com.egg.tpfinal.servicios.UsuarioService;

@Controller
@RequestMapping("")
public class ProyectoControlador {
	
	@Autowired
	private ProyectoService proyecServi;
	@Autowired
	private UsuarioService userServi;
	@Autowired
	private OngService OngServi;
	
	@PostMapping()
	public String crearProyecto(@RequestParam String email_usuario, @RequestParam String cuerpo, @RequestParam String titulo) {
		Usuario user = userServi.getUsuarioEmail(email_usuario);
		//hacer validacion de usuario null
		ONG ong = OngServi.buscarONGporidUsuario(user.getId_usuario());
		Date date = new Date();
		List<Developer> list = new ArrayList<Developer>();
		proyecServi.crearProyecto(titulo, cuerpo, date, list, ong);
		return"";
	}
	
	@GetMapping()
	public String mostrarproyectos(ModelMap mod) {
		List<Proyecto> lp = proyecServi.listarProyectosActivos();
		mod.addAttribute("listaProyecto", lp);
		return "";
	}
	
	
	
}