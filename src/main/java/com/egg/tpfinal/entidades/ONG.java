package com.egg.tpfinal.entidades;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
		@OneToOne(cascade = CascadeType.ALL)
		@JoinColumn(name = "usuario_id_usuario", referencedColumnName = "id_usuario")
		private Usuario	usuario;
		@OneToOne
		private Foto foto;
		@OneToMany
		private List<Proyecto> publicaciones;
		//Ver si hace falta los JoinColumn
		
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
		@Override
		public int hashCode() {
			return Objects.hash(alta, apellido_rep, foto, id_ong, marca, nombre_rep, publicaciones, usuario);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ONG other = (ONG) obj;
			return Objects.equals(alta, other.alta) && Objects.equals(apellido_rep, other.apellido_rep)
					&& Objects.equals(foto, other.foto) && Objects.equals(id_ong, other.id_ong)
					&& Objects.equals(marca, other.marca) && Objects.equals(nombre_rep, other.nombre_rep)
					&& Objects.equals(publicaciones, other.publicaciones) && Objects.equals(usuario, other.usuario);
		}
		
		
}
