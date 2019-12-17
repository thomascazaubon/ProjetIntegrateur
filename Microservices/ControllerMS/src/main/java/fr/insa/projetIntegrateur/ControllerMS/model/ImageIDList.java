package fr.insa.projetIntegrateur.ControllerMS.model;

import java.util.ArrayList;
import java.util.List;

public class ImageIDList {
	// Attributes
	List <Integer> imageList;
	
	// Constructor
	public ImageIDList(){
		this.imageList = new ArrayList<Integer>();
	}
	
	// Empty constructor for deserialization
	//public ImageIDList(){}

	// Methods
	public void addImageToList(Integer imageID) {
		this.imageList.add(imageID);
	}
	
	// Getters & setters
	public List<Integer> getImageList() {
		return imageList;
	}

}

