package com.fundamentosplatzi.springboot.fundamentos.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/*
* representa los post de un usuario
 */
@Entity
@Table(name = "post")
public class Post {
    @Id  //es el Id que representa la entidad a nivel de la tabla en la BD
    @GeneratedValue(strategy = GenerationType.IDENTITY) //para que se cree un id unico
    @Column(name = "id_post", nullable = false, unique = true)
    private Long id; //representamos la columna en la BD como una propiedad a nivel de clase. Tipo de dato Long y el nombre de la columna es id
    @Column(name = "description", length = 255)
    private String description;
    @ManyToOne  //porque muchos post tienen un usuario y un usuario puede tener muchos post
    @JsonBackReference
    private User user;

    public Post() {
    }

    public Post(String description, User user) {
        this.description = description;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }
}
