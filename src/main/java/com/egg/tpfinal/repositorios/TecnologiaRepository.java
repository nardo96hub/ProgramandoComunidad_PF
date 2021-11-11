package com.egg.tpfinal.repositorios;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.egg.tpfinal.entidades.Tecnologias;

@Repository
public interface TecnologiaRepository extends JpaRepository<Tecnologias, Long> {
	
	@Query("SELECT t FROM Tecnologias t WHERE t.lenguaje = :lenguaje1 ")
	public Tecnologias buscarPorLenguaje(@Param("lenguaje1") String lenguaje);
	
	@Query("SELECT DISTINCT  t from Tecnologias t group by t.lenguaje")           //evita duplicar en front checkbox
	public List<Tecnologias> listarLenguajes();									 // TO-DO: mapear p NO duplicar leng en BBDD 

}
