package fr.insa.projetIntegrateur.EvaluationMS.model;

public class ImageInfos {
	// Attributes
	private int id;
	private String dechet;
	
	// Constructor
	public ImageInfos(int id, String dechet) {
		super();
		this.id = id;
		this.dechet = dechet;
	}
	public ImageInfos(){
		
	}

	
	// Getters & setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDechet() {
		return dechet;
	}
	public void setDechet(String dechet) {
		this.dechet = dechet;
	}
	
}
