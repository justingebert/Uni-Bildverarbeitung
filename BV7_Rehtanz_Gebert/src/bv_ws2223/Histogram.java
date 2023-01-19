// BV Ue4 WS2022/23 Vorgabe
//
// Copyright (C) 2022 by Klaus Jung
// All rights reserved.
// Date: 2022-09-20
 		   		    	 

package bv_ws2223;

import javafx.scene.canvas.GraphicsContext;

import java.util.Arrays;

public class Histogram {
 		   		    	 
	private static final int grayLevels = 256;
	
    private GraphicsContext gc;
    private int maxHeight;

	private int histoMaxValue;
    
    private int[] histogram = new int[grayLevels];
 		   		    	 
    public Histogram() {
	}
    
	public Histogram(GraphicsContext gc, int maxHeight) {
		this.gc = gc;
		this.maxHeight = maxHeight;
	}
	
	public int[] getValues() {
		return histogram;
	}

	public void setImageRegion(RasterImage image, int regionStartX, int regionStartY, int regionWidth, int regionHeight) {
			histogram = new int[grayLevels];
			histoMaxValue = 0;
			//arrayfill
		for(int y = regionStartY; y<regionStartY+regionHeight;y++){
				for(int x = regionStartX; x<regionStartX+regionWidth;x++){
					int pos = y*image.width+x;
					int argb = image.argb[pos];

					int r =(argb >> 16) & 0xff;
					histogram[r] += 1;
					if(histogram[r]>histoMaxValue){
						histoMaxValue = histogram[r];
					}
				}
			}
		// TODO: calculate histogram[] out of the gray values found the given image region

	}

	public Integer getMinimum() {
		// Will be used in Exercise 5.
		for(int i = 0;i<histogram.length;i++){
			if(histogram[i] != 0) return i;
		}
		return 0;
	}
 		   		    	 
	public Integer getMaximum() {
		// Will be used in Exercise 5.
		for(int i = histogram.length;i<=0;i--){
			if(histogram[i] != 0) return i;
		}
		return 255;
	}
 		   		    	 
	public Double getMean() {
		// Will be used in Exercise 5.
		double mean = 0;
		int count = 0;
		for(int i = 0;i<histogram.length;i++){
			if(histogram[i] != 0){
				count += histogram[i];
			}
		}
		for(int i = 0;i<histogram.length;i++){
			if(histogram[i] != 0) {
				mean += i*histogram[i];
			}
		}
		return mean/(double)count;
	}
 		   		    	 
	public Integer getMedian() {
		// Will be used in Exercise 5.
		int median;
		int [] sorted = new int[histogram.length];
		System.arraycopy(histogram,0,sorted,0,histogram.length);
		Arrays.sort(sorted);
		int mid = histogram.length/2;
		median = sorted[mid];
		/*for(int i = 0;i<histogram.length;i++){
			median += i*histogram[i];
		}*/

		return median;
	}
 		   		    	 
	public Double getVariance() {
		// Will be used in Exercise 5.
		double variance = 0;
		int count = 0;
		double mean = this.getMean();
		for(int i = 0;i<histogram.length;i++){
			if(histogram[i] != 0){
				count += histogram[i];
			}
		}
		for(int i = 0;i<histogram.length;i++){
			if(histogram[i] != 0){
				double cur = Math.pow((i-mean),2)*histogram[i]/count;
				variance += cur;
			}
		}
		return variance;
	}
 		   		    	 
	public Double getEntropy() {
		// Will be used in Exercise 5.\
		double entropie = 0;
		int count = 0;
		for(int i = 0;i<histogram.length;i++){
			if(histogram[i] != 0){
				count += histogram[i];
			}
		}
		for(int i = 0;i<histogram.length;i++){
			if(histogram[i] != 0){
				double p = (double)histogram[i]/(double)count;
				entropie += p*(Math.log(p) / Math.log(2));
			}
		}
		return -entropie;

	}
	
}
 		   		    	 






