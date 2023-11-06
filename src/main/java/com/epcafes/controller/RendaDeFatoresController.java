package com.epcafes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/rendaFatores")
public class RendaDeFatoresController {
	
	@GetMapping("/capitalFixo")
    public String capitalFixo() {
    	    	
        return "restricted/custo/CapitalFixo";
    }
}
