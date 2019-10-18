package com.clinic.management.app;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Goal;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.ParserOptions;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2


public class PatientApplication implements CommandLineRunner {

	 @Bean
	    public Docket apiDocket() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.any())
	                .paths(PathSelectors.any())
	                .build();
	    }
	 
	
  public static void main(String[] args) throws Exception {
    SpringApplication.run(PatientApplication.class, args);

  }

  
  
@Override
public void run(String... args) throws Exception {
	// TODO Auto-generated method stub

	
	// TODO Auto-generated method stub
	System.out.println("Test...");

	// Log the request
	FhirContext ctx = FhirContext.forR4();
	
	try {
		FileReader reader = new FileReader("C:\\Users\\sundaramoorthim\\eclipse-workspace_zeebe_git\\TestFhir\\appClientModule\\Abbott509_Adrianna134_57.json");
	ctx.setParserOptions(new ParserOptions().setOverrideResourceIdWithBundleEntryFullUrl(false));
		
		//ctx.setParserOptions(new ParserOptions().setDontStripVersionsFromReferencesAtPaths("Patient.fullUrl"));


	    ca.uhn.fhir.parser.IParser parser = ctx.newJsonParser().setPrettyPrint(true);
	    
	  //  parser.setOmitResourceId(false);
	 //   parser.setDontStripVersionsFromReferencesAtPaths("AuditEvent.entity.reference", "Patient.managingOrganization");

	    parser.setStripVersionsFromReferences(false);


    	MongoClient mongo= MongoClients.create();
    	MongoDatabase database = mongo.getDatabase("test-fhir");
	      Bundle bundle1 = parser.parseResource(Bundle.class, reader);
	        for (Bundle.BundleEntryComponent entry : bundle1.getEntry()) {
	        	System.out.println(entry.getResource().getResourceType());
	        //	System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(entry.getResource()));
	        	
	        	
	            if (entry.getResource() instanceof Patient) {
	            	Patient patient = (Patient) entry.getResource();
	            	System.out.println("Name ::"+patient.getName().get(1).getNameAsSingleString());
	            	System.out.println("ID ::"+patient.getId());
	            	System.out.println("ID ::"+patient.getId().substring(patient.getId().indexOf("/")+1));

	    	        	System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(patient));
	    	        //	System.out.println(mongoOperations.getCollectionNames());
	    	        //	DBObject dbObject = (DBObject)JSON.parse((ctx.newJsonParser().encodeResourceToString(entry.getResource())));
	    	        	
	    	        //	BsonDocument document = BsonDocument.parse(ctx.newJsonParser().encodeResourceToString(entry.getResource()));

	    	        //	mongoOperations.insert(document,"organization");MongoClient
	    	        	
	    	        	
	    	        	
	    	           // MongoClient mongo = new MongoClient("localhost", 27017);

	    	        	MongoCollection<Document> collection = database.getCollection("patient");
	    	        	Document document =Document.parse(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(entry.getResource()));
	    	        	//collection.insertOne(document);
	    	        	Bson filter = Filters.eq("id", patient.getId().substring(patient.getId().indexOf("/")+1));
	    	        	collection.replaceOne(filter, document,new ReplaceOptions().upsert(true));
	    	        	
	    	        	
	            //	mongoOperations.insert("patient",Document.parse(ctx.newJsonParser().encodeResourceToString(entry.getResource())));
	            	;
	            	
	              }
	            
	            if (entry.getResource() instanceof Goal) {
	            	Goal goal = (Goal) entry.getResource();
	            	//System.out.println("Goal ::"+goal.getAchievementStatus().getText());
	              }
	            
	            if (entry.getResource() instanceof Observation) {
	            	Observation observation = (Observation) entry.getResource();
	            	System.out.println("Observation ::"+observation.getCode().getText());
	            	MongoCollection<Document> collection = database.getCollection("observation");
    	        	Document document =Document.parse(ctx.newJsonParser().encodeResourceToString(entry.getResource()));
    	        	System.out.println("observation Id"+observation.getId());
    	        	Bson filter = Filters.eq("id", observation.getId().substring(observation.getId().indexOf("/")+1));
    	        	collection.replaceOne(filter, document,new ReplaceOptions().upsert(true));
    	        	//System.out.println("Output ::"+document.toJson());

    	        	//collection.insertOne(document);
    	        	
    	        	
    	        	
    	        	
    	        	 filter = Filters.eq("id", "ec7ba74b-009a-42ac-8617-01aa368d550d");

    	        	
    	        	document=collection.find(filter).first();
    	        	//System.out.println("Output ::"+document);
    	        	//document.remove("_id");
    	        	System.out.println("Output ::"+document);
    	        	//observation=(Observation) ctx.newJsonParser().parseResource(document.toString());
    	        	observation=parser.parseResource(Observation.class,document.toJson());
    	        	
    	        	System.out.println("Output :: observation :::"+observation.getId());
    	        	
	              }
	            
	            
	            
	        }
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	

}

 /* @Bean
  public ZeebeClient zeebe() {
    //ZeebeClient zeebeClient = ZeebeClient.newClient();
	    ZeebeClient zeebeClient = ZeebeClient.newClientBuilder().brokerContactPoint("localhost:26500").build(); 

    return zeebeClient;
  }*/

}
