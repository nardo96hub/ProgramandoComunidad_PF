package com.egg.tpfinal.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.tpfinal.servicios.TecnologiasService;

@Controller
@RequestMapping("/tecnologia")
public class TecnologiaControlador {

	@Autowired
	private TecnologiasService ServiTec;
	@GetMapping("/crearTecnologia")
	public String tec() {
		return "crearTecnologias";
	}
	@PostMapping("/crearTecnologia")
	public String tecn(@RequestParam String tecno) {
		try {
			ServiTec.guardarTecnologias(tecno);
			return "redirect:/registrodev";
		} catch (Exception e) {
			
			e.printStackTrace();
			return "redirect:/tecnologia/crearTecnologia";
		}
	
	}
}