package com.egg.tpfinal.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Tecnologias {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	private Long	id_tecnologia;
	private String	lenguaje;



	
	
	public Long getId_tecnologia() {
		return id_tecnologia;
	}
	public void setId_tecnologia(Long id_tecnologia) {
		this.id_tecnologia = id_tecnologia;
	}
	public String getLenguaje() {
		return lenguaje;
	}
	public void setLenguaje(String lenguaje) {
		this.lenguaje = lenguaje;
	}
	@Override
	public String toString() {
		return "Tecnologias [id_tecnologia=" + id_tecnologia + ", lenguaje=" + lenguaje + "]";
	}
	
	
}
