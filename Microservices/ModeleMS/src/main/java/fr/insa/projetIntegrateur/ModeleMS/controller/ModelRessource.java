package fr.insa.projetIntegrateur.ModeleMS.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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
		// 
		callModel("train", "nomDossierTrain");
		// If everything goes well.
		status = "Complete.";
		return status;
	}
	
	/* P R E D I C T I O N */
	// PREDICT on one single image
	//@GetMapping(value="/predictOne", consumes=MediaType.APPLICATION_JSON_VALUE)
	//public Results predictOne(@RequestBody DataSet oneImage) {
	@GetMapping(value="/predictOne")
	public Results predictOne() {
		Results res = new Results();
		System.out.println("1");
		// Construire le dossier 
		// String nomDossier;
		
		String output = callModel("predictOne", "dataSet");
		System.out.println("2");

		res.setAlgorithm(output);
		return res;
	}
	// PREDICT on DataSet
	@PostMapping(value="/predict", consumes=MediaType.APPLICATION_JSON_VALUE)
	public Results predict(@RequestBody DataSet dataSet) {
		Results res = new Results();
		
		// Construire le dossier, mettre l'image dedans
		//String nomDossier;
		
		String output = callModel("predictAll", "dataSet");
		
		res.setAlgorithm(output);
		return res;
	}
	
	
	private String callModel(String action, String nomDossier) {
		System.out.println("In callModel");

		String returned = null;
		try {	
			// Execute the Python script
			String command = null;
		
			if (action == "train") {
				command = "python projet_test/model.py -a train -i " + nomDossier;
			} else if (action == "predictOne") {
				command = "python projet_test/model.py -a predictOne -i " + nomDossier;
			}
			else {
				command = "python projet_test/model.py -a predictAll -i " + nomDossier;
			}
						
			String[] com = {
	                "/bin/bash",
	                "-c",
	                "source /Users/admin/anaconda/bin/activate envFastai && " + command
	        };
	        
			System.out.println("command = " + com);
			Process p = Runtime.getRuntime().exec(com);
			System.out.println("Process started.");

			// Wait the Python script to end
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Check error log
			int len;
			if ((len = p.getErrorStream().available()) > 0) {
			  byte[] buf = new byte[len];
			  p.getErrorStream().read(buf);
			  System.err.println("Command error:\t\""+new String(buf)+"\"");
			}
			
			// Read Python output
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			returned = in.readLine();
			System.out.println("Retour = " + returned);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("CallModel ended.");

		
		return returned;
	}
	
	// model.py
	// dossierDATATRAIN
	// dossierPKL
	
	
}