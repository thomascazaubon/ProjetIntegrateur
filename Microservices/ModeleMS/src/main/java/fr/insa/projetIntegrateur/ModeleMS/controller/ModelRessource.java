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
		// TODO
		//result = model.py(train, dataSet);
		// If everything goes well.
		status = "Complete.";
		return status;
	}
	
	/* P R E D I C T I O N */
	// PREDICT on one single image
	@PostMapping(value="/predictOne", consumes=MediaType.APPLICATION_JSON_VALUE)
	public Results predictOne(@RequestBody DataSet oneImage) {
		Results res = new Results();
		// TODO
		//String output = callModel("predictOne", oneImage);
		
		//res.setAlgorithm(output);
		
		return res;
	}
	// PREDICT on DataSet
	@PostMapping(value="/predict", consumes=MediaType.APPLICATION_JSON_VALUE)
	public Results predict(@RequestBody DataSet dataSet) {
		Results res = new Results();
		// TODO
		//String output = callModel("predictAll", dataSet);
		
		//res.setAlgorithm(output);
		
		return res;
	}
	
	
	// PREDICT :    << T E S T >>
	@GetMapping("/predictTest")
	public String predictTest() {
		// TODO
		String output = callModel();
		
		//res.setAlgorithm(output);
		
		return output;
	}
	
	//private String callModel(String action, DataSet dataSet) {
	private String callModel() {
		
		String returned = null;
		try {	
			// Execute the Python script
			String command = null;
			/*
			if (action == "train") {
				command = "python model.py";
			} else if (action == "predictOne") {
				command = "python model.py -f 1 " + dataSet;
			}
			else {
				command = "python model.py -f 2 " + dataSet;
			}
			*/
			// T E S T
			command = "python test.py";
			
			System.out.println("command = " + command);
			Process p = Runtime.getRuntime().exec(command);
		
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
		
		// Return the filename of the generated png
		return returned;
	}
	
}