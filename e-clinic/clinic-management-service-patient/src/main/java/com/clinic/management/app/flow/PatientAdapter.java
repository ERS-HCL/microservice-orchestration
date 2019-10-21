package com.clinic.management.app.flow;

import org.springframework.stereotype.Component;


@Component
public class PatientAdapter {/* // implements JobHandler {
  
  @Autowired
  private ZeebeClient zeebe;

  private JobWorker subscription;
  
  @PostConstruct
  public void subscribe() {
    subscription = zeebe.newWorker()
      .jobType("ship-goods-z")
      .handler(this)
      .timeout(Duration.ofMinutes(1))
      .open();      
  }

  @Override
  public void handle(JobClient client, ActivatedJob job) {
    System.out.println("ship goods");
    client.newCompleteCommand(job.getKey()).send().join();
  }

  @PreDestroy
  public void closeSubscription() {
    subscription.close();      
  }
*/}
