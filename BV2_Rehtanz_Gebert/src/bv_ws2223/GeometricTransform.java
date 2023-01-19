// BV Ue2 WS2022/23 Vorgabe
//
// Copyright (C) 2022 by Klaus Jung
// All rights reserved.
// Date: 2022-09-20
 		   		    	 

package bv_ws2223;


public class GeometricTransform {
 		   		    	 
	public enum InterpolationType { 
		NEAREST("Nearest Neighbour"), 
		BILINEAR("Bilinear");
		
		private final String name;       
	    private InterpolationType(String s) { name = s; }
	    public String toString() { return this.name; }
	};
	
	public void perspective(RasterImage src, RasterImage dst, double angle, double perspectiveDistortion, InterpolationType interpolation) {
		switch(interpolation) {
		case NEAREST:
			perspectiveNearestNeighbour(src, dst, angle, perspectiveDistortion);
			break;
		case BILINEAR:
			perspectiveBilinear(src, dst, angle, perspectiveDistortion);
			break;
		default:
			break;	
		}
		
	}

	private int pos(int x, int y, int width) {
		int position = y * width + x;
		return position;
	}
 		   		    	 
	/**
	 * @param src source image
	 * @param dst destination Image
	 * @param angle rotation angle in degrees
	 * @param perspectiveDistortion amount of the perspective distortion 
	 */
	
	
	public void perspectiveNearestNeighbour(RasterImage src, RasterImage dst, double angle, double perspectiveDistortion) {
 		   		    	 
		// TODO: implement the geometric transformation using nearest neighbour image rendering

		double sin = Math.sin(Math.toRadians(angle));
		double cos = Math.cos(Math.toRadians(angle));

		double s = perspectiveDistortion;


		for(int yDst = 0; yDst < dst.height; yDst++){
			for(int xDst = 0; xDst < dst.width; xDst++){

				int xDstC = xDst - (dst.width/2);
				int yDstC = yDst - (dst.height/2);

				int ySrcC = (int) Math.round(yDstC/(cos-(yDstC*s*sin)));
				int xSrcC = (int) Math.round(xDstC*(s*sin*ySrcC+1));

				int xSrc = xSrcC  + (src.width/2);
				int ySrc = ySrcC  + (src.height/2);

				int srcRGB;

				int posDst = yDst*dst.width +xDst;
				int posSrc;

				if(ySrc > src.height-1 || ySrc < 0 || xSrc > src.width-1 || xSrc < 0){
					srcRGB = 0xFFFFFFFF;
				}
				else{
					posSrc = ySrc*src.width +xSrc;
					srcRGB = src.argb[posSrc];
				}

				dst.argb[posDst]= srcRGB;
			}
		}
		// NOTE: angle contains the angle in degrees, whereas Math trigonometric functions need the angle in radiant
		
	}


