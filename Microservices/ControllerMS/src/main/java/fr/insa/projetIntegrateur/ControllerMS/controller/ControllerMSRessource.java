package fr.insa.projetIntegrateur.ControllerMS.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.insa.projetIntegrateur.ControllerMS.model.*;

@RestController
@RequestMapping("/controller")
public class ControllerMSRessource {
	
	@GetMapping("/train/{nbImg}")
	public String train(@PathVariable("nbImg") int nbImg){
		// Result initialized with "failed" but will contain the class of the image given with "id".  
		String output = "Failed";
		
		RestTemplate restTemplate = new RestTemplate();
		
		// Get the right URL to the right image.
		DataSet murl = restTemplate.getForObject("http://localhost:8082/data/train/" + nbImg, DataSet.class);
		String url = murl.getUrl();
		// Prediction with Python script.
		Results res = restTemplate.getForObject("http://localhost:8084/evaluation/saveResults", Results.class);
		output = res.getResult();
		
		return output;
	}
	
	@GetMapping("/pred/{img}")
	public String getPrediction(@PathVariable("img") int img){
		// Result initialized with "failed" but will contain the class of the image given with "id".  
		String output = "Failed";
		
		RestTemplate restTemplate = new RestTemplate();
		
		// Creating our classes.
		//MinioURL murl = new MinioURL();
		//Prediction pred = new Prediction();
		
		// Get the right URL to the right image.
		Image murl = restTemplate.getForObject("http://localhost:8082/data/prediction/" + img, Image.class);
		String url = murl.getUrl();
		// Prediction with Python script.
		Prediction pred = restTemplate.getForObject("http://localhost:8083/prediction/" + url, Prediction.class);
		output = pred.getResult();
		
		return output;
		
		/*
		int i = 0;
		while(i<imagesID.getImageList().size()){
			// Get info (EvaluationMS)
			ImageInfos imageInfo = restTemplate.getForObject("http://evaluation_Service/info/"+i, ImageInfos.class);
			
			// Call MS to get image prediction (ModelMS)
			Prediction pred = restTemplate.getForObject("http://model_Service/prediction/"+i, Prediction.class);
			
			// Instanciate image with its id, dechet, prediction and put it in the list
			listImages.add(new Images(i,imageInfo.getDechet(),pred.getScore()));
			i++;
		}
		*/
	}

}