package com.fundamentosplatzi.springboot.fundamentos;

import com.fundamentosplatzi.springboot.fundamentos.bean.MyBean;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosplatzi.springboot.fundamentos.component.ComponentDependency;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.pojo.UserPojo;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import com.fundamentosplatzi.springboot.fundamentos.service.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {
	/*
	 * Con esta herramieta de imyeccion de dependencia entonces podemos tenes N implementaciones a partir de una dependencia
	 * y simplemente al utilizar la anotacion @Qualifier llamamos a la dependencia que queremos inyectar
	*/
	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);
	private ComponentDependency componentDependency; //El objeto que se Inyecta depende de la dependencia q le pasemos al @Qualifier
	private MyBean myBean; //El objeto que se Inyecta es la dependencia de la Interface MyBean
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;
	private UserService userService;

	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean,
								  MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties myBeanWithProperties,
								  UserPojo userPojo, UserRepository userRepository, UserService userService){ // El constructor recibe como parametro mi dependencia para poderla inyectar
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;
	}
	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	/**
	 * Metodo de la interface CommandLineRunner, este metodo, run nos ejecuta en la aplicacion lo que nosotros querramos,
	 * en este caso seria mostrar la implememntacion de nuestra dependencia (componentDependency)..
    */
	@Override
	public void run(String... args) throws Exception {
		//ejemplosAnterioresAClase19();  // esto se comenta a partir de la clase 19 para no mostrar los ejemplos antes explicados
		saveUsersDataBase();
		getInformationJpqlFromUser();
		saveWithErrorTransactional();
	}

	private void saveWithErrorTransactional(){
		User test1 = new User("Test1Transactional1", "TestTransactional1@domain.com", LocalDate.now());
		User test2 = new User("Test2Transactional1", "TestTransactional1@domain.com", LocalDate.now());
		User test3 = new User("Test3Transactional1", "TestTransactional1@domain.com", LocalDate.now());
		User test4 = new User("Test4Transactional1", "TestTransactional1@domain.com", LocalDate.now());

		List<User> users = Arrays.asList(test1, test2, test3, test4);
		try {
			userService.saveTransactional(users);
		}catch (Exception e) {
			LOGGER.info("Esta es una excepcion dentro del metodo transactional " + e);
		}
		userService.getAllUsers().stream()
				.forEach(user -> LOGGER.info("Este es el usuario dentro del metodo tradicional " + user));
	}

	private void getInformationJpqlFromUser(){
		LOGGER.info("Usuario con el metodo findByUserEmail"  +
				userRepository.findByUserEmail("julie@domain.com")
						.orElseThrow(() -> new RuntimeException("No se encontro el usuario")));

		userRepository.findAndSort("John", Sort.by("id").descending())
				.stream()
				.forEach(user -> LOGGER.info("Usuario con metodo sort " + user));

		userRepository.findByName("John") // se llama al query method que se creo (findByName) y en caso de que existan muchos John los vamos a mostrar por pantalla
				.stream()
				.forEach(user -> LOGGER.info("Usuario con Query method " + user));

		LOGGER.info("Usuario con Query method findByEmailAndName" + userRepository.findByEmailAndName
						("daniela@domain.com","Daniela")
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado")));

		//Al utilizar like siempre se coloca el argumento dentro de los %%
		userRepository.findByNameLike("%user%")    // busca todos los usuarios que comiencen con user y si colocaramos %u% buscaria todos los usuarios que comienzan con u
				.stream()
				.forEach(user -> LOGGER.info("Usuario findByNameLike " + user));

		userRepository.findByNameOrEmail(null, "user10@domain.com")    // busca todos los usuarios por el nombre o (or) poe el email, en este caso pusimos el nombre en null y en el email colocamos el mail que queriamos buscar, podriamos hacerlo alrevez
				.stream()
				.forEach(user -> LOGGER.info("Usuario findByNameOrEmail " + user));

		userRepository.findByBirthDateBetween(LocalDate.of(2021,3,1), LocalDate.of(2021,4,2))
				.stream()
				.forEach(user -> LOGGER.info("usuario con intervalo de fechas " + user));

		//Al utilizar like siempre se coloca el argumento dentro de los %%
		//busca todos los usuarios que comiencen con user y los ordena de manera descendente por le Id
		// Si el metodo definido en UserRepository fuese findByNameConaineOrderByIdDesc, aqui seria: userRepository.findByNameLikeContainByIdDesc("user") y devolveria los nombres que contienen la palabra user
		userRepository.findByNameLikeOrderByIdDesc("%user%")
				.stream()
				.forEach(user -> LOGGER.info("Usuario encontrado con like y ordenado descendente " + user));

		LOGGER.info("El usuario a partir del named parameter es: " + userRepository.getAllByBirthDateAndEmail(LocalDate.of(2021,07,21), "daniela@domain.com")
				.orElseThrow(() ->
						new RuntimeException("No se encontro el usuario a partir del named parameter")));
	}

	private void saveUsersDataBase(){
		User user1 = new User("John", "john@domain.com", LocalDate.of(2021,03,20));
		User user2 = new User("Julie", "julie@domain.com", LocalDate.of(2021,05,21));
		User user3 = new User("Daniela", "daniela@domain.com", LocalDate.of(2021,07,21));
		User user4 = new User("user4", "user4@domain.com", LocalDate.of(2021,03,20));
		User user5 = new User("user5", "user5@domain.com", LocalDate.of(2021,02,6));
		User user6 = new User("user6", "user6@domain.com", LocalDate.of(2021,03,15));
		User user7 = new User("user7", "user7@domain.com", LocalDate.of(2021,05,30));
		User user8 = new User("user8", "user8@domain.com", LocalDate.of(2021,07,9));
		User user9 = new User("user9", "user9@domain.com", LocalDate.of(2021,11,7));
		User user10 = new User("user10", "user10@domain.com", LocalDate.of(2021,01,28));
		User user11 = new User("user11", "user11@domain.com", LocalDate.of(2021,12,28));
		User user12 = new User("user12", "user12@domain.com", LocalDate.of(2021,6,5));
		List<User> list = Arrays.asList(user1,user2,user3,user4,user5,user6,user7,user8,user9,user10,user11,user12);
		list.stream().forEach(userRepository::save);  // de esta forma registramos la informacion en la BD
		userRepository.findAll().stream().forEach(s ->System.out.println(s));
	}
	private void ejemplosAnterioresAClase19(){
		 componentDependency.saludar();
		 myBeanWithDependency.printWithDependency();
		 myBean.print();
		 myBeanWithDependency.printWithDependency();
		 System.out.println(myBeanWithProperties.function());
		 System.out.println(userPojo.getEmail() + " " + "-" + " " + userPojo.getPassword() + " " + "-" + " " + "Edad" + userPojo.getAge());

		 try{
			 int value=10/0;
			 LOGGER.debug("mi valor: " + value);
		 } catch (Exception e) {
			 LOGGER.error("esto es un error al dividir por cero " + e.getMessage());
		 }
	 }

}
