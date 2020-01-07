package fr.insa.projetIntegrateur.ControllerMS.model;

import java.util.ArrayList;

public class Results {
	private String algorithm;
	private String parameters;
	private ArrayList<String> true_classes;
	private ArrayList<String> predicted_classes;
	
	public Results(String algorithm, String parameters, ArrayList<String> true_classes, ArrayList<String> predicted_classes) {
		this.algorithm = algorithm;
		this.parameters = parameters;
		this.true_classes = true_classes;
		this.predicted_classes = predicted_classes;
	}
	
	public void print() {
		System.out.println("[RESULTS] " + this.algorithm + " " + this.parameters);
		System.out.println("\t" + this.true_classes);
		System.out.println("\t" + this.predicted_classes);
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
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
