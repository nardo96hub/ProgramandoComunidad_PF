package com.egg.tpfinal.entidades;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class ONG {
	
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)

		private Long id_ong;
		
		private String marca;
		private String	nombre_rep;
		private String	apellido_rep;
		private Boolean	alta;
		@OneToOne
		private Usuario	usuario;
		@OneToOne
		private Foto foto;
		@OneToMany
		private List<Proyecto> publicaciones;
		
		
		public Long getId_ong() {
			return id_ong;
		}
		public void setId_ong(Long id_ong) {
			this.id_ong = id_ong;
		}
		public String getMarca() {
			return marca;
		}
		public void setMarca(String marca) {
			this.marca = marca;
		}
		public String getNombre_rep() {
			return nombre_rep;
		}
		public void setNombre_rep(String nombre_rep) {
			this.nombre_rep = nombre_rep;
		}
		public String getApellido_rep() {
			return apellido_rep;
		}
		public void setApellido_rep(String apellido_rep) {
			this.apellido_rep = apellido_rep;
		}
		public Boolean getAlta() {
			return alta;
		}
		public void setAlta(Boolean alta) {
			this.alta = alta;
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
		public List<Proyecto> getPublicaciones() {
			return publicaciones;
		}
		public void setPublicaciones(List<Proyecto> publicaciones) {
			this.publicaciones = publicaciones;
		}
		@Override
		public String toString() {
			return "ONG [id_ong=" + id_ong + ", marca=" + marca + ", nombre_rep=" + nombre_rep + ", apellido_rep="
					+ apellido_rep + ", alta=" + alta + ", usuario=" + usuario + ", foto=" + foto + ", publicaciones="
					+ publicaciones + "]";
		}
		
		
}
