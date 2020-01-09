package fr.insa.projetIntegrateur.ControllerMS.controller;

import java.awt.image.BufferedImage;

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
		DataSet dataSet = restTemplate.getForObject("http://localhost:8082/data/train/" + nbImg, DataSet.class);
		
		String status = restTemplate.postForObject("http://localhost:8083/model/train/", dataSet, String.class);
		
		return "Training status : " + status + ". With " + nbImg + " images.";
	}
	
	@GetMapping(value = "/prediction/{img}", produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] getPrediction(@PathVariable("img") int img){
		// Result initialized, will contain the class of the images given, the algorithm etc.  
		Results res;
		byte[] matrix;
		
		RestTemplate restTemplate = new RestTemplate();
		
		// Get the right URL to the right image.
		BufferedImage image = restTemplate.getForObject("http://localhost:8082/data/prediction/" + img, BufferedImage.class);
		// Prediction with Python script.
		
		res = restTemplate.postForObject("http://localhost:8083/model/predictOne", image, Results.class);
		if (res == null) {
			System.out.println("Result is null.");
		}
		// Send to the evaluation MS
		matrix = restTemplate.postForObject("http://localhost:8084/evaluation/saveResults", res, byte[].class);

		// Return the results
		return matrix;

	}

}