package fr.insa.projetIntegrateur.DataMS.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.awt.image.BufferedImage;


import javax.imageio.ImageIO;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.projetIntegrateur.DataMS.model.DataSet;


@RestController
@RequestMapping("/data")
public class DataMSRessource {
	
	// Data request for TRAINING (or prediction ?)
	@GetMapping(value = "/train/{nbImg}", produces=MediaType.APPLICATION_JSON_VALUE) 
	public DataSet getDataSet(@PathVariable("nbImg") int nbImg){
		DataSet dataSet = new DataSet(nbImg);
		// Put the right parameters in the function below !
		dataSet.addImagesToDataSet();
		return dataSet;
	}
	
	// Data request for PREDICTION (one image)
	@GetMapping(value="/prediction/{img}", produces=MediaType.APPLICATION_JSON_VALUE)
	public DataSet getImage(@PathVariable("img") int nbImg){
		
		/* For now we get a local file image.
		BufferedImage image = null;
		File file = new File("bmp_Image003.bmp");
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		*/
		
		DataSet dataSet = new DataSet(nbImg);
		// Put the right parameters in the function below !
		dataSet.addImagesToDataSet();
		System.out.println("---------------------------------------------\n"
					     + "----------------- ME OUI C KLER -------------\n"
					     + "---------------------------------------------\n");
		return dataSet;
		}
	
	
}
