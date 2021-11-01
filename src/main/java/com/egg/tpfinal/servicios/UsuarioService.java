package com.egg.tpfinal.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.egg.tpfinal.entidades.Usuario;
import com.egg.tpfinal.repositorios.UsuarioRepository;

import enumeracion.Rol;

@Service
public class UsuarioService  implements UserDetailsService{
	@Autowired
	private UsuarioRepository RepoUsu;
	
	
	//Metodo para guardar en crear/editar
	public void guardarUsuario(Usuario u,String email,String contrasena,Rol rol) {
		
		u.setContrasena(contrasena);
		u.setEmail(email);
		u.setRol(rol);
		
		u.setAlta(true);
		
		//RepoUsu.save(u);  //Falta Repositorio sea hija de JPARepository
		
	}
	
	@SuppressWarnings("unused") //Luego se borraria 
	public void crearUsuario(String email,String contrasena, Rol rol) throws Exception {
		//Usuario u=RepoUsu.buscarUsuarioEmail(email);  Cambiar cuando este los repositorio
		
		Usuario u =null; //Se borraria cuando este implementado los Repositorios 
	
		if(u!=null)
			throw new Exception("Usuario ya registrado");
		else {
			u=new Usuario();
			guardarUsuario(u, email, contrasena,  rol);
		}
	}
	
	public void editarUsuario(String email, String contrasena,Rol rol) throws Exception{
		//Usuario u=RepoUsu.buscarUsuarioEmail(email);
		Usuario u = new Usuario(); // Se borra al implementar repositorio
		guardarUsuario(u,email,contrasena,rol);
	}
	
	
	public void editarAlta(Long id) {
		//Usuario u=RepoUsu.BuscarId(id);
		Usuario u =new Usuario(); // Se borra en el futuro
		
		if(u!=null) {
			u.setAlta(!u.getAlta());
			//RepoUsu.save(u);
		}
	}
	
	public void eliminarUsuario(Long id) {
	
		
		editarAlta(id);
		
		
	}
	
	
	
	/*
	 
	  Faltan metodos de obtener Usuarios segun las consultas 
	  
	 */
	
	public List<Usuario> mostrarUsuarioActivos(){
	//	return RepoUsu.ListarUsuarioActivo;
		return null;
	}
	
	public List<Usuario> mostrarUsuarios(){
		//return RepoUsu.findAll();
		return null;
	}

	
	
	
	
	
	
	
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//Usuario u=RepoUsu.buscarUsuario(email);
		Usuario u=new Usuario();
		if(u==null) {
			return null;
		}
		
		List<GrantedAuthority> permisos = new ArrayList<>();
		GrantedAuthority p1=new SimpleGrantedAuthority("ROLE_"+u.getRol());
		
		permisos.add(p1);
		
		ServletRequestAttributes attr=(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session=attr.getRequest().getSession(true);
		session.setAttribute("usuariosession", u);
		
		
		return new User(email,u.getContrasena(),permisos);
	}
	
}
