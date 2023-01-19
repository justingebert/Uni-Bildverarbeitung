// BV Ue7 WS2022/23 Vorgabe
//
// Copyright (C) 2022 by Klaus Jung
// All rights reserved.
// Date: 2022-09-20 		   		    	 

package bv_ws2223;

 		   		    	 
public class DiscreteCosineTransform {
	 		   		    	 
	public void processDCT(RasterImage originalImage, RasterImage dctImage, RasterImage reconstructedImage, int numCoefficients) {
		
		// TODO: Encode the originalImage with DCT,
		for(int x = 0;x<8;x++){
			for(int y = 0;x<8;y++){

			}
		}
		// visualize the DCT coefficients in dctImage, and
		// decode the DCT coefficients into reconstructedImage.
		
		// Optional: only decode the first numCoefficients coefficients for each 8x8 block using zigzag scan order
		
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

 		   		    	 




