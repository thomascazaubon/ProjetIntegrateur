package fr.insa.projetIntegrateur.ModeleMS.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Results {
	private String algorithm;
	private HashMap<String, Integer> parameters; 
	private ArrayList<String> true_classes;
	private ArrayList<String> predicted_classes;
	
	public Results() {
	}
	
	public Results(String algorithm, HashMap<String, Integer> parameters, ArrayList<String> true_classes, ArrayList<String> predicted_classes) {
		this.algorithm = algorithm; 
		this.parameters = parameters; 
		this.true_classes = true_classes; 
		this.predicted_classes = predicted_classes; 
	}
	
	public void print() {
		System.out.println("[RESULTS] " + this.algorithm + " - " + this.parameters.toString().replace("{", "").replace("}", ""));
		System.out.println("\t" + this.true_classes);
		System.out.println("\t" + this.predicted_classes);
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public HashMap<String, Integer> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<String, Integer> parameters) {
		this.parameters = parameters;
	}

	public ArrayList<String> getTrue_classes() {
		return true_classes;
	}

	public void setTrue_classes(ArrayList<String> true_classes) {
		this.true_classes = true_classes;
	}

	public ArrayList<String> getPredicted_classes() {
		return predicted_classes;
	}

	public void setPredicted_classes(ArrayList<String> predicted_classes) {
		this.predicted_classes = predicted_classes;
	}
}
