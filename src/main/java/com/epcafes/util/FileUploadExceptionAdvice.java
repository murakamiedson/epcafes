package com.epcafes.util;

import org.springframework.web.multipart.MaxUploadSizeExceededException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(Model model, MaxUploadSizeExceededException e) {
        model.addAttribute("message", "Arquivo é muito grande!");

        return "/";
    }
}