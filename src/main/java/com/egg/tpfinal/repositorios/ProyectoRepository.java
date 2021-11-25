package com.egg.tpfinal.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.egg.tpfinal.entidades.Developer;
import com.egg.tpfinal.entidades.Proyecto;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long>{
	
	@Query("SELECT p FROM Proyecto p WHERE p.titulo LIKE %:titulo1%")
	public Proyecto buscarPorTitulo(@Param("titulo1") String titulo);
	
	@Query("SELECT p FROM Proyecto p WHERE p.alta = true")
	public List<Proyecto> buscarPorAlta();
	
	@Query("SELECT p FROM Proyecto p WHERE p.admitir_deve = true")
	public List<Proyecto> buscarPorDisponibilidad();
	
	@Query("SELECT p FROM Proyecto p WHERE p.admitir_deve = false")
	public List<Proyecto> buscarPorDisponibilidadFalse();
	
	@Query("SELECT p.developer from Proyecto p WHERE p.id_proyecto = :id_proyecto")
	public List<Developer> listadeveloper(@Param("id_proyecto") Long id);
	
	@Query("SELECT p FROM Proyecto p WHERE p.ong.id = :idONG")
	public List<Proyecto> buscarPorIdONG(@Param("idONG") Long id);
	
	@Query("SELECT p FROM Proyecto p where p.alta=true and (p.titulo LIKE :b OR p.cuerpo LIKE :b OR p.ong.usuario.email LIKE :b)")
	public List<Proyecto> busqueda(@Param("b") String buscar);
	
	/*@Query("SELECT p FROM Proyecto p Join Developer d WHERE p.developer.d.id_developer = :idDev")
	public List<Proyecto> buscarPorIdDeveloper(@Param("idDev") Long id);
	
	CONSULTAR EN EL EXCEL
	*/
	
	/*public List<Proyecto> findByDeveloper(List<Developer> developer);*/
	
}
