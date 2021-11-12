package com.egg.tpfinal.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	public String tecn(@RequestParam String tecno, ModelMap modelo) {			//recibe de front tecnol
		try {
			ServiTec.guardarTecnologias(tecno);								//guarda tecnl en DDBB
			return "redirect:/registrodev";
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			e.printStackTrace();											//sino puede, lanza error (pila error)
			return "crearTecnologias.html";
		}
	}
}
