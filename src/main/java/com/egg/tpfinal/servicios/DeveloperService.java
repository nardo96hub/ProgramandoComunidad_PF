package com.egg.tpfinal.servicios;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egg.tpfinal.entidades.*;
import com.egg.tpfinal.repositorios.DeveloperRepository;
import com.egg.tpfinal.repositorios.FotoRepository;

@Service
public class DeveloperService {

	@Autowired
	private DeveloperRepository DevRepo;

	@Autowired
	private UsuarioService userServi;

	@Autowired
	private FotoRepository fotoRepo;
	
	/* JDBC lenguaje directo a BBDD (new EntityManager)
	 * Spring (API NO directa a BBDD) extiende de Jpa.x eso no van mas los EntityManager xq extiende de JPA, 
	 * este baja sus utilidades- Utiliza @Etiquetas p comunicarse con BBDD (leng NO directo)
	 * Propagation.REQUIRED =  consulta lógica (código, ambito externo) a BBDD  tiene 3 opciones:
	 * 1- existe consula fisica (BBDD, ámbito interno) en curso, continua con la misma
	 * 2-existe  consula fisica (BBDD, ámbito interno) curso, anula esa,y luego crea una nueva
	 * 3-no exite  consula fisica (BBDD, ámbito interno), la crea
	 * 
	   rollBack= ctrl Z en BBDD si alguna transc no se puede ralizar (exception-error)
	   controlador invoca a crearDev y,a su vez, ésta invoca a guardarDev
		https://docs.google.com/document/d/1qjo8ND-mDg05a4QHfcA0Y-S8lSzT-XtgzkXLjYOYZfk/edit
	 */

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })     
	public void guardarDeveloper(Developer dev, Usuario usuario,  String nombre, String apellido, String tel, Foto foto,
			ArrayList<String> tec) throws Exception  {
		
		validar(usuario,tel,nombre,apellido, foto,tec);
		
		dev.setTecnologias(new ArrayList<String>());	
		dev.setNombre(nombre);
		dev.setApellido(apellido);
		dev.setAlta(true);
		dev.setTelefono(tel);									// TO-DO= tecnologias ENUM
		dev.setUsuario(usuario);
		dev.setFoto(foto);
		dev.setTecnologias(tec);	
		/*if (foto != null) {
			//fotoRepo.save(foto);
		}
		userRepo.save(dev.getUsuario());
		userServi.saveUsuario(usuario);*/
		DevRepo.save(dev);
	}
	

	@Transactional
	public void borrarDeveloper(Long ID) {
		EditarDeveloperActivo(ID);
	}

	@Transactional
	public void editarDeveloper(Long ID, Usuario usuario, String nombre, String apellido, String tel, Foto foto,
			ArrayList<String> tec) throws Exception {
		Developer dev = getDeveloper(ID);
		//validar(usuario,tel,nombre,apellido, foto,tec);
		guardarDeveloper(dev, usuario, nombre, apellido, tel, foto, tec);
	}

	@Transactional
	public void crearDeveloper(Usuario usuario, String nombre, String apellido, String tel, Foto foto,
			ArrayList<String> tec) throws Exception {
		Developer dev = DevRepo.buscarPorEmail(usuario.getEmail());
		
		validar(usuario,tel,nombre,apellido, foto, tec);
		if (dev == null) {
			dev = new Developer();
			guardarDeveloper(dev, usuario,  nombre, apellido, tel, foto, tec);
		} else {
			throw new Exception("El email ya existe");
		}
	}

	@Transactional(readOnly = true)   								//aviso q se usará SOLO LECTURA BBDD
	public List<Developer> listarTodosDeveloper() {
		return DevRepo.findAll();
	}

	@Transactional(readOnly = true)
	public List<Developer> listarDeveloperActivos() {
		return DevRepo.listarDeveloperActivos();
	}

	@Transactional
	public void EditarDeveloperActivo(Long ID) {
		Developer dev = getDeveloper(ID);
		if (dev != null) {
			dev.setAlta(!dev.getAlta()); 
			//invierte alta-baja o viceversa
			//DevRepo.save(dev);
		}
	}

	@Transactional(readOnly = true)
	public Developer getDeveloper(Long ID) {			//REPOSITORIO USA LA FUNCION DE JPA-RETORNA DEVEL
		Optional<Developer> d = DevRepo.findById(ID);
		return d.get();
	}

	@Transactional
	public void saveDeveloper(Developer developer) {
		DevRepo.save(developer);
	}

	@Transactional(readOnly = true)
	public Developer getDeveloperporIdUser(Long idUser) { //SE USA UNA QUERY-RETORNA DEVELOP
		return DevRepo.buscarPorIdUsuario(idUser);
	}
	
	
	public void validar(Usuario usuario, String tel, String nombre, String apellido, Foto foto,
			ArrayList<String> tec) throws Exception {
																//TO-DO =ACOMODAR ORDEN DE IF´SSSSSSSSSS
		if(usuario==null) { 
			throw new Exception("Usuario no creado");
		}
		if(usuario.getEmail().isEmpty()) {						//Cambiar isBlank()
			throw new Exception("Email no válido");
		}
		if(usuario.getContrasena().isEmpty()) {					//Cambiar isBlank()
			throw new Exception("Contraseña no válida - Tamaño: Mínimo 6 caracteres");
		}
		if(tel.isEmpty() || tel.length()<6) { 			/*|| tel.matches("a-zA-Z") Cambiar isBlank()*/
			throw new Exception("Teléfono no válido (Tamaño: Mínimo 6 caracteres - Sólo se admiten números)");
		}
		if(nombre.isEmpty() || nombre.length()<2 || nombre.length()>20 /*|| !(nombre.matches("a-zA-Z"))*/) {//Cambiar isBlank()
			throw new Exception("Nombre no válido (Tamaño: Mínimo 2 caracteres / Máximo= 20 caracteres - Sólo se admiten letras)");
		}
		if(apellido.isEmpty() || apellido.length()<2 || apellido.length()>20 /*|| !(apellido.matches("a-zA-Z"))*/) {//Cambiar isBlank()
			throw new Exception("Apellido no válido (Tamaño: Mínimo 2 caracteres / Máximo= 20 caracteres - Sólo se admiten letras)");
		}
		/*if(foto == null) {
			foto.setUrl_foto("https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png");
		}*/
		if(tec.isEmpty() ||tec.size()==0) { 
			throw new Exception("No ingresó campos de tecnologías");
		}

	}


	public List<Developer> listarBusquedaDeveloperActivos(String buscar) {
		List<Developer> activos,busqueda;
		Boolean agregar;
		List<String> lista;
		activos=listarDeveloperActivos();
		busqueda=DevRepo.listaBusquedaDeveloperActivos("%"+buscar+"%");
		//Esto realiza la busqueda de tecnologias
		for (Developer developer : activos) {
			if(!busqueda.contains(developer)) {	//Si el developer no esta en la lista de busqueda analizo el lenguaje
				agregar=false; //Si esta en true agrego al busqueda
				lista=developer.getTecnologias();//Obtengo la lista de tecnologias si no esta
				for (String s : lista) {	//Recorro todas las tecnologias a ver si contiene por busqueda
					if(s.contains(buscar.toUpperCase())) {
						agregar=true;
					}
				}
				if(agregar) { //Si esta true agrego a la lista de resultados por busqueda
					busqueda.add(developer);
				}
			}
			
		}
		
		return busqueda;
	}
}
