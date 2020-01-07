package fr.insa.projetIntegrateur.ControllerMS.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DataSet")
public class DataSet {
	// Attributes
	private String url;
	
	// Constructors
	public DataSet(){
		//
	}
	
	// Methods
	public void getDataSet(int nbImg) {
		// Create a JSON object of a dataSet of images.
		
		// For now it's just a generic string.
		url = "DataSetTest";
		
		this.setUrl(url);
	}
	
	// Getters and setters
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}



