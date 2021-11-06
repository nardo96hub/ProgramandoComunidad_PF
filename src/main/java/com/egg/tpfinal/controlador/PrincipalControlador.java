package com.egg.tpfinal.controlador;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("")
public class PrincipalControlador {

	@GetMapping("/")
	public String s() {
		return "index.html";
	}
	
	@GetMapping("/quienesomos")
	public String quiensoy() {
		return "quienesomos";
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/principal")
	public String principal() {
		return "principal";
	}
	
	 @GetMapping("/login")
	    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
	        if (error != null) {
	            model.put("error", "Usuario o clave incorrectos");
	        }
	        if (logout != null) {
	            model.put("logout", "Ha salido correctamente.");
	        }
	        return "login.html";
	    }
	
					

		
}

