package com.game.rockpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class RockpcApplication {

	public static void main(String[] args) {
		SpringApplication.run(RockpcApplication.class, args);
	}

}
