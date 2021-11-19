package com.egg.tpfinal.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.egg.tpfinal.entidades.Developer;
import com.egg.tpfinal.entidades.ONG;
import com.egg.tpfinal.entidades.Proyecto;
import com.egg.tpfinal.entidades.Usuario;
import com.egg.tpfinal.repositorios.OngRepository;
import com.egg.tpfinal.servicios.DeveloperService;
import com.egg.tpfinal.servicios.OngService;
import com.egg.tpfinal.servicios.ProyectoService;
//import com.egg.tpfinal.servicios.UsuarioService;

import enumeracion.Rol;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/proyect")
public class ProyectoControlador {

	@Autowired
	private ProyectoService proyecServi;
	// @Autowired
	// private UsuarioService userServi;
	@Autowired
	private OngService OngServi;
	@Autowired
	private DeveloperService deveServi;
	@Autowired
	private OngRepository ongRepo;
	// @Autowired
	// private DeveloperService devServi;

	@PreAuthorize("hasAnyRole('ROLE_ONG')")
	@GetMapping("/publicarproyecto")
	public String registrar() {
		return "publishproyectTest.html";
	}

	@PreAuthorize("hasAnyRole('ROLE_ONG')")
	@PostMapping("/procesarform")
	public String crearProyecto(/* @RequestParam String email_usuario, */ @RequestParam String cuerpo,
			@RequestParam String titulo, HttpSession session, ModelMap mod) {

		try {
			// el email es obtenido por la session del usuario logeado
			Usuario ongLogeada = (Usuario) session.getAttribute("usuariosession");
			/*
			 * System.out.println("Usuario?"); System.out.println(ongLogeada);
			 */
			// String email_usuario = ongLogeada.getEmail();

			// Usuario user = userServi.getUsuarioEmail(email_usuario);

			// ONG ongaux = new ONG();
			// optimizar siguiente codigo en futuras versiones, y ver que funcion cumple
			// aqui
			// aunque es una buena medida de seguridad, se prefiere hacer una sola consulta
			// con email
			/*
			 * for (ONG ong : OngServi.listarONGactivas()) { if
			 * (ong.getUsuario().getId_usuario() == user.getId_usuario()) { ongaux = ong; }
			 * else { //redireccionar a sign up?
			 * 
			 * } }
			 */

			// ONG ongaux = OngServi.buscarONGporUsuario(ongLogeada);
			ONG ongaux = ongRepo.buscarPorEmail(ongLogeada.getEmail());
			/*
			 * System.out.println("ONG?"); System.out.println(ongaux);
			 */

			// ONG ong = OngServi.buscarONGporidUsuario(user.getId_usuario()).get();
			// //arreglar en futuras versiones
			Date date = new Date();

			proyecServi.crearProyecto(titulo, cuerpo, date, new ArrayList<Developer>(), ongaux);
			return "redirect:/principal";
		} catch (Exception e) {
			mod.put("error", e.getMessage());
			e.printStackTrace();
			return "publishproyectTest.html";
		}

	}

	@GetMapping("/proyecto")
	public String mostrarproyectos(ModelMap mod, @RequestParam(required = false) String b) {
		if (b != null) {
			mod.addAttribute("proyectos", proyecServi.listarProyectosBusquedaActivos(b));
		} else {
			mod.addAttribute("proyectos", proyecServi.listarProyectosActivos());
		}

		return "listaproyectos"; // falta vista
	}

	@GetMapping("/eliminarproyecto/{id}")
	public String eliminarProyecto(@PathVariable Long id) {
		// System.out.println("Id: "+id);
		proyecServi.borrarProyecto(id);
		return "redirect:/listarTodo"; // falta vista
	}

	/*
	 * EN SEGUNDA VERSION public String editarProyecto() { return ""; }
	 */

	@GetMapping("/postularse/{idProyecto}")
	public String postularse(HttpSession session, @PathVariable Long idProyecto) {

		// Usuario usuario = null;
		try {

			Usuario login = (Usuario) session.getAttribute("usuariosession");
			Developer deveAux = deveServi.getDeveloperporIdUser(login.getId_usuario());

			proyecServi.postularse(deveAux, idProyecto);
			return "redirect:/proyect/proyecto/{idProyecto}";

		} catch (Exception e) {
			return "redirect:/proyect/proyecto";
		}
	}

	@GetMapping("/proyecto/{id}") // revisar
	public String devolverProyecto(ModelMap mod, @PathVariable Long id) {
		// System.out.println(id);
		Proyecto proyecto = proyecServi.buscarPorID(id);
		// System.out.println(proyecto.getDeveloper().get(0).getFoto().getUrl_foto());
		mod.addAttribute("proyecto", proyecto);
		return "proyectoindividual";
	}

	// Una vez se da a boton forzar o se alcanzo el estado false en mas developer se
	// carga el proyecto en la ong
	@GetMapping("/forzarInicio/{id}")
	public String cargarProyectoenONG(@PathVariable Long id) {

		Proyecto p = proyecServi.buscarPorID(id);

		OngServi.agregarProyectos(p.getOng(), p);

		return "redirect:/principal";
	}

	@PreAuthorize("isAuthenticated() && (hasAnyRole('ROLE_ADMIN') || hasAnyRole('ROLE_ONG'))")
	@GetMapping("/editar/{id}")
	public String ed(@PathVariable Long id, ModelMap mod) {
		Proyecto p = proyecServi.buscarPorID(id);
		mod.addAttribute(p);
		return "editarproyecto";
	}

	@PreAuthorize("isAuthenticated() && (hasAnyRole('ROLE_ADMIN') || hasAnyRole('ROLE_ONG'))")
	@PostMapping("/editar/{id}")
	public String editar(@PathVariable Long id, @RequestParam String titulo,  @RequestParam String cuerpo,  ModelMap mod,
			HttpSession session) {
		try {
			Proyecto p = proyecServi.buscarPorID(id);
			Usuario ongLogeada = (Usuario) session.getAttribute("usuariosession");
			ONG o = OngServi.getONG(p.getOng().getId_ong());
			if (ongLogeada.getRol().equals(Rol.ONG) && (o.getUsuario().getEmail().equals(ongLogeada.getEmail()))) {
				proyecServi.editarProyecto(id, titulo, cuerpo, p.getFecha_post(), p.getDeveloper(), p.getOng());
				return "redirect:/perfil";
			} else {
				proyecServi.editarProyecto(id, titulo, cuerpo, p.getFecha_post(), p.getDeveloper(), p.getOng());
				return "redirect:/listarTodo";
			}
		} catch (Exception e) {
			e.printStackTrace();
			mod.put("error", e.getMessage());
			return "redirect:/principal";
		}
	}
}
