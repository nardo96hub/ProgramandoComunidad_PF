package com.egg.tpfinal.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.egg.tpfinal.repositorios.FotoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import com.egg.tpfinal.entidades.Foto;

@Service
public class FotoService {

	@Autowired
	FotoRepository fotoRepo;

	private String upload_folder = ".//src//main//resources//files//";     	//PQ GUARDE EN SERVIDOR (carpeta)
	private String url_fotoDB = "/src/main/resources/files/";				//PQ GUARDE EN SERVIDOR (BBDD)

	public Foto guardarfoto(MultipartFile file) throws IOException {		//guarda una foto
		Foto foto = null;
		if (!file.isEmpty()) {
			byte[] bytes = file.getBytes();									//convierte un archivo a byte
			String url_imagen;
			String url_base;
			try {
				Long ultimoID = fotoRepo.findTopByOrderById_fotoDesc();		//obtengo ID max de foto 
				url_imagen = upload_folder + (ultimoID + 1);				//completo URL
				url_base = url_fotoDB + (ultimoID + 1);						//completo URL
			} catch (NullPointerException e) {
				url_imagen = upload_folder + 1;								//si nombrearchivo=null genero URL=1
				url_base = url_fotoDB + 1;
			}
			String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")); //obtengo extensión de foto
			url_imagen = url_imagen + extension;							//completo nombre foto
			Path path = Paths.get(url_imagen);								//obtengo ruta de acceso completa+URL
			Files.write(path, bytes);										//almaceno foto en BBDD/Servidor
			foto = new Foto(url_base + extension);							//creo objeto foto
		}
		return foto;
	}
}
