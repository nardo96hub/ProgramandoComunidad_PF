package com.egg.tpfinal.entidades;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
public class Proyecto {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_proyecto;
	private String	titulo;
	
	@Column(length = 4000)
	private String	cuerpo;
	
	@Temporal (TemporalType.DATE)
	private Date fecha_post;
	
	private Boolean	admitir_deve; //se setea en false cuando el proyecto ya tiene 5 developers
	private Boolean	alta;
	@OneToMany
	private List<Developer> developer;	
	@OneToOne
	private ONG	ong;
	
	
	public Long getId_proyecto() {
		return id_proyecto;
	}
	public void setId_proyecto(Long id_proyecto) {
		this.id_proyecto = id_proyecto;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	public Date getFecha_post() {
		return fecha_post;
	}
	public void setFecha_post(Date fecha_post) {
		this.fecha_post = fecha_post;
	}
	public Boolean getAdmitir_deve() {
		return admitir_deve;
	}
	public void setAdmitir_deve(Boolean admitir_deve) {
		this.admitir_deve = admitir_deve;
	}
	public Boolean getAlta() {
		return alta;
	}
	public void setAlta(Boolean alta) {
		this.alta = alta;
	}
	public List<Developer> getDeveloper() {
		return developer;
	}
	public void setDeveloper(List<Developer> developer1) {
		developer = developer1;
	}
	public ONG getOng() {
		return ong;
	}
	public void setOng(ONG ong) {
		this.ong = ong;
	}
	@Override
	public String toString() {
		return "Proyecto [id_proyecto=" + id_proyecto + ", titulo=" + titulo + ", cuerpo=" + cuerpo + ", fecha_post="
				+ fecha_post + ", admitir_deve=" + admitir_deve + ", alta=" + alta + ", Developer=" + developer
				+ ", ong=" + ong + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(admitir_deve, alta, cuerpo, developer, fecha_post, id_proyecto, ong, titulo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Proyecto other = (Proyecto) obj;
		return Objects.equals(admitir_deve, other.admitir_deve) && Objects.equals(alta, other.alta)
				&& Objects.equals(cuerpo, other.cuerpo) && Objects.equals(developer, other.developer)
				&& Objects.equals(fecha_post, other.fecha_post) && Objects.equals(id_proyecto, other.id_proyecto)
				&& Objects.equals(ong, other.ong) && Objects.equals(titulo, other.titulo);
	}
	
	
}
