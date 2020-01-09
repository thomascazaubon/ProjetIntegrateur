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
	public BufferedImage getImage(@PathVariable("img") int img){
		
		// For now we get a local file image.
		BufferedImage image = null;
		File file = new File("bmp_Image003.bmp");
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		/*
		byte[] matrix = null;
		File file1 = new File("bmp_Image003.bmp");
		try {
			matrix = Files.readAllBytes(file1.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		return image;
	}
	
	/*
	@GetMapping("images")
	public String nb_images(){
		return "Il y a 36 457 900 images en ligne !";
	}
	// @PathVariable est remplacé par @RequestBody si le paramètre est un objet.
	@GetMapping(value = "newImage/{id}")
	public String newImage(@PathVariable int id){
		Image image = new Image(id);
		return "L'image n°" + image.getId() + " a bien été créée.";
	}
	@GetMapping(value = "findImage/{id}")
	public String findImage(@PathVariable int id){
		String reponse = "Désolé.e.s ... \nVotre image n'est pas ici, il n'y a pas encore de mémoire.";	
		return reponse;
	}
	// Retour XML :
	@GetMapping(value = "imagesXML/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public Image infoImageXML(@PathVariable int id){
		Image image = new Image(id);
		return image;
	}
	*/
}
