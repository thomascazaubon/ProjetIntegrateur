package fr.insa.projetIntegrateur.EvaluationMS.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.insa.projetIntegrateur.EvaluationMS.model.Results;
	
@RestController
@RequestMapping("/evaluation")
public class EvaluationMSRessource {
	
	@PostMapping(value="/saveResults", consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] saveResults(@RequestBody Results resultsToSave) {
		/* 
		 * Input : Results instance as JSON
		 * 
		 * Saves the results as json files in the results repository.
		 * Create a png analyses with a confusion matrix in the charts repository. 
		 * 
		 * Return : png with confusion matrix
		 */
		
		// Debug
		resultsToSave.print();
		
		// Read the json data given by the client
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(resultsToSave);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		// Write the json data in a file 
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	    LocalDateTime now = LocalDateTime.now(); 
		String fileName = resultsToSave.getAlgorithm()+ "-";
		for (Map.Entry<String, Integer> e : resultsToSave.getParameters().entrySet()) {
			fileName += e.getKey() + "_" + e.getValue() + "-";
	    }
		fileName += dtf.format(now).replace("/", "_").replace(":", "_").replace(" ", "-")+".json";
		FileWriter fileToSave = null;
		try {
			fileToSave = new FileWriter("./results/" + fileName);
			fileToSave.append(json);
			fileToSave.flush();
			fileToSave.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Generate and get the png (filename) corresponding to the results
		String pngName = getPngName(fileName);
	    File image = new File("./charts/" + pngName);
		byte[] pixels = null;
		try {
			pixels = Files.readAllBytes(image.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return pixels;
	}
	
	private String getPngName(String resultsPath) {
		// Call the Python script that generates png
		// Return the filename of the generated png
		
		String pngName = null;
		try {	
			// Execute the Python script
			String command = null;
			if (resultsPath == null) {
				command = "python display.py";
			} else {
				command = "python display.py -f " + resultsPath;
			}
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
			pngName = in.readLine();
			System.out.println("pngName = " + pngName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Return the filename of the generated png
		return pngName;
	}
		
	@GetMapping(value = {"/getResults", "/getResults/{resultsName}"}, produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] getResults(@PathVariable(name="resultsName", required=false) String resultsName) throws IOException {	 
		// Get the png filename corresponding to the given results
		String pngName = getPngName(resultsName);
		
		// Read the png file
	    File image = new File("./charts/" + pngName);
		byte[] pixels = null;
		try {
			// Transform the png in bytes so it can be send to another service
			pixels = Files.readAllBytes(image.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Return the png (as bytes)
		return pixels;
	}
}

