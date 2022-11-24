// BV Ue3 WS2022/23 Vorgabe
//
// Copyright (C) 2022 by Klaus Jung
// All rights reserved.
// Date: 2022-09-20
 		   		    	 

package bv_ws2223;

import java.util.Arrays;

public class MorphologicFilter {
 		   		    	 
	// filter implementations go here:
	
	public void copy(RasterImage src, RasterImage dst) {
		// TODO: just copy the image
		System.arraycopy(src.argb,0,dst.argb,0,src.argb.length);
	}
	
	public void dilation(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// kernel's first dimension: y (row), second dimension: x (column)
		// TODO: dilate the image using the given kernel
		Arrays.fill(dst.argb,0xFFFFFFFF);
		for(int y = 0;y<src.height;y++) {
			for (int x = 0; x < src.width; x++) {
				int pos = y*src.width + x;
				int pixSRC = src.argb[pos];
				int r = (pixSRC >> 16) & 0xff;
				int g = (pixSRC >> 8) & 0xff;
				int b =  pixSRC & 0xff;

				if(r == 0 && g == 0 && b == 0){
					for(int i = 0; i< kernel.length;i++){
						for(int j = 0; j<kernel[i].length; j++){
							if(kernel[i][j]){
								int iC = i-kernel.length/2;
								int jC = j-kernel[i].length/2;
								int newPos = (y+iC)*src.width + x+jC;
								//randbehandlung mit x+jc und y+jc
								if(newPos>0 && newPos<src.argb.length-1){
									int rNew = 0;
									int gNew = 0;
									int bNew = 0;
									dst.argb[newPos] =  (0xff << 24) | (rNew << 16) | (gNew << 8) | (bNew);
								}/*else{
									dst.argb[pos] = src.argb[pos];
								}*/
							}
						}
					}
				}
			}
		}

	}
 		   		    	 
	public void erosion(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// This is already implemented. Nothing to do.
		// It will function once you implemented dilation and RasterImage invert()
		src.invert();
		dilation(src, dst, kernel);
		dst.invert();
		src.invert();
	}


	//falschrum um drehen
	public void opening(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// TODO: implement opening by using dilation() and erosion()
		RasterImage imgD = new RasterImage(src.width, src.height);
		dilation(src,imgD,kernel);
		erosion(imgD,dst,kernel);

	}
	
	public void closing(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// TODO: implement closing by using dilation() and erosion()
		RasterImage imgD = new RasterImage(src.width, src.height);
		erosion(src,imgD,kernel);
		dilation(imgD,dst,kernel);

	}

}
 		   		    	 




