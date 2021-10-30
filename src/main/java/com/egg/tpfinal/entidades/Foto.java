package com.egg.tpfinal.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Foto {

@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id_foto;
}


