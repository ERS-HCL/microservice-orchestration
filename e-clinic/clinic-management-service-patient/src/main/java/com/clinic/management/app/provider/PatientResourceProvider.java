package com.clinic.management.app.provider;

import java.util.List;

import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clinic.management.repo.PatientRepository;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.jaxrs.server.AbstractJaxRsResourceProvider;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.Delete;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;

@Component
public class PatientResourceProvider extends AbstractJaxRsResourceProvider<Patient> {

    @Autowired
	private PatientRepository patientRepository;
  
    public PatientResourceProvider(FhirContext fhirContext) {
        super(fhirContext);
    }

    @Read
    public Patient find(@IdParam final IdType theId) {
    	if (!theId.getIdPart().isEmpty()) {
        	return patientRepository.findbyUUID(theId.getIdPart());
        } else {
            throw new ResourceNotFoundException(theId);
        }
    }

    @Create
    public MethodOutcome createPatient(@ResourceParam Patient patient) {

        patient.setId(IdDt.newRandomUuid().toString());
        patient.setIdBase(IdDt.newRandomUuid().toString());
        patientRepository.save(patient);
        return new MethodOutcome(patient.getIdElement());
    }

    @Override
    public Class<Patient> getResourceType() {
        return Patient.class;
    }
    
    @Delete
    public MethodOutcome delete(@IdParam final IdType theId) {
        final Patient deletedPatient = find(theId);
        if(deletedPatient !=null)
        {
        	patientRepository.deletebyUUID(theId.getIdPart().toString());
        }
        
        final MethodOutcome result = new MethodOutcome().setCreated(true);
        result.setResource(deletedPatient);
        return result;
    }
    @Search
    public List<Patient> search(@ResourceParam final Patient patient) {
        return (List<Patient>) patientRepository.findAll();
    }


}