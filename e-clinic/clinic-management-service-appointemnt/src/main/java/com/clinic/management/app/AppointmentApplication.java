package com.clinic.management.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import io.zeebe.client.ZeebeClient;


@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.clinic.management.repo")

public class AppointmentApplication {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(AppointmentApplication.class, args);

  }


  @Bean
  public ZeebeClient zeebe() {
    ZeebeClient zeebeClient = ZeebeClient.newClientBuilder().brokerContactPoint("localhost:26500").build(); 

    return zeebeClient;
  }

}
