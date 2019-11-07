package com.clinic.management.app.provider;

import java.util.List;

import org.hl7.fhir.r4.model.Appointment;
import org.hl7.fhir.r4.model.Appointment.AppointmentParticipantComponent;
import org.hl7.fhir.r4.model.Appointment.ParticipantRequired;
import org.hl7.fhir.r4.model.Appointment.ParticipationStatus;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clinic.management.repo.AppointmentRepository;
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
public class AppointmentResourceProvider extends AbstractJaxRsResourceProvider<Appointment> {

    @Autowired
	private AppointmentRepository appointmentRepository;
    
	  
    public AppointmentResourceProvider(FhirContext fhirContext) {
        super(fhirContext);
    }

    @Read
    public Appointment find(@IdParam final IdType theId) {
    	if (!theId.getIdPart().isEmpty()) {
        	return appointmentRepository.findbyUUID(theId.getIdPart());
        } else {
            throw new ResourceNotFoundException(theId);
        }
    }

    
    public MethodOutcome autoCreateAppointment( Appointment appointment,String patientUUID,String name) {

    	appointment.setId(IdDt.newRandomUuid().toString());
    	appointment.setIdBase(IdDt.newRandomUuid().toString());
    	Reference value=new Reference();
    	value.setReference(patientUUID);
    	value.setDisplay(name);
 	
    	appointment.addParticipant().setActor(value).setStatus(ParticipationStatus.ACCEPTED).setRequired(ParticipantRequired.INFORMATIONONLY);
    	
        appointmentRepository.save(appointment);
  
        return new MethodOutcome(appointment.getIdElement());
    }
    
    @Create
    public MethodOutcome createAppointment(@ResourceParam Appointment appointment) {

    	appointment.setId(IdDt.newRandomUuid().toString());
    	appointment.setIdBase(IdDt.newRandomUuid().toString());
        appointmentRepository.save(appointment);
  
        return new MethodOutcome(appointment.getIdElement());
    }

    @Override
    public Class<Appointment> getResourceType() {
        return Appointment.class;
    }
    
    @Delete
    public MethodOutcome delete(@IdParam final IdType theId) {
        final Appointment deletedAppointment = find(theId);
        if(deletedAppointment !=null)
        {
        	appointmentRepository.deletebyUUID(theId.getIdPart().toString());
        }
        
        final MethodOutcome result = new MethodOutcome().setCreated(true);
        result.setResource(deletedAppointment);
        return result;
    }
    @Search
    public List<Appointment> search(@ResourceParam final Appointment patient) {
        return (List<Appointment>) appointmentRepository.findAll();
    }


}