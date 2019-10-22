package com.clinic.management.app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.zeebe.client.ZeebeClient;

@RestController
public class PatientControlller {

	@Autowired
	private ZeebeClient zeebe;

	@PostMapping("/check-in")
	public String checkin(@RequestBody String uuid) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));

		zeebe.newPublishMessageCommand().messageName("Message_Patient_Info").correlationKey(uuid)
				.timeToLive(Duration.ofMinutes(30)).variables("{\"check-in-time\":\""+dateFormat.format(date)+"\"}").send().join();

		return "Completed";

	}
	
	@PostMapping("/bp-data")
	public String bpdata(@RequestBody String uuid) {

		zeebe.newPublishMessageCommand().messageName("Message_BP_Info").correlationKey(uuid)
				.timeToLive(Duration.ofMinutes(30)).variables("{\"bpValue\":100}").send().join();

		return "Completed";

	}


}
