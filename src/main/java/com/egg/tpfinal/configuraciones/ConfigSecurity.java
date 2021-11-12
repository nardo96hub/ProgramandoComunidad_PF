package com.egg.tpfinal.configuraciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.egg.tpfinal.servicios.UsuarioService;


	@Configuration
	@EnableWebSecurity
	@EnableGlobalMethodSecurity(prePostEnabled=true)
	public class ConfigSecurity extends WebSecurityConfigurerAdapter{
		
		@Autowired
		private UsuarioService usuarioService;
			
		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(usuarioService).passwordEncoder(new BCryptPasswordEncoder());  //objeto q encripta contrasñ
		}
		
		@Override
		  protected void configure(HttpSecurity http) throws Exception {
		        http
		                .authorizeRequests()
		                .antMatchers("/css/*", "/js/*", "/img/*","/files/*",
		                        "/**").permitAll()					//carpetas q no te genere conflict spring
		                .and().
		                formLogin()
		                .loginPage("/login")					//pag de loguin
		                .loginProcessingUrl("/logincheck")		//URL generada al hacer loguin
		                .usernameParameter("email")				//param de usuario
		                .passwordParameter("password")			//param de contraseñ
		                .defaultSuccessUrl("/principal")		//URL q se accede al loguin exitoso
		                .failureUrl("/login?error=error")		// URL q se accede al loguin defectuoso
		                .permitAll()							
		                .and().logout()							
		                .logoutUrl("/logout")					//deslogueo
		                .logoutSuccessUrl("/login?logout")      //deslogueo exitoso
		                .permitAll().
		                and().csrf().disable();
		    }
		}
	
	
