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

				int ySrcC = (int) (yDstC/(cos-(yDstC*s*sin)));
				int xSrcC = (int) (xDstC*(s*sin*ySrcC+1));

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
/*
		double oldW = (width - 1);
		double newW = (width_n - 1);
		double oldH = (height - 1);
		double newH = (height_n - 1);

		double ratioW = oldW / newW;
		double ratioH = oldH / newH;

		int rgbA = 0;
		int rgbB = 0;
		int rgbC = 0;
		int rgbD = 0;

		for (int y_n = 0; y_n < height_n; y_n++) {
			for (int x_n = 0; x_n < width_n; x_n++) {

				int pos_n = pos(x_n, y_n, width_n);
				int altX = (int) Math.round(ratioW * x_n);
				int altY = (int) Math.round(ratioH * y_n);

				double h = ratioW * x_n - altX;
				double v = ratioH * y_n - altY;

				// Farbwerte aus den richtigen Pixeln auslesen
				rgbA = pix[pos(altX, altY, width)];
				if (altX == width - 1) {
					rgbB = pix[pos(altX, altY, width)];
				}
				else {
					rgbB = pix[pos(altX + 1, altY, width)];
				}

				if (altY == height - 1) {
					rgbC = pix[pos(altX, altY, width)];
				}
				else {
					rgbC = pix[pos(altX, altY + 1, width)];
				}

				if (altX == width - 1 && altY == height - 1) {
					rgbD = pix[pos(altX, altY, width)];
				}
				else if (altX == width - 1 && altY != height - 1) {
					rgbD = pix[pos(altX, altY + 1, width)];
				}
				else if (altX != width - 1 && altY == height - 1) {
					rgbD = pix[pos(altX + 1, altY, width)];
				}
				else {
					rgbD = pix[pos(altX + 1, altY + 1, width)];
				}

				// RGB extrahieren
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

				// overrun correction
				if (rNew > 255) {
					rNew = 255;
				}
				else if (rNew < 0) {
					rNew = 0;
				}
				if (gNew > 255) {
					gNew = 255;
				}
				else if (gNew < 0) {
					gNew = 0;
				}
				if (bNew > 255) {
					bNew = 255;
				}
				else if (bNew < 0) {
					bNew = 0;
				}

				pix_n[pos_n] = (0xff << 24) | (rNew << 16) | (gNew << 8) | (bNew);
			}
		}*/
	}
		// NOTE: angle contains the angle in degrees, whereas Math trigonometric functions need the angle in radiant
		
 	}
 		   		    	 



