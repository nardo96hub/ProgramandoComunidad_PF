package com.egg.tpfinal.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import com.egg.tpfinal.entidades.Developer;
import com.egg.tpfinal.entidades.ONG;
import com.egg.tpfinal.entidades.Usuario;
import com.egg.tpfinal.repositorios.UsuarioRepository;
import com.egg.tpfinal.repositorios.DeveloperRepository;
import com.egg.tpfinal.repositorios.OngRepository;

import enumeracion.Rol;

@Service
public class UsuarioService implements UserDetailsService {
	@Autowired
	private UsuarioRepository RepoUsu;
	@Autowired
	private DeveloperRepository dr;
	@Autowired
	private OngRepository or;

	@Transactional // Metodo para setear usuario
	public Usuario seteoUsuario(String email, String contrasena, Rol rol) throws Exception {
		Usuario u = new Usuario();
		// validarDatos(contrasena, email);
		validarCredenciales(contrasena, email);
		String contraseniaEncriptada = new BCryptPasswordEncoder().encode(contrasena); 		//Recibo una contraseña y con esta linea se encripta guardandola en BDD 
		u.setContrasena(contraseniaEncriptada);
		u.setEmail(email);
		u.setRol(rol);
		u.setAlta(true);
		return u;
	}
	
	public void validarCredenciales(String contrasena, String email) throws Exception  {
		if (contrasena.isEmpty()) {
			throw new Exception("Contraseña no válida");
		}
		if (email.isEmpty()) {
			throw new Exception("Email no válido");
		}
	}

	@Transactional
	public void crearUsuario(String email, String contrasena, Rol rol) throws Exception {	//No se usa se reemplazo por seteoUsuario
		Usuario u = RepoUsu.buscarPorEmail(email);
		if (u != null)
			throw new Exception("Usuario ya registrado");
		else {
			u = new Usuario();
			// guardarUsuario(u, email, contrasena, rol);
		}
	}

	@Transactional
	public void editarUsuario(String email, String contrasena, Rol rol) throws Exception {  		//Adaptar cuando se haga editar
		Usuario u = RepoUsu.buscarPorEmail(email);
		// guardarUsuario(u,email,contrasena,rol);
		// editar usuario y volverselo a setear a developer o ong y persistir en esas
		// entidades
	}

	@Transactional
	public void editarAlta(Long id) {					//Invierte  el estado
		Usuario u = getUsuario(id);
		if (u != null) {
			u.setAlta(!u.getAlta());
			RepoUsu.save(u);
		}
	}

	@Transactional
	public void eliminarUsuario(Long id) {				//Elimino alta
		editarAlta(id);
	}

	@Transactional(readOnly = true)						//Muestra usuariosActivos
	public List<Usuario> mostrarUsuarioActivos() {
		return RepoUsu.listarUsuariosActivos();
	}

	@Transactional(readOnly = true)						//Muestro Todos los Usuarios
	public List<Usuario> mostrarUsuarios() {
		return RepoUsu.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {	//Metodo encargado de realizar login
	
		Usuario u = RepoUsu.buscarPorEmail(email);


		if (u != null) {
			List<GrantedAuthority> permisos = new ArrayList<>();						//Guarda Lista de roles supuestamente		
			GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + u.getRol().toString());	//Carga el Rol
			permisos.add(p1);
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);		//Defino la session		
			session.setAttribute("usuariosession", u);
			return new User(email, u.getContrasena(), permisos);			//Creo Nuevo usuario para login
		} else {	
			throw new UsernameNotFoundException("error");					//Por si ocurre problema
		}
	}

	@Transactional(readOnly = true)
	public Usuario getUsuario(Long ID) {
		Optional<Usuario> u = RepoUsu.findById(ID);							//Retorna usuario por Id
		return u.get();
	}

	@Transactional(readOnly = true)
	public Usuario getUsuarioEmail(String email) {							//Busca usuario por Email
		Usuario u = RepoUsu.buscarPorEmail(email);
		return u;
	}

	@Transactional
	public void saveUsuario(Usuario usuario) {								//Guarda usuario en BDD
		RepoUsu.save(usuario);
	}

	/*public String  usuarioConectado(Usuario u) {						
		System.out.println("Rol : "+u.getRol());
		if(u.getRol()==Rol.DEVE) {										
			Developer developer = dr.buscarPorEmail(u.getEmail());		//según rol, se obtiene nombre q figura cuando se loguea
			return developer.getNombre()+" "+developer.getApellido();
		}
		if(u.getRol()==Rol.ONG) {
			ONG ong=or.buscarPorEmail(u.getEmail());
			return ong.getMarca();
		}
		if(u.getRol()==Rol.ADMIN) {
			Developer dev = dr.buscarPorEmail(u.getEmail());			//Admin se cambia en BBDD, de ONG o Develop => busco Develop, si esta return NombreDevelop;
			if(dev !=null) {											// sino busco ONG, si esta, return MarcaONG. Si hay ERROR, return NULL
				return dev.getNombre()+" "+dev.getApellido();
			}else {
				ONG ong=or.buscarPorEmail(u.getEmail());
				return ong.getMarca();
			}
		}
		
		
		return null;
	}*/
	
	public Usuario usuarioconectado(){										//Función que trae usuario conectado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
	if (!(authentication instanceof AnonymousAuthenticationToken)) {        //LUUUUUUU????????????
		String email = authentication.getName();
		Usuario u = RepoUsu.buscarPorEmail(email);
                    return RepoUsu.save(u);
	}else{
                return null; 
            }                
    }
	
	 public String nombre(){
	        Usuario usuario = usuarioconectado();						//Trae usuario logueado	
	      
	        if(usuario.getRol() != Rol.ONG) {							//ROL= Dev o ROL=Admin
	        	 Developer d = dr.buscarPorIdUsuario(usuario.getId_usuario());
       	 
	        	 if(d!=null) {
	        		  if (usuario.getId_usuario() == d.getUsuario().getId_usuario()) {
	                 return d.getNombre();
	                 }
	        	 }  
	        }
	        if(usuario.getRol() != Rol.DEVE) {							//ROL=ONG 0 ROL=Admin
	        	  ONG o = or.buscarPorEmail(usuario.getEmail());
	        	
	        	  System.out.println(o);
	        	  if(o!=null) {
	        		   if (usuario.getId_usuario() == o.getUsuario().getId_usuario()) {
	                  return o.getMarca();
	        		   }
	        	    } 	 
	        	}
	  
	        return null;
	 }
	
}

	


