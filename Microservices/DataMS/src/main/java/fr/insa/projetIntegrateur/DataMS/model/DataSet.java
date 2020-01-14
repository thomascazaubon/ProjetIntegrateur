package fr.insa.projetIntegrateur.DataMS.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import org.xmlpull.v1.XmlPullParserException;

import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidArgumentException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.MinioException;
import io.minio.errors.NoResponseException;
import io.minio.messages.Item;

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
		int imagesToRetrieve = this.listSize;
		Random r = new Random();
		System.out.println(imagesToRetrieve + " images asked !");
		// Connection to minio server
		MinioClient minioClient;
		try {
			long start = System.currentTimeMillis();
			minioClient = new MinioClient("http://192.168.0.7", 9000, "minioadmin", "minioadmin");
			//InputStream stream = minioClient.getObject(args[0],args[0]);
			Iterable<Result<Item>> organics = minioClient.listObjects("dataset1","O");
			Iterable<Result<Item>> recyclables = minioClient.listObjects("dataset1","R"); 	
			int organicsLength = 0;
			int recyclablesLength = 0;
			for (Object i : organics){organicsLength++;}
			for (Object i : recyclables){recyclablesLength++;}
			
			for (int i = 0 ; i < imagesToRetrieve ; i++){	
				String fileName;
				boolean success = false;
				while(!success) {
					//Organic			
					if(r.nextInt(2) == 0){
						int index = 1 + r.nextInt(organicsLength + 1);
						fileName = "O_" + index + ".jpg";
					} else {
						//Recyclable
						int index = 1 + r.nextInt(recyclablesLength + 1);
						fileName = "R_" + index + ".jpg";
					}
					
					InputStream stream;
					try {
						stream = minioClient.getObject("dataset1", fileName);
						BufferedImage image = ImageIO.read(stream);
						File targetFile = new File(fileName);
			    		ImageIO.write(image, "jpg", targetFile);
			    		System.out.print((i + 1) + " ");
			    		success = true;
					} catch (InvalidKeyException | InvalidBucketNameException | NoSuchAlgorithmException
							| InsufficientDataException | NoResponseException | ErrorResponseException | InternalException
							| InvalidArgumentException | InvalidResponseException | IOException | XmlPullParserException e) {
						// TODO Auto-generated catch block
						System.out.println("\n\n*** " + fileName + " cannot be retrieved ! ***\n");
					}
				}
			}
			long end = System.currentTimeMillis();
			System.out.println("\n\n*** Took " + ((end - start)/1000) + " seconds to complete.***\n\n");
			
		} catch (InvalidEndpointException | InvalidPortException | XmlPullParserException e1) {
			// TODO Auto-generated catch block
			System.out.println("************ AH GROS ON EST LA HEIN ************");
			e1.printStackTrace();
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

