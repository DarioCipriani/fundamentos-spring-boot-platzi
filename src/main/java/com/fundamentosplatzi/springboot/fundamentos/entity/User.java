package com.fundamentosplatzi.springboot.fundamentos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id  //es el Id que representa la entidad a nivel de la tabla en la BD
    @GeneratedValue(strategy = GenerationType.AUTO) //para que se autoincremente cuando agregamos un usuario
    @Column(name = "id_user", nullable = false, unique = true)
    private Long id; //representamos la columna en la BD como una propiedad a nivel de clase. Tipo de dato Long y el nombre de la columna es id

    @Column(length = 50)
    private String name;

    @Column(length = 50, unique = true)
    private String email;
    private LocalDate birthDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)  // un usuario tiene muchos post y un post tiene un usuario
    @JsonIgnore
   // @JsonManagedReference //esto es para que no nos de un error relacionado con Stack over flow
    private List<Post> posts = new ArrayList<>();

    public User() {
    }

    public User(String name, String email, LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
    }

    public User(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", posts=" + posts +
                '}';
    }
}
