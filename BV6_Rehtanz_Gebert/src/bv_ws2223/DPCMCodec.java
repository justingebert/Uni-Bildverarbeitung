// BV Ue6 WS2022/23 Vorgabe
//
// Copyright (C) 2022 by Klaus Jung
// All rights reserved.
// Date: 2022-09-20
 		   		    	 

package bv_ws2223;


public class DPCMCodec {
	 		   		    	 
	public enum PredictionType { 
		A("A (horizontal)"), 
		B("B (vertical)"), 
		C("C (diagonal)"),
		ABC("A+B-C"), 
		AB_MEAN("(A+B)/2"),
		ADAPTIVE("adaptive");
		
		private final String name;       
	    private PredictionType(String s) { name = s; }
	    public String toString() { return this.name; }
	};


	public void processDPCM(RasterImage originalImage, RasterImage errorImage, RasterImage reconstructedImage, double quantizationDelta, PredictionType type) {
		int offsetHori = 0;
		int offsetVert = 0;
		//TODO if schleifen nach type

		if (type == PredictionType.A){
			offsetHori = -1;
		} else if (type == PredictionType.B) {
			offsetVert = -1; 
		} else if (type == PredictionType.C) {
			offsetHori = -1;
			offsetVert = -1;
		}

		int A;
		int B;
		int C;

		int width = originalImage.width;
		int height = originalImage.height;
		for(int y = 0;y<height;y++){
			for(int x = 0;x<width;x++){
				int pos = y*width+x;
				int argb = originalImage.argb[pos];
				int value =(argb >> 16) & 0xff;

				int xOffset = x + offsetHori;
				int yOffset = y + offsetVert;

				int valuePrev;
				int argbPrev;

				int valuePrevA = 0;
				int valuePrevB = 0;
				int valuePrevC = 0;

				valuePrev = 0;
				if(xOffset>0 && yOffset>0){
					A = originalImage.argb[yOffset * width + (xOffset - 1)];
					B = originalImage.argb[(yOffset  - 1) * width + xOffset];
					C = originalImage.argb[(yOffset  - 1) * width + (xOffset - 1)];
					valuePrevA =(A >> 16) & 0xff;
					valuePrevB =(B >> 16) & 0xff;
					valuePrevC =(C >> 16) & 0xff;
				}

				if (type == PredictionType.ABC) valuePrev = valuePrevA+valuePrevB-valuePrevC;
				else if (type == PredictionType.AB_MEAN) valuePrev = (valuePrevA+valuePrevB)/2;
				else if (type == PredictionType.ADAPTIVE) {
					if(Math.abs(valuePrevA-valuePrevC)<Math.abs(valuePrevB-valuePrevC)){
						valuePrev = valuePrevB;
					}else{
						valuePrev = valuePrevA;
					}
				} else {

					int posOffset = yOffset*width + xOffset;

					if (posOffset < 0) posOffset = 0;
					argbPrev = originalImage.argb[posOffset];
					valuePrev = (argbPrev >> 16) & 0xff;
				}

				/*switch (type){
					case ABC: System.out.println("works");
				}*/

				if (xOffset < 0) valuePrev = 128;
				if (yOffset < 0) valuePrev = 128;

				int e = valuePrev - value;
				int eImage = e +128;

				int eStrich = e;

				if (eImage > 255) eImage = 255;
				else if (eImage < 0) eImage = 0;

				errorImage.argb[pos] = (0xFF<<24) | (eImage<<16) | (eImage<<8) | eImage;

				int sRecon = valuePrev + eStrich;
				reconstructedImage.argb[pos] = (0xFF<<24) | (sRecon<<16) | (sRecon<<8) | sRecon;

			}
		}
		// TODO: Encode the originalImage with DPCM using the given prediction type, 
		// visualize the prediction error in errorImage, and
		// decode the prediction error into reconstructedImage.
		
		// Hint: You can implement encoding and decoding with a single iteration over the pixels of the given image.
		
		// Optional: Implement DPCM with quantization. The quantization step size is given in quantizationDelta.
		

	}
	
	public double getMSE(RasterImage originalImage, RasterImage reconstructedImage) {
		
		double mse = Double.NaN;
		
		// TODO: calculate and return the Mean Square Error between the given images
		int width = originalImage.width;
		int height = originalImage.height;
		double sum = 0;
		int count = 0;
		for(int y = 0;y<height;y++) {
			for (int x = 0; x < width; x++) {
				int pos = y*width+x;
				int argbOrig = originalImage.argb[pos];
				int argbRecon = reconstructedImage.argb[pos];

				int valueOrig =(argbOrig >> 16) & 0xff;
				int valueRecon =(argbRecon >> 16) & 0xff;

				sum += Math.pow(valueOrig-valueRecon,2);
				count += 1;
			}
		}
		mse = 1.0/count * sum;
		
		return mse;
	}
	
 		   		    	 
}
 		   		    	 







