package com.clinic.management.repo;


import org.hl7.fhir.r4.model.Patient;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, String> {
	
	@Query("{ '_id.myStringValue' : ?0 }")
	Patient findbyUUID(String uuid);
	
	@DeleteQuery("{ '_id.myStringValue' : ?0 }")
	Long deletebyUUID(String uuid);
}
