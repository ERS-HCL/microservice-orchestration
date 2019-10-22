package com.clinic.management.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ca.uhn.fhir.rest.server.interceptor.CorsInterceptor;
import ca.uhn.fhir.rest.server.interceptor.LoggingInterceptor;
import io.zeebe.client.ZeebeClient;


@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.clinic.management.repo")

public class PatientApplication {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(PatientApplication.class, args);

  }


  @Bean
  public ZeebeClient zeebe() {
    // Cannot yet use Spring Zeebe in current alpha
    ZeebeClient zeebeClient = ZeebeClient.newClientBuilder().brokerContactPoint("localhost:26500").build(); 
  //  zeebeClient.getConfiguration();
    
    
    // Trigger deployment
    zeebeClient.newDeployCommand() //
      .addResourceFromClasspath("bp-process-clinic-1.bpmn") //
      .send().join();
    
    return zeebeClient;
  }
  /*@Bean
  public ZeebeClient zeebe() {
    //ZeebeClient zeebeClient = ZeebeClient.newClient();
	    ZeebeClient zeebeClient = ZeebeClient.newClientBuilder().brokerContactPoint("localhost:26500").build(); 

    return zeebeClient;
  }*/

}
