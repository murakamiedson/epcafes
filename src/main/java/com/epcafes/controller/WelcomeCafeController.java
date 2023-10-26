package com.epcafes.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Controller
public class WelcomeCafeController {

	@GetMapping("/epcafes")
	public String home() {
		return "index";
	}
}
