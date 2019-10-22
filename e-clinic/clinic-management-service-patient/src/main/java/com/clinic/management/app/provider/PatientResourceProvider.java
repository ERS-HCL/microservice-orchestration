package com.clinic.management.app.provider;

import java.util.List;

import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clinic.management.repo.PatientRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import io.zeebe.client.ZeebeClient;
@Component
public class PatientResourceProvider extends AbstractJaxRsResourceProvider<Patient> {

    @Autowired
	private PatientRepository patientRepository;
    
	@Autowired
	  private ZeebeClient zeebe;
  
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
        
        
		com.clinic.management.app.flow.Encounter e= new com.clinic.management.app.flow.Encounter();
		com.clinic.management.app.flow.Patient pat= new com.clinic.management.app.flow.Patient();
		e.setEncounterId(patient.getId());
		pat.setName(patient.getNameFirstRep().getFamily());
		pat.setAddress(patient.getAddress().get(0).getLine().toString()+","+patient.getAddress().get(0).getCity()+","+patient.getAddress().get(0).getCountry());
		e.setPatient(pat);
		String json=null;
		ObjectMapper mapper = new ObjectMapper();
		  try {
			 json = mapper.writeValueAsString(e);
			//System.out.println("ResultingJSONstring = " + json);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		 try {           
		      // start a workflow instance / should be basically just send
		      // a message to broker - which will correlate it himself
		      // this is not yet in the current version of zeebe - so we 
		      // have to specify the workflow to start
		      zeebe.newCreateInstanceCommand() //
		        .bpmnProcessId("bp-process-clinic") //
		        .latestVersion() //
		        .variables(json) //
		        .send().join();
		    } catch (Exception ex) {
		      throw new RuntimeException("Could not tranform and send message due to: "+ ex.getMessage(), ex);
		    }
        
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