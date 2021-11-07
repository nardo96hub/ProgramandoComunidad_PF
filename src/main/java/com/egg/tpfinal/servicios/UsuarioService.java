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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
		String contraseniaEncriptada = new BCryptPasswordEncoder().encode(contrasena);
		u.setContrasena(contraseniaEncriptada);
		u.setEmail(email);
		u.setRol(rol);
		u.setAlta(true);
		
		return u;
	}
	
	@Transactional
	public void crearUsuario(String email,String contrasena, Rol rol) throws Exception {
		Usuario u=RepoUsu.buscarPorEmail(email);
		if(u!=null)
			throw new Exception("Usuario ya registrado");
		else {
			u=new Usuario();
			//guardarUsuario(u, email, contrasena,  rol);
		}
	}
	
	@Transactional
	public void editarUsuario(String email, String contrasena,Rol rol) throws Exception{
		Usuario u=RepoUsu.buscarPorEmail(email);
		//guardarUsuario(u,email,contrasena,rol);
		//editar usuario y volverselo a setear a developer o ong y persistir en esas entidades
		
	}
	
	@Transactional
	public void editarAlta(Long id) {
		Usuario u = getUsuario(id);
		if(u!=null) {
			u.setAlta(!u.getAlta());
			RepoUsu.save(u);
		}
	}
	
	@Transactional
	public void eliminarUsuario(Long id) {
		editarAlta(id);
	}
	
	@Transactional(readOnly=true)
	public List<Usuario> mostrarUsuarioActivos(){
		return RepoUsu.listarUsuariosActivos();
	}
	
	@Transactional(readOnly=true)
	public List<Usuario> mostrarUsuarios(){
		return RepoUsu.findAll();
	}

	@SuppressWarnings("unused")
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	//	System.out.println(email);
		Usuario u=RepoUsu.buscarPorEmail(email);
//		System.out.println(u.getContrasena());
//		System.out.println(u.getEmail());
		
		if(u!=null) {
			List<GrantedAuthority> permisos = new ArrayList<>();
			GrantedAuthority p1=new SimpleGrantedAuthority("ROLE_"+u.getRol().toString());
			permisos.add(p1);
			ServletRequestAttributes attr=(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session=attr.getRequest().getSession(true);
			session.setAttribute("usuariosession", u);
			return new User(email,u.getContrasena(),permisos);
		}else {
			
		throw new UsernameNotFoundException("error");
		
		}
		
		
	}
	
	@Transactional(readOnly=true)
	public Usuario getUsuario(Long ID) {
		Optional<Usuario> u = RepoUsu.findById(ID);
		return u.get();
	}
	
	@Transactional(readOnly=true)
	public Usuario getUsuarioEmail(String email) {
		Usuario u = RepoUsu.buscarPorEmail(email);
		return u;
	}
	
	@Transactional
	public void saveUsuario(Usuario usuario) {
		RepoUsu.save(usuario);
	}
	
	
}
