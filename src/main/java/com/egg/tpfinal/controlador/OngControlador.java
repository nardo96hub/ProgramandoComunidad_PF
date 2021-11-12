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
	
	
	@GetMapping("/crearong")					//muestra el registro de ONG
	public String registro() {
		return "registroong.html";
	}
	@PostMapping("/crearong")						//CREA ONG
	public String cargaong(@RequestParam String marca, @RequestParam String name,@RequestParam String ape,
			@RequestParam String user,@RequestParam String pass,@RequestParam(value="file", required=false) MultipartFile file
			,ModelMap mod)
	{
	
		
		try {
		
			Usuario u =ServiUsu.seteoUsuario(user, pass, Rol.ONG); 			//crea y setea usuario con atrib del front
			
			Foto foto=null;
			if(file !=null) {												// si recibe foto, la guarda
				foto = ServiFoto.guardarfoto(file);//solo sube la foto al server(no persiste la url)
				//se hizo asi porque sino no se guarda relacionada a la ong y no sabemos como funcionara la relacion con jointable
				
			}
			
			ServiOng.crearOng(u, marca, name, ape,foto);				//crea y setea ONG
			
			return "redirect:/login";									//si se crea bien, te redirige a login ONG
		} catch (Exception e) {
			mod.put("error", e.getMessage());						// sino, lanza excepción
			e.printStackTrace();
			return "registroong.html";
		}
		
	}
	
	@PreAuthorize("isAuthenticated()")							//loguead@, se puede ver list de ONG 
	@GetMapping("/listarong")
	public String listarong(Model mod) {
		List<ONG> lo=ServiOng.listarONGactivas();				
		mod.addAttribute("listarOng",lo);						//añade al modelmap lita de ong
		return "listaong";
		//return "redirect:/";
	}
	@PreAuthorize("isAuthenticated()")								//si esta loguead@ puede aliminar (alta=false) ONG
	@GetMapping("/eliminarong/{id_ong}")
	public String eliminarong(@PathVariable Long id_ong) {
		ServiOng.borrarONG(id_ong);
		return "redirect:/listarTodo";
	}

}
