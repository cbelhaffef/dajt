package com.cbelhaffef.dajt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.*;

@SpringBootApplication(scanBasePackages = "com.cbelhaffef.dajt")
@EnableJpaRepositories(basePackages ={"com.cbelhaffef.dajt.dao.repositories"},repositoryImplementationPostfix = "CustomImpl")
@EntityScan(basePackages ={ "com.cbelhaffef.dajt.dao.entities"})
@EnableTransactionManagement
public class MainApp {
	public static void main(String[] args) throws Exception {
		new SpringApplication(MainApp.class).run(args);
	}
}

