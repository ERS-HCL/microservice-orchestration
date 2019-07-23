package com.clinic.management.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.zeebe.client.ZeebeClient;

@SpringBootApplication
public class PatientApplication {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(PatientApplication.class, args);
  }

  @Bean
  public ZeebeClient zeebe() {
    //ZeebeClient zeebeClient = ZeebeClient.newClient();
	    ZeebeClient zeebeClient = ZeebeClient.newClientBuilder().brokerContactPoint("localhost:26500").build(); 

    return zeebeClient;
  }

}
