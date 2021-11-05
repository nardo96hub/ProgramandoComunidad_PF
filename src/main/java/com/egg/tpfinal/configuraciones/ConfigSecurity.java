package com.egg.tpfinal.configuraciones;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


	@Configuration
	@EnableWebSecurity
	@EnableGlobalMethodSecurity(prePostEnabled=true)
	public class ConfigSecurity extends WebSecurityConfigurerAdapter{
		
		/*@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests()
					.antMatchers("/css/*", "/js/*", "/img/*", "/**").permitAll()
					.and().csrf()
						.disable();
		} */
		
		@Override
		  protected void configure(HttpSecurity http) throws Exception {
		        http
		                .authorizeRequests()
		                .antMatchers("/css/*", "/js/*", "/img/*",
		                        "/**").permitAll()
		                .and().
		                formLogin()
		                .loginPage("/login")
		                .loginProcessingUrl("/logincheck")
		                .usernameParameter("email")
		                .passwordParameter("password")
		                .defaultSuccessUrl("/principal")
		                .permitAll()
		                .and().logout()
		                .logoutUrl("/logout")
		                .logoutSuccessUrl("/login?logout")             
		                .permitAll().
		                and().csrf().disable();
		    }
		}
	
