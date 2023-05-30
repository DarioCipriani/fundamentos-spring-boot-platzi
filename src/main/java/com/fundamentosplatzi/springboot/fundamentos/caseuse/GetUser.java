package com.fundamentosplatzi.springboot.fundamentos.caseuse;

import com.fundamentosplatzi.springboot.fundamentos.entity.User;

import java.util.List;

//Esta interface lo que va hacer es retribuir la lista de los usuarios
public interface GetUser {
    List<User> getAll();
}
