package com.egg.tpfinal.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.tpfinal.entidades.Tecnologias;
import com.egg.tpfinal.entidades.Usuario;
import com.egg.tpfinal.servicios.DeveloperService;
import com.egg.tpfinal.servicios.TecnologiasService;
import com.egg.tpfinal.servicios.UsuarioService;

import enumeracion.Rol;

@Controller
@RequestMapping("/registrodev")
public class DeveloperControlador {
	@Autowired
	private TecnologiasService ServiTec;
	@Autowired
	private DeveloperService ServiDev;
	@Autowired
	private UsuarioService ServiUsu;
	
	
	@GetMapping()
	public String mostrardev(ModelMap mod){
		List<Tecnologias> lt=ServiTec.listarTecnologiasUnicas();
		mod.addAttribute("listaTec", lt);
		return "registrodevtest.html";
	}
	
	@PostMapping()
	public String cargardev(@RequestParam List<String> lenguajes,@RequestParam String user,@RequestParam String pass, @RequestParam String name, @RequestParam String apellido,@RequestParam String tel) {
		try {
			List<Tecnologias> tecnologias= new ArrayList<Tecnologias>();
			for (String tec : lenguajes) { //lenguajes es string, se lo pasa a tipo de tecnologias
				Tecnologias tecn=new Tecnologias();
				tecn.setLenguaje(tec);
				tecnologias.add(tecn);
			//	System.out.println(tec);
			}
			/*System.out.println("\nLista de tecnologias");
			for (Tecnologias t : tecnologias) {
				System.out.println(t);
			}*/
			Usuario u = ServiUsu.seteoUsuario(user, pass, Rol.DEVE);
		//	ServiUsu.guardarUsuario(u, user, pass, Rol.DEVE);
			ServiDev.crearDeveloper(u, name, apellido, tel, null, tecnologias); //crea y guarda
			return "redirect:/";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
			return "redirect:/registrodev";
		}
		//return "redirect:/";
	}
	
}
