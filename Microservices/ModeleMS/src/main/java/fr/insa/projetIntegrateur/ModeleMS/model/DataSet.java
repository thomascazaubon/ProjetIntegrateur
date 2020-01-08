package fr.insa.projetIntegrateur.ModeleMS.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DataSet {
	// Attributes
	private ArrayList<BufferedImage> imgList;
	private int listSize;
	
	// Constructors
	public DataSet(int nbImg) {
		this.listSize = nbImg;
	}
	
	// Method
	public void addImagesToDataSet() {
		int x = this.listSize;
		while (x >= 1) {
			// Add image to imgList.
			
			x = x - 1;
		}
		
	}
	
	// Getters & Setters
	public ArrayList<BufferedImage> getImgList() {
		return imgList;
	}
	public void setImgList(ArrayList<BufferedImage> imgList) {
		this.imgList = imgList;
	}

	public int getListSize() {
		return listSize;
	}
	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
	
	
}


