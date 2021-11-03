package com.egg.tpfinal.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	
	//Metodo para setear usuario
	public Usuario seteoUsuario(String email,String contrasena,Rol rol) {
		Usuario u = new Usuario();
		u.setContrasena(contrasena);
		u.setEmail(email);
		u.setRol(rol);
		u.setAlta(true);
		
		return u;
	}
	
	public void crearUsuario(String email,String contrasena, Rol rol) throws Exception {
		Usuario u=RepoUsu.buscarPorEmail(email);
		if(u!=null)
			throw new Exception("Usuario ya registrado");
		else {
			u=new Usuario();
			//guardarUsuario(u, email, contrasena,  rol);
		}
	}
	
	public void editarUsuario(String email, String contrasena,Rol rol) throws Exception{
		Usuario u=RepoUsu.buscarPorEmail(email);
		//guardarUsuario(u,email,contrasena,rol);
		//editar usuario y volverselo a setear a developer o ong y persistir en esas entidades
		
	}
	
	public void editarAlta(Long id) {
		Usuario u = getUsuario(id);
		if(u!=null) {
			u.setAlta(!u.getAlta());
			RepoUsu.save(u);
		}
	}
	
	public void eliminarUsuario(Long id) {
		editarAlta(id);
	}
	
	public List<Usuario> mostrarUsuarioActivos(){
		return RepoUsu.listarUsuariosActivos();
	}
	
	public List<Usuario> mostrarUsuarios(){
		return RepoUsu.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario u=RepoUsu.buscarPorEmail(email);
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
	
	public Usuario getUsuario(Long ID) {
		Optional<Usuario> u = RepoUsu.findById(ID);
		return u.get();
	}
	
	public Usuario getUsuarioEmail(String email) {
		Usuario u = RepoUsu.buscarPorEmail(email);
		return u;
	}
	
	public void saveUsuario(Usuario usuario) {
		RepoUsu.save(usuario);
	}
	
	
}
