package com.fundamentosplatzi.springboot.fundamentos.bean;

import org.springframework.context.annotation.Bean;


public class MyBeanImplement implements MyBean{
    @Override
    public void print() {
        System.out.println("Hola desde mi implementacion propia del Bean");
    }
}
