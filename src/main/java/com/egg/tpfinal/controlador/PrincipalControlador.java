package com.egg.tpfinal.controlador;

import java.util.List;

import javax.servlet.http.HttpSession;

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
	
	
	@GetMapping("/")							//la vista x defec 
	public String s() {
		return "index.html";
	}
	
	@GetMapping("/quienesomos")				//vista q se muestra al tocar "quienes somos"
	public String quiensoy() {
		return "quienesomos";
	}
	@PreAuthorize("isAuthenticated()") 									//se accede si se loguea 
	@GetMapping("/principal")											//vista q se muestra al loguear
	public String principal(ModelMap model,HttpSession session) {
	    model.put("nombre", ServiUsu.nombre());							//muestra el nombre cuando se loguea
		return "principal";
	}
	
	 @GetMapping("/login")
	    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
	        if (error != null) {											//si error esta lleno, se imprime mensj
	            model.put("error", "Usuario o clave incorrectos");			
	        }
	        if (logout != null) {
	            model.put("logout", "Ha salido correctamente.");		//si usuario cerro seción, vuelve al inicio y muestra mensj
	        }
	        return "login.html";
	    }
	 
	 
	 @PreAuthorize("hasAnyRole('ROLE_ADMIN') && isAuthenticated()" )  // si está logueadoy rol=admin, puede acceder a URL
	 @GetMapping("/listarTodo")
	 public String todo(ModelMap mod) {									
		 List<Proyecto> lp= ServiProy.listarTodosProyecto();
		 List<Usuario> lu = ServiUsu.mostrarUsuarios();					//estos son toas tablas q puede ver ADMIN
		 List<Developer> ld = ServiDev.listarTodosDeveloper();
		 List<ONG> lo = ServiOng.listartodaslasONG();
		 
		 mod.addAttribute("usuarios",lu);
		 mod.addAttribute("developers",ld);							// el .add agrega listas de objetos al modelmap p mostrar a admin
		 mod.addAttribute("ongs",lo);
		 mod.addAttribute("proyectos",lp);
		 return "listarTodo";
	 }
		
}

