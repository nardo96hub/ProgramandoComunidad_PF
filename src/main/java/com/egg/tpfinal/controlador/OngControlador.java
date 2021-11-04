package com.egg.tpfinal.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.tpfinal.entidades.ONG;
import com.egg.tpfinal.entidades.Usuario;
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
	
	
	@GetMapping("/crearong")
	public String registro() {
		return "registroong.html";
	}
	@PostMapping("/crearong")
	public String cargaong(@RequestParam String marca, @RequestParam String name,@RequestParam String ape,@RequestParam String user,@RequestParam String pass)
	{
		
		try {
		
			Usuario u =ServiUsu.seteoUsuario(user, pass, Rol.ONG);//Falta validar Usuario
			
			
			ServiOng.crearOng(u, marca, name, ape);
			System.out.println("Se creo bien");
			return "redirect:/";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "redirect:/ong/crearong";
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
	@GetMapping("/listarong")
	public String listarong(Model mod) {
		List<ONG> lo=ServiOng.listarONGactivas();
		mod.addAttribute("listarOng",lo);
		//return "listarong";
		return "redirect:/";
	}
	
	@GetMapping("/eliminarong/{id}")
	public String eliminarong(@PathVariable Long id) {
		ServiOng.borrarONG(id);
		return "redirect:/";
	}

}
