package com.egg.tpfinal.controlador;

import java.util.ArrayList;
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

import enumeracion.Rol;

import com.egg.tpfinal.entidades.*;
import com.egg.tpfinal.repositorios.DeveloperRepository;
import com.egg.tpfinal.repositorios.OngRepository;

@Controller
@RequestMapping("")
public class PrincipalControlador {
	@Autowired
	private UsuarioService ServiUsu;
	@Autowired
	private DeveloperService ServiDev;
	@Autowired
	private DeveloperRepository RepoDev;
	@Autowired
	private OngService ServiOng;
	@Autowired
	private OngRepository RepoOng;
	@Autowired
	private ProyectoService ServiProy;
	
	
	@GetMapping("/")
	public String s() {
		return "index.html";
	}
	
	@GetMapping("/quienesomos")
	public String quiensoy(ModelMap model,HttpSession session) {
		
			Usuario log = (Usuario) session.getAttribute("usuariosession");
			if(log!=null) {
				Developer d=RepoDev.buscarPorEmail(log.getEmail());
			if(d!=null) {
				model.addAttribute("fotoperfil",d);
			}else {
				ONG o = RepoOng.buscarPorEmail(log.getEmail());
				model.addAttribute("fotoperfil",o);
			}
			}
			
		
		return "quienesomos";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/principal")
	public String principal(ModelMap model,HttpSession session) {
		Usuario ongLogeada = (Usuario) session.getAttribute("usuariosession");
		model.addAttribute("usuario",ongLogeada);
		if(ongLogeada.getRol()==Rol.DEVE) {
			Developer dev=RepoDev.buscarPorEmail(ongLogeada.getEmail());
			model.addAttribute("fotoperfil",dev);
		}
		if(ongLogeada.getRol()==Rol.ONG) {
			ONG ong=RepoOng.buscarPorEmail(ongLogeada.getEmail());
			model.addAttribute("fotoperfil",ong);
			
		}
		if(ongLogeada.getRol()==Rol.ADMIN) {
			ONG ong=RepoOng.buscarPorEmail(ongLogeada.getEmail());
			if(ong!=null) {
					model.addAttribute("fotoperfil",ong);
			}else {
				Developer dev=RepoDev.buscarPorEmail(ongLogeada.getEmail());
				model.addAttribute("fotoperfil",dev);
			}
		
		}
		
		
		
		
      model.put("nombre", ServiUsu.nombre());
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
	
	
	 //Administrador no tiene perfil para ser el codigo mas facil en futuras versiones se implementa
	 //@PreAuthorize("(hasAnyRole('ROLE_DEV') || hasAnyRole('ROLE_ONG')) && isAuthenticated()")
	 @GetMapping("/perfil")
	 public String perfil(ModelMap mod,HttpSession session){
		 Usuario usuLog=(Usuario) session.getAttribute("usuariosession");
		 if(usuLog.getRol()==Rol.ONG) {
			 ONG o=RepoOng.buscarPorEmail(usuLog.getEmail());
			 if(o!=null) {
				 mod.addAttribute("perfil",o);
			 }else {
				 return "redirect:/principal";  //Nunca deberia pasar ya que usuario tiene rol ONG
			 }
			 
		 }else {	//Como dev no tiene proyecto en atributos se reniega mas esto de ahora se deberia hacer en un servicio pero para evitar conflicto se hace en controlador
			 Developer d=RepoDev.buscarPorEmail(usuLog.getEmail());
			 if(d!=null) {
				 
				 mod.addAttribute("perfil",d);
				 List<Proyecto> proy=new ArrayList<Proyecto>();
				 List<Proyecto> proyact=ServiProy.listarProyectosActivos();
				 
				 for (Proyecto p : proyact) {
					List<Developer> dep=p.getDeveloper();
					fin:
					for (Developer dev : dep) {
						if(dev.equals(d)) {
							proy.add(p);
							System.out.println("Agrego el proyecto: "+p);
							break fin;
						}
					}
					
				}
				 System.out.println("Se agrego bien?");
				 for (Proyecto proyecto : proy) {
					System.out.println(proyecto);
				}
				 
				 
				 
				 
				 mod.addAttribute("listaProyDev",proy);
				 
				 
			 }else {
				 return "redirect:/principal";
			 }
		 }
		 
		 
		 
		 return "perfil.html";
	 }

		
}

