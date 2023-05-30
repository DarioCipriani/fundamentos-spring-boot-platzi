package com.fundamentosplatzi.springboot.fundamentos.repository;

import com.fundamentosplatzi.springboot.fundamentos.dto.UserDTO;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository // este esteriotipo lo utilizamos para pode inyectar la interface UserRepository como dependencia
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Query("Select u from User u where u.email=?1") //"Select u from User u where u.email=?1" va la consulta JPQL, en JPQL trabajamos a partir de objetos
    Optional<User> findByUserEmail(String email);
    @Query("Select u from User u where  u.name like ?1%") //se agrega el operador % porque hacemos un like, es decir que vamos a buscar y ordenar a partir del name
    List<User> findAndSort(String name, Sort sort);

    List <User> findByName(String name);

    Optional<User> findByEmailAndName(String email,String name);

    List<User> findByNameLike(String name); //Query method que encuentra informacion a travez de un nombre

    List<User> findByNameOrEmail(String name, String email);  //Query method que encuentra informacion a travez de un nombre o (or) email

    List<User> findByBirthDateBetween(LocalDate begin, LocalDate end); //Query method que encuentra informacion a travez de un intervalo de fecha

    //Query method para ordenar de manera descendente toda la informacion que estamos trayendo,
    // en este caso bamos a buscar a partir del nombre y vamos a ordenar de manera descendente a partir del Id
    List<User> findByNameLikeOrderByIdDesc(String name);

    //@Query crea las sentencias de tipo JPQL y de esta manera queda construido el Query a nivel de JPQL con named Parameters
    @Query("select new com.fundamentosplatzi.springboot.fundamentos.dto.UserDTO(u.id, u.name, u.birthDate)" +
        " from User u " +
        " where u.birthDate=:parametroFecha" +
        " and u.email=:paratroEmail")
    Optional<UserDTO> getAllByBirthDateAndEmail(@Param("parametroFecha") LocalDate date,
                                                @Param("paratroEmail") String email);

    List<User> findAll();
}
