package com.clinic.management.repo;


import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.hl7.fhir.r4.model.Appointment;

public interface AppointmentRepository extends CrudRepository<Appointment, String> {
	
	@Query("{ '_id.myStringValue' : ?0 }")
	Appointment findbyUUID(String uuid);
	
	@DeleteQuery("{ '_id.myStringValue' : ?0 }")
	Long deletebyUUID(String uuid);
}
