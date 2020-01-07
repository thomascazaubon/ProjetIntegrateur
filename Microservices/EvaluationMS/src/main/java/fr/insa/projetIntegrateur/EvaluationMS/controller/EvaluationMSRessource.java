package fr.insa.projetIntegrateur.EvaluationMS.controller;

import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.insa.projetIntegrateur.EvaluationMS.model.Results;
	
@RestController
@RequestMapping("/evaluation")
public class EvaluationMSRessource {
	
	@GetMapping(value="/saveResults", consumes=MediaType.APPLICATION_JSON_VALUE)
	public void saveResults(Results resultsToSave) {
		resultsToSave.print();
		
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(resultsToSave);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		FileWriter fileToSave = null;
		try {
			fileToSave = new FileWriter("./"+resultsToSave.getAlgorithm()+java.time.Clock.systemUTC().instant()+".json");
			fileToSave.append(json);
			fileToSave.flush();
			fileToSave.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

