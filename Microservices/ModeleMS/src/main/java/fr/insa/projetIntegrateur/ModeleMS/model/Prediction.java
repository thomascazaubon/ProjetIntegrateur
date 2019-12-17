package fr.insa.projetIntegrateur.ModeleMS.model;

public class Prediction {
	
	// Attributes
		private String result;
		
		// Constructor
		public Prediction() {
			//
			//
		}
		
		// Methods
		public void predict(String url){
			// Getting the right image from the DB
			// image = minio.getMachinTrucBidule(url);
			
			// Python script called here. For now it's a generic string.
			// result = pyScript(imagePixel);
			this.setResult("organic for "+ url);
		}

		// Getters & setters
		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}
	
}
