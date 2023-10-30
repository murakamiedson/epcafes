package com.epcafes.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String handleException(HttpServletRequest req, Model model, RuntimeException ex) {
        model.addAttribute("mensagem", ex.getMessage());

        return "error";
    }
}
