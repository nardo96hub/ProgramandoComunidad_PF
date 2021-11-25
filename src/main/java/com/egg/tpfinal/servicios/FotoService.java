package com.egg.tpfinal.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.egg.tpfinal.repositorios.FotoRepository;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.annotation.Resource;

import com.egg.tpfinal.entidades.Foto;
import com.egg.tpfinal.entidades.ImgurRes;

@Service
public class FotoService {

	@Autowired
	FotoRepository fotoRepo;

	private String upload_folder = ".//src//main//resources//files//";
	private String url_fotoDB = "/src/main/resources/files/";

	public Foto guardarfoto(MultipartFile file) {
		Foto foto = null;
		
		if (!file.isEmpty()) {
			
			
			// Optional<Long> ultimoID = fotoRepo.findTopByOrderByIdDesc().get();
			//String url_imagen;
			String url_base;
			try {
				byte[] bytes = file.getBytes();
				//System.out.println("comenzando");
				url_base =subirApi(bytes);
				//System.out.println("vamos bien");
				return new Foto(url_base);
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("error, se intentara cargar la foto local");
				try {
					foto=	guardarfoto2(file);
					return foto;
				} catch (Exception ex) {
				  System.out.println("error no se ha podido subir foto");
				  return new Foto("https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png");
				}
			}
			
			
			/*
			 * try {
			 
				Long ultimoID = fotoRepo.findTopByOrderById_fotoDesc();
				url_imagen = upload_folder + (ultimoID + 1);
				url_base = url_fotoDB + (ultimoID + 1);
			} catch (NullPointerException e) {
				url_imagen = upload_folder + 1;
				url_base = url_fotoDB + 1;
			}
			String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			// "1.jpeg
			// file.getOriginalFilename().substring (
			// file.getOriginalFilename().indexOf("."),
			// file.getOriginalFilename().length());
			url_imagen = url_imagen + extension;
			Path path = Paths.get(url_imagen);
			Files.write(path, bytes);
			foto = new Foto(url_base + extension);*/
		}else {
			foto=new Foto("https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png");
		}
		return foto;
	}
	
	 public String subirApi(byte[] fileBytes) throws Exception {
	    String clientId="151e2322b0d386c"; //este es mi cliente id, es un dato muy privado y no hay que mostrarselo a nadie
	    //se genera en la misma pagina de imgur.
		final String uri = "https://api.imgur.com/3/image";

		MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>(); //es una especie de LinkedHashMap
		//la api de imgurl recibe esto con la foto, recordar que linked hashmap esta ordenado por orden de insercion
		//por lo que el orden de iteración es el mismo que el orden de inserción (o el orden de acceso, según los parámetros de construcción).
		bodyMap.add("image", getUserFileResource(fileBytes)); //agrego el binario de la foto
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA); //comenzamos el armado de mensaje: Establece el tipo de mensaje
		headers.add("Authorization", "Client-ID "+clientId);  //clave valor o atributo y valor, el atributo se llama asi porque la api de imgur lo espera asi
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

		RestTemplate restTemplate = new RestTemplate();
		//abajo uso la clase casera imgurres para mapear el envio y la respuesta de la imagen
		ResponseEntity<ImgurRes> respuesta = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, ImgurRes.class);
		System.out.println("estado " + respuesta.getStatusCode()); // deberia devolver 200 
		System.out.println("enlace " + respuesta.getBody().getData().getLink()); // debe devolver el enlace de la imagen cargada 
		
		return respuesta.getBody().getData().getLink();
	  }
	 
	 /*
	  * InputStreamResource (Acceso al flujo de entrada): Se utiliza para acceder a los recursos del flujo de entrada binario. 
	  * Debido a que InputStreamResource es un recurso que siempre está abierto, isOpen () siempre devuelve verdadero. 
	  * Si necesita leer una secuencia varias veces, no use InputStreamResource (aunque es ampliamente aplicable, no es eficiente. 
	  * Intente usar ByteArrayResource o FileSystemResource en su lugar)
	  */
	 
	 public static ByteArrayResource getUserFileResource(byte[] bytes) throws IOException {
		return  new ByteArrayResource(bytes);
	  }
	
	 public Foto guardarfoto2(MultipartFile file) throws IOException {		//guarda una foto
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