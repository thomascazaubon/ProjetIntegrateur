package fr.insa.projetIntegrateur.ModeleMS.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.projetIntegrateur.ModeleMS.model.Prediction;

@RestController
@RequestMapping("/prediction")
public class PredictionRessource {
	
	@GetMapping("/{url}")
	public Prediction getPrediction(@PathVariable("url") String url){
		Prediction pred = new Prediction();
		
		pred.predict(url);
		
		return pred;
	}
}