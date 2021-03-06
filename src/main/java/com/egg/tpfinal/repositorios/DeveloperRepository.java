package com.egg.tpfinal.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.egg.tpfinal.entidades.Developer;



@Repository
public interface DeveloperRepository extends JpaRepository<Developer,Long> {   // 1°Param=object-2°Param=tipo ID
	
	@Query("SELECT d FROM Developer d WHERE d.alta = true")    				//
	public List<Developer> listarDeveloperActivos();
	
	@Query("SELECT d FROM Developer d WHERE d.nombre = :nombre1 AND d.apellido = :apellido1")
	public List<Developer> buscarPorNombreYApellido(@Param("nombre1") String nombre, @Param("apellido1") String apellido);
	
	@Query("SELECT d FROM Developer d WHERE d.usuario.email = :email1")
	public Developer buscarPorEmail(@Param("email1") String email);

	@Query("SELECT d FROM Developer d WHERE d.usuario.id_usuario = :idUsuario")
	public Developer buscarPorIdUsuario(@Param("idUsuario") Long id);
	
	/*@Query("SELECT d.tecnologias FROM Developer d WHERE d.id_developer = :idDeveloper")
	public List<Tecnologias> buscarTecnologiasporId(@Param("idDeveloper") Long id);*/

	@Query("SELECT d FROM Developer d where d.alta= true and (d.usuario.email LIKE :b OR d.nombre LIKE :b OR d.apellido LIKE :b OR d.telefono LIKE :b)")
	public List<Developer> listaBusquedaDeveloperActivos(@Param("b") String buscar);
	
	/*@Query("SELECT d FROM Developer d WHERE d.tecnologias.lenguaje = :tecno1 AND d.tecnologias.lenguaje = :tecno2 AND d.tecnologias.lenguaje = :tecno3")
	public List<Developer> buscarPor3Tecnologias(@Param("tecno1") String tecno1, @Param("tecno2") String tecno2, @Param("tecno3") String tecno3);
	
	@Query("SELECT d FROM Developer d WHERE d.tecnologias.lenguaje = :tecno1 OR d.tecnologias.lenguaje = :tecno2 OR d.tecnologias.lenguaje = :tecno3")
	public List<Developer> buscarPorAlguna3Tecnologias(@Param("tecno1") String tecno1, @Param("tecno2") String tecno2, @Param("tecno3") String tecno3);
	
	@Query("SELECT d FROM Developer d WHERE d.tecnologias.lenguaje = :tecno1")
	public List<Developer> buscarPorTecnologias(@Param("tecno1") String tecno);*/
	
}
