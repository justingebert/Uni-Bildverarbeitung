// BV Ue7 WS2022/23 Vorgabe
//
// Copyright (C) 2022 by Klaus Jung
// All rights reserved.
// Date: 2022-09-20 		   		    	 

package bv_ws2223;

 		   		    	 
public class DiscreteCosineTransform {
	 		   		    	 
	public void processDCT(RasterImage originalImage, RasterImage dctImage, RasterImage reconstructedImage, int numCoefficients) {
		
		// TODO: Encode the originalImage with DCT, 
		// visualize the DCT coefficients in dctImage, and
		// decode the DCT coefficients into reconstructedImage.
		
		// Optional: only decode the first numCoefficients coefficients for each 8x8 block using zigzag scan order
		
	}
 		   		    	 
	public double getMSE(RasterImage originalImage, RasterImage reconstructedImage) {
		
		double mse = Double.NaN;
		
		// TODO: calculate and return the Mean Square Error between the given images
		
		return mse;
	}

 		   		    	 
} 		   		    	 

 		   		    	 




