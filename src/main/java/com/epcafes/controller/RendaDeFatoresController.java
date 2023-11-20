package com.epcafes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RendaDeFatoresController {

	@GetMapping("/rendaFatores")
	public String fazerLogin(){
		return "restricted/custo/RendaFatores";
    }
}
