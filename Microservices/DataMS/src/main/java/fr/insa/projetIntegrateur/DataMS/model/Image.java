package fr.insa.projetIntegrateur.DataMS.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Image")
public class Image {
	// Attributes
	private String url;
	
	// Constructors
	public Image(){
		//
	}
	
	// Methods
	public void getImage(int img) {
		// Create a JSON object for the image to predict.
		
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
