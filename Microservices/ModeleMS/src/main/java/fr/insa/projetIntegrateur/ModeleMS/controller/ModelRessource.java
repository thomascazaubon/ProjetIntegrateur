package fr.insa.projetIntegrateur.ModeleMS.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.projetIntegrateur.ModeleMS.model.DataSet;
import fr.insa.projetIntegrateur.ModeleMS.model.Results;

@RestController
@RequestMapping("/model")
public class ModelRessource {
	
	/* T R A I N I N G */
	@PostMapping(value="/train", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String train(@RequestBody DataSet dataSet) {
		String status = "Failed.";
		// TODO
		
		// If everything goes well.
		status = "Complete.";
		return status;
	}
	
	/* P R E D I C T I O N */
	// PREDICT on one single image
	@PostMapping(value="/predictOne", consumes=MediaType.APPLICATION_JSON_VALUE)
	public Results predictOne(@RequestBody DataSet resultsToSave) {
		Results res = new Results();
		// TODO
		
		return res;
	}
	// PREDICT on DataSet
	@PostMapping(value="/predict", consumes=MediaType.APPLICATION_JSON_VALUE)
	public Results predict(@RequestBody DataSet resultsToSave) {
		Results res = new Results();
		// TODO
		
		return res;
	}
}