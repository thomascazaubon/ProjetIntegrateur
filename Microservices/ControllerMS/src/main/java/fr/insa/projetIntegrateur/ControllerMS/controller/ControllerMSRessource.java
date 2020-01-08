package fr.insa.projetIntegrateur.ControllerMS.controller;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
		// Result of the training initialized, will contain the time taken, the algorithm etc.  
		//TrainResults res; ???
		
		RestTemplate restTemplate = new RestTemplate();
		
		// Get the right dataset.
		DataSet dataSet = restTemplate.getForObject("http://localhost:8082/data/train/" + nbImg, DataSet.class);
		
		restTemplate.postForObject("http://localhost:8083/data/train/", dataSet, DataSet.class);

		// Train with Python script.
		//Results res = restTemplate.getForObject("http://localhost:8084/evaluation/saveResults", Results.class);
		//output = res.getResult();
		
		return "Training done with " + nbImg + " images.";
	}
	
	@GetMapping("/pred/{img}")
	public String getPrediction(@PathVariable("img") int img){
		// Result initialized, will contain the class of the images given, the algorithm etc.  
		Results res;
		
		RestTemplate restTemplate = new RestTemplate();
		
		// Get the right URL to the right image.
		BufferedImage image = restTemplate.getForObject("http://localhost:8082/data/prediction/" + img, BufferedImage.class);
		// Prediction with Python script.
		res = restTemplate.postForObject("http://localhost:8083/model/predictOne", image, Results.class);
		// Sent to the evaluation MS
		restTemplate.postForObject("http://localhost:8084/evaluation/saveResults", res, void.class);

		// Return the results
		return "[RESULTS] " + res.getAlgorithm() + " " + res.getParameters() + " " + res.getPredicted_classes();
		
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