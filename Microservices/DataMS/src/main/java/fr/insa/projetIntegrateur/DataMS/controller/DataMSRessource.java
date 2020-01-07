package fr.insa.projetIntegrateur.DataMS.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.projetIntegrateur.DataMS.model.*;

@RestController
@RequestMapping("/data")
public class DataMSRessource {
	
	@GetMapping(value = "/train/{nbImg}", produces=MediaType.APPLICATION_JSON_VALUE) 
	public DataSet getDataSet(@PathVariable("nbImg") int nbImg){
		DataSet murl = new DataSet();
		murl.getDataSet(nbImg);
		return murl;
	}
	
	@GetMapping(value="/predict/{img}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Image getImage(@PathVariable("img") int img){
		Image murl = new Image();
		murl.getImage(img);
		return murl;
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
