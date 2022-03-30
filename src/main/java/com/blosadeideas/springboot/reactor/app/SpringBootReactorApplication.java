package com.blosadeideas.springboot.reactor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.blosadeideas.springboot.reactor.app.models.Usuario;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Flux<Usuario> nombres = Flux.just("David","Gordo", "ya", "vez")
				.map(nombre -> new Usuario(nombre.toUpperCase(), null))
				.doOnNext(usuario -> {
					if(usuario == null) {
						throw new RuntimeException("Nombres no pueden ser vacíos");
					}
					System.out.println(usuario.getNombre());
					
					
				})
				.map(usuario -> {
					String nombre = usuario.getNombre().toLowerCase();
					usuario.setNombre(nombre);
					return usuario;
				})
				
				;
				
				
				//Expresión lambda.
				//.doOnNext(System.out::println);
				
				//Trabajo con varias instrucciones
				/*.doOnNext(elemento -> {
					System.out.println(elemento);
				});*/
		
		
		
		//nombres.subscribe();
//		nombres.subscribe(log::info);
				nombres.subscribe(e -> log.info(e.getNombre()),
						error -> log.error(error.getMessage()),
						new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								log.info("Ha finalizado la ejecución del observable con éxito!");
							}
							
							
						});
	}

}
