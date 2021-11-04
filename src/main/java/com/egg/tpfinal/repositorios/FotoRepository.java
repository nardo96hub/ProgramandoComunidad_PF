package com.egg.tpfinal.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.egg.tpfinal.entidades.Foto;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long>{

	@Query("SELECT max(id_foto) from Foto")
	Long findTopByOrderById_fotoDesc();
	
	
}
