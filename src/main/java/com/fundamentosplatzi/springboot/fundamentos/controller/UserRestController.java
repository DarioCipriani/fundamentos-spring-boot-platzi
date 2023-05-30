package com.fundamentosplatzi.springboot.fundamentos.controller;

import com.fundamentosplatzi.springboot.fundamentos.caseuse.CreateUser;
import com.fundamentosplatzi.springboot.fundamentos.caseuse.DeleteUser;
import com.fundamentosplatzi.springboot.fundamentos.caseuse.GetUser;
import com.fundamentosplatzi.springboot.fundamentos.caseuse.UpdateUser;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  //hereda de la anotacion cotroller y a su vez nos permite que todos los metodos q se creen aca se formatee con el formato Json
@RequestMapping("/api/users")  //es la ruta por donde va a ser consumido el servicio
public class UserRestController {
    //capa relacionada con los servicios q vamos a desplegar. Los servicios son create, get, delete, update
    private GetUser getUser;
    private CreateUser createUser;
    private DeleteUser deleteUser;
    private UpdateUser updateUser;
    public UserRestController(GetUser getUser, CreateUser createUser, DeleteUser deleteUser, UpdateUser updateUser){
        this.getUser = getUser;
        this.createUser = createUser;
        this.deleteUser = deleteUser;
        this.updateUser = updateUser;
    }
    @GetMapping("/")
    List<User> get(){
        return getUser.getAll();
    }

    //Para crear los usuarios
    @PostMapping("/")
    ResponseEntity<User> newUser(@RequestBody User newUser){
        return new ResponseEntity<>(createUser.save(newUser), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteuser(@PathVariable Long id){
        deleteUser.remove(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //Metodo Update
    @PutMapping("{id}")
    ResponseEntity<User> replaceUser(@RequestBody User newUser, @PathVariable Long id){
        return new ResponseEntity<>(updateUser.update(newUser, id),HttpStatus.OK);
    }
}
