package com.egg.tpfinal.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.egg.tpfinal.entidades.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	@Query("SELECT u FROM Usuario u WHERE u.alta = true")
	public List<Usuario> listarUsuariosActivos();
	
	@Query("SELECT u FROM Usuario u WHERE u.email = :email1")					//= funcion q la de abajo
	public Usuario buscarPorEmail(@Param("email1") String email);				

	public Usuario findByEmail(String email);									//devuelve usuario
	
	@Query("SELECT u.email FROM Usuario u WHERE u.email = :email1")				//devuelve string email 
	public String findByStringEmail(String email);
	
	@Query("SELECT u FROM Usuario u WHERE u.rol = :rol1 OR u.rol = :rol2")
	public List<Usuario> buscarPorRol(@Param("rol1") String rol1, @Param("rol2") String rol2);
	
}
