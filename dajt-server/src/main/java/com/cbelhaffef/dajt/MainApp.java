package com.cbelhaffef.dajt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.*;

@SpringBootApplication
@EnableJpaRepositories(basePackages ={ "com.cbelhaffef.dajt.repo"},repositoryImplementationPostfix = "CustomImpl")
@EntityScan(basePackages ={ "com.cbelhaffef.dajt.model"})
@EnableTransactionManagement
@ComponentScan({"com.cbelhaffef.dajt"})
public class MainApp {
	public static void main(String[] args) throws Exception {
		new SpringApplication(MainApp.class).run(args);
	}
}

