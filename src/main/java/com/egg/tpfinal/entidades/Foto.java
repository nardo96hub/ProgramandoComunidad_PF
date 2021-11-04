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
private String url_foto;

public Foto(String url_foto) {
	
	this.url_foto = url_foto;
}
public Long getId_foto() {
	return id_foto;
}
public String getUrl_foto() {
	return url_foto;
}
public void setId_foto(Long id_foto) {
	this.id_foto = id_foto;
}
public void setUrl_foto(String url_foto) {
	this.url_foto = url_foto;
}

}


