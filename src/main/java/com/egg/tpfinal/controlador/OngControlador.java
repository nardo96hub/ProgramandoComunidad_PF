package com.egg.tpfinal.controlador;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.egg.tpfinal.entidades.Foto;
import com.egg.tpfinal.entidades.ONG;
import com.egg.tpfinal.entidades.Proyecto;
import com.egg.tpfinal.entidades.Usuario;
import com.egg.tpfinal.servicios.FotoService;
import com.egg.tpfinal.servicios.OngService;
import com.egg.tpfinal.servicios.ProyectoService;
import com.egg.tpfinal.servicios.UsuarioService;

import enumeracion.Rol;

@Controller

@RequestMapping("/ong")
public class OngControlador {

	@Autowired
	private UsuarioService ServiUsu;

	@Autowired
	private OngService ServiOng;

	@Autowired
	private FotoService ServiFoto;
	
	@Autowired
	private ProyectoService ServiProyecto;
	
	

	@GetMapping("/crearong")
	public String registro() {
		return "registroong.html";
	}

	@PostMapping("/crearong")
	public String cargaong(@RequestParam String marca, @RequestParam String name, @RequestParam String ape,
			@RequestParam String user, @RequestParam String pass,
			@RequestParam(value = "file", required = false) MultipartFile file, ModelMap mod) {

		try {

			Usuario u = ServiUsu.seteoUsuario(user, pass, Rol.ONG);// Falta validar Usuario

			Foto foto = null;
			if (file != null) {
				foto = ServiFoto.guardarfoto(file);// solo sube la foto al server(no persiste la url)
				// se hizo asi porque sino no se guarda relacionada a la ong y no sabemos como
				// funcionara la relacion con jointable

			}/* else {
				foto.setUrl_foto("https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png");
			}*/

			ServiOng.crearOng(u, marca, name, ape, foto);
			
			return "redirect:/login";
		} catch (Exception e) {
			mod.put("error", e.getMessage());
			e.printStackTrace();
			return "registroong.html";
		}

	}

	/*
	 * @GetMapping(value="/agregarong")//value= public String mostrarong(Model
	 * model) { model.addAttribute("ONG",new ONG()); return "registroong.html"; }
	 * 
	 * @PostMapping("/crearong") public String cargarong(@ModelAttribute ONG ong ) {
	 * ServiOng.saveOng(ong);//En el futuro hacer crearOng para validar return
	 * "redirect:/"; }
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/listarong")
	public String listarong(Model mod,@RequestParam(required=false) String b) {
		if(b!=null) {
			mod.addAttribute("listarOng",ServiOng.listarONGBusquedaActiva(b));
		}else {
			mod.addAttribute("listarOng", ServiOng.listarONGactivas());
		}
		
		
		return "listaong";
		// return "redirect:/";
	}

	/*@PreAuthorize("isAuthenticated() && (hasAnyRole('ROLE_ADMIN') || hasAnyRole('ROLE_ONG'))")
	@GetMapping("/eliminarong/{id_ong}")
	public String eliminarOng(@PathVariable Long id_ong,  ModelMap mod) {
		
		ONG o = ServiOng.getONG(id_ong);
		mod.addAttribute(o); // devuelvo a front el objeto a editar desde la base de datos

		return "eliminarong";
		 
	}*/
	
	@PreAuthorize("isAuthenticated() && (hasAnyRole('ROLE_ADMIN') || hasAnyRole('ROLE_ONG'))")
	@GetMapping("/eliminarong/{id_ong}")
	public String eliminarong(@PathVariable Long id_ong, ModelMap mod, HttpSession session) {
		try {
			//permite eliminar la ong al rol "ROLE_ONG"
			Usuario ongLogeada = (Usuario) session.getAttribute("usuariosession");
			ONG ong = ServiOng.getONG(id_ong);
			if(ongLogeada.getRol().equals(Rol.ONG) && (ong.getUsuario().getEmail().equals(ongLogeada.getEmail()))) {
				ServiOng.borrarONG(id_ong);
				for (Proyecto p : ong.getPublicaciones() ) {
					if (p.getAlta()) {
						ServiProyecto.EditarProyectoActivo(p.getId_proyecto());
					}
				}
				ServiOng.saveOng(ong);
				ServiUsu.eliminarUsuario(ongLogeada.getId_usuario());
				return "redirect:/logout";  // cambiar vista   
			} else {
				//permite eliminar la ong al rol "ROLE_ADMIN"
				if (ongLogeada.getRol().equals(Rol.ADMIN)) {
					ONG o2 = ServiOng.getONG(id_ong);
					ServiOng.borrarONG(id_ong);
					for (Proyecto p : o2.getPublicaciones() ) {
						if (p.getAlta()) {
							
							ServiProyecto.EditarProyectoActivo(p.getId_proyecto());
						} 
					}
				}
				return "redirect:/listarTodo";

			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mod.put("error", e.getMessage());
			return "redirect:/principal";
		}
		//return null; // no debería llegar nunca acá
		 
	}

	@PreAuthorize("isAuthenticated() && (hasAnyRole('ROLE_ADMIN') || hasAnyRole('ROLE_ONG')) ")
	@GetMapping("/editar/{id}")
	public String edi(@PathVariable Long id, ModelMap mod) {
		ONG o = ServiOng.getONG(id);
		mod.addAttribute("ong",o); // devuelvo a front el objeto a editar desde la base de datos

		return "editarong.html";
	}

	@PreAuthorize("isAuthenticated() && (hasAnyRole('ROLE_ADMIN') || hasAnyRole('ROLE_ONG'))")
	@PostMapping("/editar/{id}") 
	public String editar(@RequestParam(value = "file", required = false) MultipartFile file, @PathVariable Long id,
			@RequestParam String marca, @RequestParam String name, @RequestParam String ape, ModelMap mod,
			HttpSession session) {

		try {
			//permite editar la ong al rol "ROLE_ONG"
			Foto foto = null;
			Usuario ongLogeada = (Usuario) session.getAttribute("usuariosession");
			ONG o = ServiOng.getONG(id);
			if (ongLogeada.getRol().equals(Rol.ONG) && (o.getUsuario().getEmail().equals(ongLogeada.getEmail()))) {
				if (file != null) {
					foto = ServiFoto.guardarfoto(file);
					ServiOng.editarOng(id, marca, name, ape, o.getUsuario(), foto);
				} else {
					ServiOng.editarOng(id, marca, name, ape, o.getUsuario(), o.getFoto());
				}

				return "redirect:/principal";
			} else {
				//permite editar la ong al rol "ROLE_ADMIN"
				Foto foto2 = null;
				ONG o2 = ServiOng.getONG(id);
				if (ongLogeada.getRol().equals(Rol.ADMIN)) {
					if (file != null) {
						foto2 = ServiFoto.guardarfoto(file);
						ServiOng.editarOng(id, marca, name, ape, o2.getUsuario(), foto2);
					} else {
						ServiOng.editarOng(id, marca, name, ape, o2.getUsuario(), o2.getFoto());
					}
					return "redirect:/listarTodo";
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			mod.put("error", e.getMessage());
			return "redirect:/principal";
		}
		return null; // no debería llegar nunca acá
	}
}
