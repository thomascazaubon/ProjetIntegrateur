package fr.insa.projetIntegrateur.DataMS.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.awt.image.BufferedImage;


import javax.imageio.ImageIO;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
import io.minio.errors.NoResponseException;
import io.minio.messages.Item;


@RestController
@RequestMapping("/data")
public class DataMSRessource {
	
	// Data request for one image
	@GetMapping(value="/{img}", produces=MediaType.APPLICATION_JSON_VALUE)
	public void getImage(@PathVariable("img") int nbImg){
		System.out.println("\n\n************** RECEIVED ***************\n\n");
		getImages(nbImg);
		System.out.println("Zouper");
	}
	
	private void getImages(int nbImg) {
		int imagesToRetrieve = nbImg;
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
						File targetFile = new File("./images/"+fileName);
			    		ImageIO.write(image, "jpg", targetFile);
			    		System.out.print((i + 1) + " ");
			    		success = true;
					} catch (InvalidKeyException | InvalidBucketNameException | NoSuchAlgorithmException
							| InsufficientDataException | NoResponseException | ErrorResponseException | InternalException
							| InvalidArgumentException | InvalidResponseException | IOException | XmlPullParserException e) {
						System.out.println("\n\n*** " + fileName + " cannot be retrieved ! ***\n");
					}
				}
			}

			long end = System.currentTimeMillis();
			System.out.println("\n\n*** Took " + ((end - start)/1000) + " seconds to complete.***\n\n");
			
		} catch (InvalidEndpointException | InvalidPortException | XmlPullParserException e1) {
			System.out.println("************ AH GROS ON EST LA HEIN ************");
			e1.printStackTrace();
		}
	}
	
	
}
