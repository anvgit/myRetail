package com.anvesh.target.myretailrestfulapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan("com.anvesh.target.*")
@EnableMongoRepositories("com.anvesh.target.dao")
public class MyRetailRestfulApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyRetailRestfulApiApplication.class, args);
	}

}
