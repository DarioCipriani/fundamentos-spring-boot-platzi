package com.fundamentosplatzi.springboot.fundamentos.component;

import org.springframework.stereotype.Component;

//Primera dependencia creada a partir de la interface ComponentDependency
@Component
public class ComponentImplement implements ComponentDependency {
    @Override
    public void saludar() {
        System.out.println("Hola mundo desde mi componente");
    }
}
