package fr.insa.projetIntegrateur.ControllerMS.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.insa.projetIntegrateur.ControllerMS.model.*;

@RestController
@RequestMapping("/controller")
public class ControllerMSRessource {
	
	@GetMapping("/train/{nbImg}")
	public String train(@PathVariable("nbImg") int nbImg){
		
		RestTemplate restTemplate = new RestTemplate();
		
		// Get the right dataset.
		restTemplate.getForObject("http://192.168.0.9:8088/data/" + nbImg, Void.class);
		
		String status = restTemplate.getForObject("http://192.168.0.9:8083/model/train/", String.class);
		
		return "Training status : " + status + ". With " + nbImg + " images.";
	}
	
	@GetMapping("/testDataMS/{nbImg}")
	public String test(@PathVariable("nbImg") int nbImg){
		
		RestTemplate restTemplate = new RestTemplate();
		
		// Get the right dataset.
		restTemplate.getForObject("http://192.168.0.9:8088/data/" + nbImg, Void.class);
		
		//String status = restTemplate.postForObject("http://localhost:8083/model/train/", dataSet, String.class);
		
		return "Ok boomer.";
	}
	
	@GetMapping(value = "/prediction/{img}", produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] getPrediction(@PathVariable("img") int img){
		// Result initialized, will contain the class of the images given, the algorithm etc.  
		byte[] matrix;
		
		RestTemplate restTemplate = new RestTemplate();

		// Get the image
		restTemplate.getForObject("http://192.168.0.9:8088/data/" + img, Void.class);

		// Prediction with Python script.
		Results res = restTemplate.getForObject("http://192.168.0.9:8083/model/predictOne", Results.class);
		
		// Send to the evaluation MS
		matrix = restTemplate.postForObject("http://192.168.0.9:8084/evaluation/saveResults", res, byte[].class);

		// Return the results
		return matrix;

	}

}