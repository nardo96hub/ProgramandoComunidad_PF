package com.egg.tpfinal.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

	 private String upload_folder = ".//src//main//resources//files//";
	 private String url_fotoDB = "/src/main/resources/files/";
	  public Foto guardarfoto(MultipartFile file) throws IOException {
		  Foto foto=null;
		  
	        if(!file.isEmpty()){
	            byte[] bytes = file.getBytes();
	            
	           //Optional<Long> ultimoID = fotoRepo.findTopByOrderByIdDesc().get();
	           String url_imagen;
	           String url_base;
	           try {
	        	   Long ultimoID = fotoRepo.findTopByOrderById_fotoDesc();
	        	   
	        	   url_imagen = upload_folder+ (ultimoID+1);
	        	   url_base = url_fotoDB +(ultimoID+1);
	           }catch(NullPointerException e) {
	        	   url_imagen = upload_folder+1;
	        	   url_base = url_fotoDB +1;
	           }
	           
	          
	           String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
	        
	           //"1.jpeg
	           // file.getOriginalFilename().substring ( file.getOriginalFilename().indexOf("."), file.getOriginalFilename().length());
	           	url_imagen = url_imagen+extension;
	            Path path = Paths.get(url_imagen);
	            
	            Files.write(path,bytes);
	            foto = new Foto(url_base+extension);
	                    
	            
	        }
	        return foto; 
	  }
}
