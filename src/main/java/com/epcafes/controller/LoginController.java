package com.epcafes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epcafes.model.AutenticacaoDTO;
import com.epcafes.model.Usuario;
import com.epcafes.service.TokenService;


@Controller
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

   @GetMapping
   @RequestMapping("/login")
   public String fazerLogin(Usuario usuario){
        return "login";
    }

     @PostMapping("/login")
    public ResponseEntity login(AutenticacaoDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        ResponseCookie cookieToken = ResponseCookie.from("tokenCookie",token)
        .httpOnly(true)
        .secure(true)
        .maxAge(28800)
        .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookieToken.toString()).build(); 
    }
}
