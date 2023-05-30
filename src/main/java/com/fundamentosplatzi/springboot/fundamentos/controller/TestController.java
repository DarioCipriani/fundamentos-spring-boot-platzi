package com.fundamentosplatzi.springboot.fundamentos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    //creamos una funcion para probar que el puerto y el path se despliegan correctamente
    @RequestMapping //para aceptar todas las solicitudes dentro de este metodo a nivel HTTP
    @ResponseBody //para responder un cuerpo a nivel del servicio
    public ResponseEntity<String> function(){
        return new ResponseEntity<>("Hello from controller", HttpStatus.OK);
    }
}
