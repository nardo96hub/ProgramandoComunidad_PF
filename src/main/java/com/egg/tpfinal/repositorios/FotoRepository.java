package com.egg.tpfinal.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.tpfinal.entidades.Foto;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long>{

}
