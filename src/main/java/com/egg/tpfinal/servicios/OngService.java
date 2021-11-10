package com.egg.tpfinal.servicios;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.egg.tpfinal.entidades.*;
import com.egg.tpfinal.repositorios.FotoRepository;
import com.egg.tpfinal.repositorios.OngRepository;
import com.egg.tpfinal.repositorios.ProyectoRepository;
import enumeracion.Rol;

@Service
public class OngService {

	@Autowired
	private OngRepository ONGRepo;
	@Autowired
	private UsuarioService ServiUsu;
	@Autowired
	private FotoRepository fotoRepo;
	@Autowired
	private ProyectoRepository ProyRepo;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void guardarOng(ONG ong, String marca, String nombre_rep, String apellido_rep, Usuario usuario, Foto foto) throws Exception {
		 validarDatos(marca, nombre_rep,apellido_rep,usuario,foto);
		ong.setMarca(marca);
		ong.setNombre_rep(nombre_rep);
		ong.setApellido_rep(apellido_rep);
		ong.setUsuario(usuario);
		ong.setAlta(true);
		ong.setFoto(foto);
		ServiUsu.saveUsuario(usuario);
		if (foto != null) {
			fotoRepo.save(foto);
		}
		ONGRepo.save(ong);
	}

	/*
	 * public void validarDatos(String marca, String nombre_rep) throws Exception{
	 * if (marca == null || marca.isEmpty()) { throw new
	 * Exception("El nombre de la ONG no es válido"); } if (marca ==
	 * ONGRepo.marcaOngString(marca)) { throw new
	 * Exception("El nombre de la ONG ya se encuentra registrado"); } Pattern
	 * pattern = Pattern.compile("[^a-zA-Z'\\-\\s]"); boolean caracterInvalido =
	 * false; if(!caracterInvalido == pattern.matcher(nombre_rep).find()) { throw
	 * new Exception("Carácter inválido"); }
	 * 
	 * 
	 * }
	 */

	@Transactional
	public void borrarONG(Long ID) {
		EditarONGActivo(ID);
	}

	@Transactional
	private void EditarONGActivo(Long ID) {
		ONG ong = getONG(ID);
		if (ong != null) {
			ong.setAlta(!ong.getAlta());
			ONGRepo.save(ong);
		}
	}

	@Transactional(readOnly = true)
	public List<ONG> listartodaslasONG() {
		return ONGRepo.findAll();
	}

	@Transactional(readOnly = true)
	public List<ONG> listarONGactivas() {
		return ONGRepo.listarONGactivas();
	}

	@Transactional
	public void editarOng(Long ID, String marca, String nombre_rep, String apellido_rep, Usuario usuario, Foto foto) throws Exception {
		ONG ong = getONG(ID);
		guardarOng(ong, marca, nombre_rep, apellido_rep, usuario, foto);

	}

	@Transactional(readOnly = true)
	public ONG getONG(Long ID) {
		Optional<ONG> ong = ONGRepo.findById(ID);
		return ong.get();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void crearOng(Usuario usuario, String marca, String nombre_rep, String apellido_rep, Foto foto)
			throws Exception {
		ONG ong = ONGRepo.marcaOng(marca);// Se puede cambiar por email usuario
		if (ong == null) {
			ong = new ONG();
			guardarOng(ong, marca, nombre_rep, apellido_rep, usuario, foto);
		} else {
			throw new Exception("Ya se encuentra la Ong en BDD");
		}
	}

	@Transactional
	public void saveOng(ONG ong) {
		ONGRepo.save(ong);
	}

	@Transactional(readOnly = true)
	public Optional<ONG> buscarONGporidUsuario(Long ID) {
		// return ONGRepo.buscarONGporidUsuario(ID);
		return ONGRepo.findById(ID);
	}

	@Transactional(readOnly = true)
	public ONG buscarONGporUsuario(Usuario usuario) {
		return ONGRepo.findByUsuario(usuario);
	}

	@Transactional
	public void agregarProyectos(ONG ong, Proyecto p) {
		if (!p.getAdmitir_deve()) {
			List<Proyecto> proyec = ong.getPublicaciones();
			if (!proyec.contains(p)) {
				proyec.add(p);
				ong.setPublicaciones(proyec);
				saveOng(ong);
			}
		} else {
			p.setAdmitir_deve(false);
			ProyRepo.save(p);
			agregarProyectos(ong, p);
		}
	}
	public void validarDatos(String marca, String nombre, String apellido, Usuario usuario, Foto foto)  throws Exception{
		// Revisar Orden de if segun la prioridad en crear ong
		
		if(marca.isEmpty() || marca.length()<4){
			throw new Exception("Marca no registrada o ingreso tamaño < 4"); //Cambiar isBlank()
		}
		if(nombre.isEmpty() || nombre.length()<4 || nombre.length()>20) {
			throw new Exception("Nombre no registrada o ingreso tamaño < 4 o tamaño>20");
		}
		if(apellido.isEmpty() ||(apellido.length()<4 || apellido.length()>20)) {
			throw new Exception("Apellido no registrada o ingreso tamaño < 4 o tamaño>20");
		}
		if(usuario==null) {
			throw new Exception("Usuario no registrado");
		}
		if(usuario.getEmail().isEmpty() || usuario.getEmail().length()<4 || (!usuario.getEmail().contains(".")|| !usuario.getEmail().contains("@") )) {
			throw new Exception("Email no registrada o ingreso tamaño < 4 o incluye un email sin punto o @");
		}
		if(usuario.getContrasena().isEmpty() ) {
			throw new Exception("Contraseña no registrada");
		}
		if(foto==null) {
			throw new Exception("Foto no ingresada ");
		}
		
	
		
		
	}
}
