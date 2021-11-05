package com.egg.tpfinal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.egg.tpfinal.servicios.UsuarioService;

@SpringBootApplication
public class ProgramandoComunidadesApplication {
@Autowired
private UsuarioService usuarioService;
	public static void main(String[] args) {
		SpringApplication.run(ProgramandoComunidadesApplication.class, args);
		
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioService).passwordEncoder(new BCryptPasswordEncoder());
	}

}
