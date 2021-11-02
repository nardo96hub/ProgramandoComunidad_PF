package com.egg.tpfinal.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egg.tpfinal.entidades.ONG;

public interface OngRepository extends JpaRepository <ONG, Long>{

	
	@Query("SELECT o FROM ong o WHERE o.alta = true ")
	public List<ONG> listarONGactivas();
	
	@Query("SELECT o FROM ong o WHERE o.marca=:marca1")
	public List<ONG> marcaOng(@Param("marca1") String marca);
	
	@Query("SELECT o FROM ong o WHERE o.marca LIKE : %marca2%")
	public List<ONG> BuscarPorMarca(@Param("marca2") String marca);
	
	@Query("SELECT o FROM ong o WHERE o.nombre_rep=: nombre AND o.apellido_rep= :apellido")
	public List<ONG>buscarporRepresentante(@Param("nombre") String nombre, @Param("apellido")String apellido );
	
	@Query("SELECT o FROM ong WHERE o.usuario.id_usuario= :idUsuario")
	public ONG buscarONGporidUsuario (@Param("idUsuaruio") Long id);
	
	@Query("SELECT o FROM ong WHERE o.proyecto.id_proyecto= :idproyecto")
    public List<ONG> proyectosPorOng(@Param("idproyecto")Long Id );
	
	
}
 