package com.egg.tpfinal.controlador;
 
import java.util.ArrayList;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.egg.tpfinal.entidades.Developer;
import com.egg.tpfinal.entidades.Foto;
import com.egg.tpfinal.entidades.Tecnologias;
import com.egg.tpfinal.entidades.Usuario;
import com.egg.tpfinal.servicios.DeveloperService;
import com.egg.tpfinal.servicios.FotoService;
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
	@Autowired
	private FotoService ServiFoto;
 
 
	@GetMapping()
	public String mostrardev(ModelMap mod){
		List<Tecnologias> lt=ServiTec.listarTecnologiasUnicas();
		mod.addAttribute("listaTec", lt);
		return "registrodev.html";
	}
 
	@PostMapping("/cargardev")
	public String cargardev(@RequestParam List<String> lenguajes,
			@RequestParam String user,@RequestParam String pass, @RequestParam String name,
			@RequestParam String apellido,@RequestParam String tel,@RequestParam(value="file", required=false) MultipartFile file  ) {
		System.out.println("entre");
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
 
 
			Foto foto=null;
			if(file != null) {
				foto = ServiFoto.guardarfoto(file);//solo sube la foto al server(no persiste la url)
				//se hizo asi porque sino no se guarda relacionada al developer
			}
 
		//	ServiUsu.guardarUsuario(u, user, pass, Rol.DEVE);
			ServiDev.crearDeveloper(u, name, apellido, tel, foto, tecnologias); //crea y guarda
			return "redirect:/";
		} catch (Exception e) {
 
			e.printStackTrace();	
			return "redirect:/registrodev";
		}
		//return "redirect:/";
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/listardev")
	public String listardev(ModelMap mod) {
		List<Developer> ld= ServiDev.listarDeveloperActivos();
		mod.addAttribute("listaDev", ld);
		return "listadevelop";
	}
 
}
 