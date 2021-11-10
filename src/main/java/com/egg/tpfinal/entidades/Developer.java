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
public class Developer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_developer;
	private String nombre;
	private String apellido;
	private Boolean alta;
	private String telefono;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "usuario_id_usuario", referencedColumnName = "id_usuario")
	private Usuario usuario;

	@OneToOne (cascade = CascadeType.ALL)
	private Foto foto;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Tecnologias> tecnologias;

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

	@Override
	public int hashCode() {
		return Objects.hash(alta, apellido, foto, id_developer, nombre, tecnologias, telefono, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Developer other = (Developer) obj;
		return Objects.equals(alta, other.alta) && Objects.equals(apellido, other.apellido)
				&& Objects.equals(foto, other.foto) && Objects.equals(id_developer, other.id_developer)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(tecnologias, other.tecnologias)
				&& Objects.equals(telefono, other.telefono) && Objects.equals(usuario, other.usuario);
	}

}
