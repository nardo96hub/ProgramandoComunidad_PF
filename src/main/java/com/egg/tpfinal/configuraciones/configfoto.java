package com.egg.tpfinal.configuraciones;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class configfoto extends WebMvcConfigurerAdapter {

	 @Override //\src\main\resources\files /src/main/resources/files /images/** "file:images/"
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/src/main/resources/files/**").addResourceLocations("file:src/main/resources/files/");
	    }
	 	//URL donde se guarda la foto q sube Usuario (develop/ONG) y q no te genere conflicto srping
	 	//agrego directorio estático, el codico es muy claro, lo q no sepas googlea
}
