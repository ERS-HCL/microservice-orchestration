package com.clinic.management.app.flow;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.ParserOptions;

@RestController
public class PatientController {


    MongoClient mongo= MongoClients.create();
	MongoDatabase database = mongo.getDatabase("test-fhir");
	MongoCollection<Document> collection = database.getCollection("patient");

    
    @GetMapping("/patient")
    public <T> List<T> patient() {
    	
    	List<T> resultMapList = new ArrayList<T>();
        FindIterable<Document> findIterable = collection.find();
    
        MongoCursor<Document> cursor = findIterable.iterator();
        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                T parseObject = (T) JSON.parse(document.toJson());
                resultMapList.add(parseObject);
            }
        } finally {
            cursor.close();
        }

        return resultMapList;
    }
    
    @PostMapping("/patient")
    String newEmployee(@RequestBody String newpatient) {
    	
    	Document document =Document.parse(newpatient);
    	FhirContext ctx = FhirContext.forR4();
    	ctx.setParserOptions(new ParserOptions().setOverrideResourceIdWithBundleEntryFullUrl(false));
		
		//ctx.setParserOptions(new ParserOptions().setDontStripVersionsFromReferencesAtPaths("Patient.fullUrl"));


	    ca.uhn.fhir.parser.IParser parser = ctx.newJsonParser().setPrettyPrint(true);
    	
    	Patient pat=parser.parseResource(Patient.class,document.toJson());
    	
    	System.out.println("Output :: Patient :::"+pat.getId());
    	//collection.insertOne(document);
    	//Bson filter = Filters.eq("id", patient.getId().substring(patient.getId().indexOf("/")+1));
    	//collection.replaceOne(filter, document,new ReplaceOptions().upsert(true));
      return "Done";
    }
}
