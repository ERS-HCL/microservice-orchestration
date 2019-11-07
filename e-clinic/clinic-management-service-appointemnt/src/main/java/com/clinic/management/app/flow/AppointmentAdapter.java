package com.clinic.management.app.flow;

import java.time.Duration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.hl7.fhir.r4.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clinic.management.app.provider.AppointmentResourceProvider;
import com.clinic.management.repo.AppointmentRepository;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.clients.JobClient;
import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.subscription.JobHandler;
import io.zeebe.client.api.subscription.JobWorker;


@Component
public class AppointmentAdapter implements JobHandler {
  
  @Autowired
  private ZeebeClient zeebe;
  @Autowired
	private AppointmentResourceProvider appointmentResourceProvider;

  private JobWorker subscription;
  
  @PostConstruct
  public void subscribe() {
    subscription = zeebe.newWorker()
      .jobType("schedule-details-zeebe")
      .handler(this)
      .timeout(Duration.ofMinutes(1))
      .open();      
  }

  @Override
  public void handle(JobClient client, ActivatedJob job) {
	  
	 java.util.LinkedHashMap patient = (java.util.LinkedHashMap) job.getVariablesAsMap().get("patient");  
	  
    String encounterId = (String) job.getVariablesAsMap().get("encounterId"); 
/*    String checkintime = (String) job.getVariablesAsMap().get("check-in-time"); 
    String bpValue = (String) job.getVariablesAsMap().get("bpValue")*/; 
    Appointment appointment =new Appointment();
    
	appointmentResourceProvider.autoCreateAppointment(appointment, encounterId, patient.get("name").toString());
    System.out.println("Appointment Details Created   --- " + patient +"::::" +encounterId);



    client.newCompleteCommand(job.getKey()).send().join();
  }

  @PreDestroy
  public void closeSubscription() {
    subscription.close();      
  }
}
