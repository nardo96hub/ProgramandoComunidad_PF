package com.egg.tpfinal.entidades;
import java.util.List;


import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;




@Entity
public class Developer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	private Long id_developer;
	
	private String	nombre;
	private String	apellido;
	private Boolean	alta;
	private String telefono;
	@OneToOne
	private Usuario usuario;
	@OneToOne
	private Foto foto;  

	@OneToMany(cascade = CascadeType.ALL)

	private List<Tecnologias>tecnologias;
	
	
	
	public Long getId_developer() {
		return id_developer;
	}
	public void setId_developer(Long id_developer) {
		this.id_developer = id_developer;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public Boolean getAlta() {
		return alta;
	}
	public void setAlta(Boolean alta) {
		this.alta = alta;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Foto getFoto() {
		return foto;
	}
	public void setFoto(Foto foto) {
		this.foto = foto;
	}
	public List<Tecnologias> getTecnologias() {
		return tecnologias;
	}
	public void setTecnologias(List<Tecnologias> tecnologias) {
		this.tecnologias = tecnologias;
	}
	@Override
	public String toString() {
		return "Developer [id_developer=" + id_developer + ", nombre=" + nombre + ", apellido=" + apellido + ", alta="
				+ alta + ", telefono=" + telefono + ", usuario=" + usuario + ", foto=" + foto + ", tecnologias="
				+ tecnologias + "]";
	}
	
		
	
}
