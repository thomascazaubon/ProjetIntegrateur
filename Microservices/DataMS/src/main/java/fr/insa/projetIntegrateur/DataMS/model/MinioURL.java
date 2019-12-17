package fr.insa.projetIntegrateur.DataMS.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Image")
public class MinioURL {
	// Attributes
	private String url;
	
	// Constructors
	public MinioURL(){
		//
		//
		//
	}
	
	// Methods
	public void linkWithMinio(int id) {
		 // Get the URL of a random image or same image every time.
		
		// Depending on the ID, get the according URL. For now it's just a generic string.
		url = "urlImageTest";
		
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
