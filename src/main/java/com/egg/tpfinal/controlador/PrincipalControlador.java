package com.egg.tpfinal.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.tpfinal.servicios.DeveloperService;
import com.egg.tpfinal.servicios.OngService;
import com.egg.tpfinal.servicios.ProyectoService;
import com.egg.tpfinal.servicios.UsuarioService;
import com.egg.tpfinal.entidades.*;

@Controller
@RequestMapping("")
public class PrincipalControlador {
	@Autowired
	private UsuarioService ServiUsu;
	@Autowired
	private DeveloperService ServiDev;
	@Autowired
	private OngService ServiOng;
	@Autowired
	private ProyectoService ServiProy;
	
	
	@GetMapping("/")
	public String s() {
		return "index.html";
	}
	
	@GetMapping("/quienesomos")
	public String quiensoy() {
		return "quienesomos";
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/principal")
	public String principal() {
		return "principal";
	}
	
	 @GetMapping("/login")
	    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
	        if (error != null) {
	            model.put("error", "Usuario o clave incorrectos");
	        }
	        if (logout != null) {
	            model.put("logout", "Ha salido correctamente.");
	        }
	        return "login.html";
	    }
	 
	 
	 @PreAuthorize("hasAnyRole('ROLE_ADMIN') && isAuthenticated()" )
	 @GetMapping("/listarTodo")
	 public String todo(ModelMap mod) {
		 List<Proyecto> lp= ServiProy.listarTodosProyecto();
		 List<Usuario> lu = ServiUsu.mostrarUsuarios();
		 List<Developer> ld = ServiDev.listarTodosDeveloper();
		 List<ONG> lo = ServiOng.listartodaslasONG();
		 
		 mod.addAttribute("usuarios",lu);
		 mod.addAttribute("developers",ld);
		 mod.addAttribute("ongs",lo);
		 mod.addAttribute("proyectos",lp);
		 return "listarTodo";
	 }
	
					

		
}

