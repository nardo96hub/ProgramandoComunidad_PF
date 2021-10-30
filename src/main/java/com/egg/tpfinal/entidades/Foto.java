package com.egg.tpfinal.entidades;

import javax.persistence.Entity;

@Entity
public class Foto {

@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id_foto;
}

}