	/**
	 * @param src source image
	 * @param dst destination Image
	 * @param angle rotation angle in degrees
	 * @param perspectiveDistortion amount of the perspective distortion 
	 */
	public void perspectiveBilinear(RasterImage src, RasterImage dst, double angle, double perspectiveDistortion) {
 		   		    	 
		// TODO: implement the geometric transformation using bilinear interpolation
		double sin = Math.sin(Math.toRadians(angle));
		double cos = Math.cos(Math.toRadians(angle));

		double s = perspectiveDistortion;

		for(int yDst = 0;yDst<dst.height;yDst++){
			for(int xDst = 0;xDst<dst.width;xDst++){

				int width = dst.width;
				int height = dst.height;

				int xDstC = xDst - (dst.width / 2);
				int yDstC = yDst - (dst.height / 2);

				double ySrcC = (yDstC / (cos - (yDstC * s * sin)));
				double xSrcC = (xDstC * (s * sin * ySrcC + 1));


				 //nur floor
				double xSrc = (xSrcC + (src.width / 2));
				//int xSrcCeil = (int) Math.ceil(xSrcC + (src.width / 2));
				int xSrcFloor = (int) Math.floor(xSrcC + (src.width / 2));
				//int xSrcRound = (int) Math.round(xSrcC + (src.width / 2));


				double ySrc = (ySrcC + (src.height / 2));
				//int ySrcCeil = (int) Math.ceil(ySrcC + (src.height / 2));
				int ySrcFloor = (int) Math.floor(ySrcC + (src.height / 2));
				//int ySrcRound = (int) Math.round(ySrcC + (src.height / 2));

				//double v1 = ySrcCeil - ySrc;
				//double v2 = ySrc - ySrcFloor;
				//double h1 = xSrcCeil - xSrc;
				//double h2 = xSrc - xSrcFloor;

				double v = xSrc - xSrcFloor;
				double h = ySrc - ySrcFloor;

				//v = Math.min(v1, v2);
				//h = Math.min(h1, h2);

				int rgbA = 0;
				int rgbB = 0;
				int rgbC = 0;
				int rgbD = 0;
				if(ySrcFloor < 0 && xSrcFloor<0){
					rgbA = 0xFFFFFFFF;
					rgbB = src.argb[pos(xSrcFloor,ySrcFloor+1,src.width)];
					rgbC = src.argb[pos(xSrcFloor+1,ySrcFloor,src.width)];
					rgbD = src.argb[pos(xSrcFloor+1,ySrcFloor+1,src.width)];
				}
				if(ySrcFloor >= src.height-1 || xSrcFloor >= src.width-1 || ySrcFloor<0 || xSrcFloor<0){
					rgbA = 0xFFFFFFFF;
					rgbB = 0xFFFFFFFF;
					rgbC = 0xFFFFFFFF;
					rgbD = 0xFFFFFFFF;
				}else{
					rgbA = src.argb[pos(xSrcFloor,ySrcFloor,src.width)];
					rgbB = src.argb[pos(xSrcFloor,ySrcFloor+1,src.width)];
					rgbC = src.argb[pos(xSrcFloor+1,ySrcFloor,src.width)];
					rgbD = src.argb[pos(xSrcFloor+1,ySrcFloor+1,src.width)];
				}

				/*if(ySrcRound <= src.height-1 && xSrcRound <= src.width-1 && ySrcRound>0 && xSrcRound>0){
					rgbA = src.argb[pos(xSrcRound,ySrcRound,src.width)];
				}
				if(xSrcRound >= src.width-1){
					rgbB = 0xFFFFFFFF;
				}else{
					rgbB = src.argb[pos(xSrcRound,ySrcRound+1,src.width)];
				}
				if(ySrcRound >= src.height-1){
					rgbC = 0xFFFFFFFF;
				}else{
					rgbC = src.argb[pos(xSrcRound+1,ySrcRound,src.width)];
				}
				if(xSrcRound >= src.width-1 && ySrcRound >= src.height-1){
					rgbD = 0xFFFFFFFF;
				}else if(xSrcRound >= src.width-1 && ySrcRound != src.height-1){
					rgbD = src.argb[pos(xSrcRound,ySrcRound+1,src.width)];
				}else if(xSrcRound != src.width-1 && ySrcRound >= src.height-1){
					rgbD = src.argb[pos(xSrcRound+1,ySrcRound,src.width)];
				}else{
					rgbD = src.argb[pos(xSrcRound+1,ySrcRound+1,src.width)];
				}*/

				int rA = (rgbA >> 16) & 0xff;
				int gA = (rgbA >> 8) & 0xff;
				int bA = rgbA & 0xff;
				int rB = (rgbB >> 16) & 0xff;
				int gB = (rgbB >> 8) & 0xff;
				int bB = rgbB & 0xff;
				int rC = (rgbC >> 16) & 0xff;
				int gC = (rgbC >> 8) & 0xff;
				int bC = rgbC & 0xff;
				int rD = (rgbD >> 16) & 0xff;
				int gD = (rgbD >> 8) & 0xff;
				int bD = rgbD & 0xff;

				// Formel von den Folien
				int rNew = (int) Math.round(rA * (1 - h) * (1 - v) + rB * h * (1 - v) + rC * (1 - h) * v + rD * h * v);
				int gNew = (int) Math.round(gA * (1 - h) * (1 - v) + gB * h * (1 - v) + gC * (1 - h) * v + gD * h * v);
				int bNew = (int) Math.round(bA * (1 - h) * (1 - v) + bB * h * (1 - v) + bC * (1 - h) * v + bD * h * v);


				int posDst = yDst * dst.width + xDst;
				dst.argb[posDst] = (0xff << 24) | (rNew << 16) | (gNew << 8) | (bNew);
			}
		}
			}


	}
		// NOTE: angle contains the angle in degrees, whereas Math trigonometric functions need the angle in radiant
 		   		    	 



