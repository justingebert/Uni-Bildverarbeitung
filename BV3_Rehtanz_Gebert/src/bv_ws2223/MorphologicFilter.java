// BV Ue3 WS2022/23 Vorgabe
//
// Copyright (C) 2022 by Klaus Jung
// All rights reserved.
// Date: 2022-09-20
 		   		    	 

package bv_ws2223;

public class MorphologicFilter {
 		   		    	 
	// filter implementations go here:
	
	public void copy(RasterImage src, RasterImage dst) {
		// TODO: just copy the image
		System.arraycopy(src.argb,0,dst.argb,0,src.argb.length);
	}
	
	public void dilation(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// kernel's first dimension: y (row), second dimension: x (column)
		// TODO: dilate the image using the given kernel
	}
 		   		    	 
	public void erosion(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// This is already implemented. Nothing to do.
		// It will function once you implemented dilation and RasterImage invert()
		src.invert();
		dilation(src, dst, kernel);
		dst.invert();
		src.invert();
	}
	
	public void opening(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// TODO: implement opening by using dilation() and erosion()
	}
	
	public void closing(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// TODO: implement closing by using dilation() and erosion()
	}
	
	
 		   		    	 	
	

}
 		   		    	 




