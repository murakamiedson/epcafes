package com.epcafes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
@Getter
@Setter
@Controller
public class WelcomeCafeController {

	@GetMapping("/epcafes")
	public String home() {

		log.info("welcome controller...");
		return "index";
	}
}
