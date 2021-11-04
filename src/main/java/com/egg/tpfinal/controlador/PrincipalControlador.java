package com.egg.tpfinal.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	@GetMapping("/principal")
	public String principal() {
		return "principal";
	}
}
