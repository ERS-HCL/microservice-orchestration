package com.clinic.management.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import ca.uhn.fhir.rest.server.interceptor.LoggingInterceptor;


@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.clinic.management.repo")

public class PatientApplication {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(PatientApplication.class, args);

  }

  @Bean
  public LoggingInterceptor loggingInterceptor() {
      return new LoggingInterceptor();
  }
  


 /* @Bean
  public ZeebeClient zeebe() {
    //ZeebeClient zeebeClient = ZeebeClient.newClient();
	    ZeebeClient zeebeClient = ZeebeClient.newClientBuilder().brokerContactPoint("localhost:26500").build(); 

    return zeebeClient;
  }*/

}
