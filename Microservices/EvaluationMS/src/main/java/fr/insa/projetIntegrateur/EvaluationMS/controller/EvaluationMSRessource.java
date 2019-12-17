package fr.insa.projetIntegrateur.EvaluationMS.controller;

import java.util.List;
import java.util.Arrays;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.projetIntegrateur.EvaluationMS.model.ImageInfos;
	
@RestController
@RequestMapping("/info")
public class EvaluationMSRessource {
	
	@GetMapping("/{id}")
	public ImageInfos getIDImages(@PathVariable("id") int id){
			
		List<ImageInfos> listInfos = Arrays.asList(
				new ImageInfos(0,"Carotte"),
				new ImageInfos(1,"Pain"),
				new ImageInfos(2,"Rideaux"),
				new ImageInfos(3, "Fraise")
				);
		
		return listInfos.get(id);
	}
}

