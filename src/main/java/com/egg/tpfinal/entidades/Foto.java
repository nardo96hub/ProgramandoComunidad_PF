package com.egg.tpfinal.entidades;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Foto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_foto;
	private String url_foto;

	public Foto() {
		super();
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(id_foto, url_foto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Foto other = (Foto) obj;
		return Objects.equals(id_foto, other.id_foto) && Objects.equals(url_foto, other.url_foto);
	}

}
