package com.clinic.management.test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Goal;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;

import ca.uhn.fhir.context.FhirContext;

public class Main {
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		System.out.println("Test...");
	
		// Log the request
		FhirContext ctx = FhirContext.forR4();
		
		try {
			FileReader reader = new FileReader("C:\\Users\\sundaramoorthim\\eclipse-workspace_zeebe_git\\TestFhir\\appClientModule\\Abbott509_Adrianna134_57.json");
		    ca.uhn.fhir.parser.IParser parser = ctx.newJsonParser().setPrettyPrint(true);

		      Bundle bundle1 = parser.parseResource(Bundle.class, reader);
		        for (Bundle.BundleEntryComponent entry : bundle1.getEntry()) {
		        	System.out.println(entry.getResource().getResourceType());
		        //	System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(entry.getResource()));
		        	
		        	
		            if (entry.getResource() instanceof Patient) {
		            	Patient patient = (Patient) entry.getResource();
		            	System.out.println("Name ::"+patient.getName().get(1).getNameAsSingleString());
		              }
		            
		            if (entry.getResource() instanceof Goal) {
		            	Goal goal = (Goal) entry.getResource();
		            //	System.out.println("Goal ::"+goal.getAchievementStatus().getText());
		              }
		            
		            if (entry.getResource() instanceof Observation) {
		            	Observation observation = (Observation) entry.getResource();
		            	System.out.println("Observation ::"+observation.getCode().getText());
		              }
		        }
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

	/* (non-Java-doc)
	 * @see java.lang.Object#Object()
	 */
	public Main() {
		super();
	}

}