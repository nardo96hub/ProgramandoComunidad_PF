package com.egg.tpfinal.controlador;

import java.util.List;

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
import com.egg.tpfinal.entidades.Usuario;
import com.egg.tpfinal.servicios.FotoService;
import com.egg.tpfinal.servicios.OngService;
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
	
	
	@GetMapping("/crearong")
	public String registro() {
		return "registroong.html";
	}
	@PostMapping("/crearong")
	public String cargaong(@RequestParam String marca, @RequestParam String name,@RequestParam String ape,
			@RequestParam String user,@RequestParam String pass,@RequestParam(value="file", required=false) MultipartFile file
			,ModelMap mod)
	{
	
		
		try {
		
			Usuario u =ServiUsu.seteoUsuario(user, pass, Rol.ONG);//Falta validar Usuario
			
			Foto foto=null;
			if(file !=null) {
				foto = ServiFoto.guardarfoto(file);//solo sube la foto al server(no persiste la url)
				//se hizo asi porque sino no se guarda relacionada a la ong y no sabemos como funcionara la relacion con jointable
				
			}
			
			ServiOng.crearOng(u, marca, name, ape,foto);
			
			return "redirect:/login";
		} catch (Exception e) {
			mod.put("error", e.getMessage());
			e.printStackTrace();
			return "registroong.html";
		}
		
	}
	/*@GetMapping(value="/agregarong")//value=
	public String mostrarong(Model model) {
		model.addAttribute("ONG",new ONG());
		return "registroong.html";
	}
	
	@PostMapping("/crearong")
	public String cargarong(@ModelAttribute ONG ong ) {
		ServiOng.saveOng(ong);//En el futuro hacer crearOng para validar
		return "redirect:/";
	}
	*/
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/listarong")
	public String listarong(Model mod) {
		List<ONG> lo=ServiOng.listarONGactivas();
		mod.addAttribute("listarOng",lo);
		return "listaong";
		//return "redirect:/";
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/eliminarong/{id_ong}")
	public String eliminarong(@PathVariable Long id_ong) {
		ServiOng.borrarONG(id_ong);
		return "redirect:/listarTodo";
	}

	@PreAuthorize("isAuthenticated() && (hasAnyRole('ROLE_ADMIN') || hasAnyRole('ROLE_ONG')) ")
	@GetMapping("/editar/{id}")
	public String edi(@PathVariable Long id,ModelMap mod) {
		ONG o=ServiOng.getONG(id);
		mod.addAttribute(o);

		return "editarong";
	}
	
	@PreAuthorize("isAuthenticated() && hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/editar/{id}") //Consultar a adri como camiar foto
	public String editar(@PathVariable Long id,@RequestParam String marca, @RequestParam String name,@RequestParam String ape,ModelMap mod) {	
		try {			
			ONG o = ServiOng.getONG(id);			
			ServiOng.editarOng(id, marca, name, ape, o.getUsuario(), o.getFoto());
			return "redirect:/listarTodo";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mod.put("error",e.getMessage());
			return "redirect:/ong/editar/{id}";
		}	
	}
}
