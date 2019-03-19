package com.cbelhaffef.dajt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableAsync
@EnableTransactionManagement
@EnableJpaRepositories(basePackages ={"com.cbelhaffef.dajt.api"},repositoryImplementationPostfix = "CustomImpl")
@EntityScan(basePackages ={ "com.cbelhaffef.dajt.api"})
@SpringBootApplication(scanBasePackages = "com.cbelhaffef.dajt")
public class MainApp {

	public static void main(String[] args) throws Exception {
		new SpringApplication(MainApp.class).run(args);
	}

}

