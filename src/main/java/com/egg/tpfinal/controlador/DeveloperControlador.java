package com.egg.tpfinal.controlador;
 
import java.util.ArrayList;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
 
@RequestMapping("/registrodev") 			//RequestMapping si esta debajo de Controller es la url padre 
public class DeveloperControlador {
	@Autowired									//Autowired instancia el objeto,  esto evita hacer new Objeto()
	private TecnologiasService ServiTec;
	@Autowired
	private DeveloperService ServiDev;
	@Autowired
	private UsuarioService ServiUsu;
	@Autowired
	private FotoService ServiFoto;
 
 
	@GetMapping()								//GetMapping retorna siempre un html en este caso no lleva direccion porque es /registrodev
	public String mostrardev(ModelMap mod){		//Por cada model/ModelMap se le envia informacion del back al front ej datos de base de datos o errores 
		List<Tecnologias> lt=ServiTec.listarTecnologiasUnicas();	//
		mod.addAttribute("listaTec", lt);							// Buscar Las tecnologias en la BASE DE DATOS 
		return "registrodev.html";
	}
/* @RequestParam es si envian desde un formulario con etiqueta name o si se usa Objetos con th (Lo que intentaron pame,fede y tami)
	El nombre en el parametro del controlador se tiene que llamar igual que el name en el html
	
	@PathVariable  Es si se le envia un valor desde el html como en el caso de un ID
	
	Estas 2 etiquetas son fundamentales en los parametros de controladores 
	*/
	

	@PostMapping("/cargardev")
	public String cargardev(@RequestParam List<String> lenguajes,
			@RequestParam String user,@RequestParam String pass, @RequestParam String name,
			@RequestParam String apellido,@RequestParam String tel,@RequestParam(value="file", required=false) MultipartFile file, ModelMap modelo) throws Exception{
		try {
			List<Tecnologias> tecnologias= new ArrayList<Tecnologias>();	
			
			for (String tec : lenguajes) { //lenguajes es string, se lo pasa a tipo de tecnologias
				 
				Tecnologias tecn=new Tecnologias();
				tecn.setLenguaje(tec);
				tecnologias.add(tecn);
				//return "redirect:/registrodev";
				
			}
			
			Usuario u = ServiUsu.seteoUsuario(user, pass, Rol.DEVE);		//Creo y guardo nuevo usuario
			Foto foto=null;													//Es necesario porque ServiFoto retorna una foto
			
			if(file != null) {
				foto = ServiFoto.guardarfoto(file);//solo sube la foto al server(no persiste la url)
				//se hizo asi porque sino no se guarda relacionada al developer
			}
			
			ServiDev.crearDeveloper(u, name, apellido, tel, foto, tecnologias); //crea y guarda
			return "redirect:/login";  			//Si todo funciono regresa al index 
		} catch (Exception e) {
			List<Tecnologias> lt=ServiTec.listarTecnologiasUnicas();		//Esta linea y la de abajo esta para que vuelva a cargar las tecnologias sino no lo hace
			modelo.addAttribute("listaTec", lt);
			modelo.put("error", e.getMessage());					//Mando error al html si existio, el 'error' debe coincidir en el html
			e.printStackTrace();									//Muestro error en consola
			return "registrodev.html";
		}
		
	}
	
	//Lista los Developer Activos (alta=true)
	@PreAuthorize("isAuthenticated()")	//Es una etiqueta de Spring security no se puede acceder a la url si no se esta Logueado 
	@GetMapping("/listardev")
	public String listardev(ModelMap mod) {
		List<Developer> ld= ServiDev.listarDeveloperActivos();
		mod.addAttribute("listaDev", ld);
		return "listadevelop";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/eliminardev/{id_developer}")
	public String eliminardev(@PathVariable Long id_developer) {
		System.out.println("ID:");
		System.out.println(id_developer);
		ServiDev.borrarDeveloper(id_developer);
		return "redirect:/listarTodo";
	}
 
}
 