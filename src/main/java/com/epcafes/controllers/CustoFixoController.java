package com.epcafes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epcafes.entities.CustoFixo;
import com.epcafes.services.CustoFixoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/custoFixo")
public class CustoFixoController {
	
	@Autowired
	private CustoFixoService custoFixoService;
	
	@PostMapping
	public CustoFixo salvar(@RequestBody @Valid CustoFixo custoFixo) {
		
		return custoFixoService.salvar(custoFixo);
	}
	
	@GetMapping
	public List<CustoFixo> buscarTodos(){
		
		return custoFixoService.buscarTodos();
	}
}
