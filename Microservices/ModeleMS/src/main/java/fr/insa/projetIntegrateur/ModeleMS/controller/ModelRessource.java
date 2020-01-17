package fr.insa.projetIntegrateur.ModeleMS.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

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
	@GetMapping(value="/predictOne", produces=MediaType.APPLICATION_JSON_VALUE)
	public Results predictOne() {
		Results res = new Results();
		// Construire le dossier 
		// String nomDossier;
		
		ArrayList<String> output = callModel("predictOne", "dataSet");
		
		res.setAlgorithm(output.get(0));
		
		res.setParameters(new HashMap<String, Integer>());
		res.getParameters().put(output.get(1).split(" ")[0], Integer.parseInt(output.get(1).split(" ")[1]));
		
		res.setTrue_classes(new ArrayList<String>());
		res.getTrue_classes().add(output.get(2));
		res.setPredicted_classes(new ArrayList<String>());
		res.getPredicted_classes().add(output.get(3));
		res.print();
		
		return res;
	}
	// PREDICT on DataSet
	@GetMapping(value="/predictAll")
	public Results predict(@RequestBody DataSet dataSet) {
		Results res = new Results();
		
		// Construire le dossier, mettre l'image dedans
		//String nomDossier;
		
		ArrayList<String> output = callModel("predictAll", "dataSet");
		
		res.setAlgorithm(output.get(0));
		//res.setParameters(output.get(1));
		res.getTrue_classes().add(output.get(2));
		res.getPredicted_classes().add(output.get(3));		
		return res;
	}
	
	
	private ArrayList<String> callModel(String action, String nomDossier) {
		System.out.println("In callModel");

		ArrayList<String> returned = new ArrayList<String>();
		try {	
			// Execute the Python script
			String command = null;
		
			if (action == "train") {
				command = "python3 projet_test/model.py -a train";
			} else if (action == "predictOne") {
				command = "python3 projet_test/model.py -a predictOne";
			}
			else {
				command = "python3 projet_test/model.py -a predictAll";
			}
			/*		
			String[] com = {
	                "/bin/bash",
	                "-c",
	                "source /Users/admin/anaconda/bin/activate envFastai && " + command
	        };
	        */
			System.out.println("command = " + command);
			Process p = Runtime.getRuntime().exec(command);
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
			String line;
			while((line = in.readLine()) != null) {
				System.out.println(line);
				returned.add(line);
			}
			System.out.println("Retour = " + returned);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("CallModel ended.");

		
		return returned;
	}	
}

