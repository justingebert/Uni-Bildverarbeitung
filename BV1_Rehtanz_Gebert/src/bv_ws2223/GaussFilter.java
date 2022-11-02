// BV Ue1 WS2022/23 Vorgabe
//
// Copyright (C) 2022 by Klaus Jung
// All rights reserved.
// Date: 2022-09-20
 		   		    	 

package bv_ws2223;

public class GaussFilter {
 		   		    	 
	private double[][] kernel;
 		   		    	 
	public double[][] getKernel() {
		return kernel;
	}

	public void apply(RasterImage src, RasterImage dst, int kernelSize, double sigma) {

		// TODO: Implement a Gauss filter of size "kernelSize" x "kernelSize" with given "sigma"

		// Step 1: Allocate appropriate memory for the field variable "kernel" representing a 2D array.
		kernel = new double[kernelSize][kernelSize];

		double sum = 0;


		// Step 2: Fill in appropriate values into the "kernel" array.
		// Hint:
		// Use g(d) = e^(- d^2 / (2 * sigma^2)), where d is the distance of a coefficient's position to the hot spot.
		// Note that in this comment e^ denotes the exponential function and ^2 the square. In Java ^ is a different operator.

		if(kernelSize == 1){
			kernel[0][0] = 1;
			dst.argb = src.argb;
		}



		if(kernelSize>1){
			int middle = kernelSize/2;
			for(int x = -middle;x<=middle;x++){
				for(int y = -middle;y<=middle;y++){

					double d = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
					double g = Math.exp(-1*(d*d)/(2*sigma*sigma));

					int a  = x+middle;
					int b  = y+middle;

					kernel [a] [b] = g;
					sum = sum + g;
				}
			}
			for(int a = 0; a<kernelSize; a++){
				for(int b = 0; b<kernelSize;b++) {
					kernel[a][b] = kernel[a][b]/sum;
				}
			}

			for (int y = 0; y < src.height; y++) {
				for (int x = 0; x < src.width; x++) {

					int pos = y * src.width + x;

					double rFinal = 0;
					double gFinal = 0;
					double bFinal = 0;

					int posY = -1;

					//kernel schleifen
					for(int ky = y-middle; ky <= y+middle;ky++) {
						int ky1 = ky;
						if(ky1 < 0){ky1 = 0;}
						if (ky1 >= src.height - 1) {ky1 = src.height-1;}
						posY++;
						int posX = -1;
						for (int kx = x-middle; kx <= x+middle; kx++) {
							int kx1 = kx;
							if(kx1 < 0){kx1 = 0;}
							if(kx1 >= src.width-1){kx1 = src.width-1;}
							posX++;

							int posNew = ky1*src.width +kx1;
							int rgbNEW = src.argb[posNew];

							int r = ((rgbNEW >> 16) & 0xff);
							int g = ((rgbNEW >>  8) & 0xff);
							int b =  (rgbNEW        & 0xff);

							//int posY = ky - (y-middle);
							//int posX = kx - (x-middle);

							rFinal = rFinal + (r * kernel[posY][posX]);
							gFinal = gFinal + (g * kernel[posY][posX]);
							bFinal = bFinal + (b * kernel[posY][posX]);

							int rn = (int)rFinal;
							int gn = (int)gFinal;
							int bn = (int)bFinal;

							dst.argb[pos] = (0xFF<<24) | (rn<<16) | (gn << 8) | bn;
						}
					}
				}
			}

		}
		// Step 3: Normalize the "kernel" such that the sum of all its values is one.


		// Step 4: Apply the filter given by "kernel" to the source image "src". The result goes to image "dst".
		//bildschleifen

		// Use "constant continuation" for boundary processing.

	}


}
 		   		    	 




