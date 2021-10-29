package com.egg.tpfinal.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class PrincipalControlador {

	@GetMapping("/")
	public String s() {
		System.out.println("Soy Nardo");
		System.out.println("Soy Fede");
		System.out.println("Soy Karen");
		System.out.println("Soy Adrian");
		return "test.html";
	}
}
