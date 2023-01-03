// BV Ue4 WS2022/23 Vorgabe
//
// Copyright (C) 2022 by Klaus Jung
// All rights reserved.
// Date: 2022-09-20
 		   		    	 

package bv_ws2223;

import java.io.File;
import java.util.Arrays;

import bv_ws2223.ImageAnalysisAppController.Visualization;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class RasterImage {
 		   		    	 
	private static final int gray  = 0xffa0a0a0;

	public int[] argb;	// pixels represented as ARGB values in scanline order
	public int width;	// image width in pixels
	public int height;	// image height in pixels
	
	public RasterImage(int width, int height) {
		// creates an empty RasterImage of given size
		this(width, height, gray);
	}

	public RasterImage(int width, int height, int argbColor) {
		// creates an empty RasterImage of given size and color
		this.width = width;
		this.height = height;
		argb = new int[width * height];
		Arrays.fill(argb, argbColor);
	}
	
	public RasterImage(RasterImage image) {
		// copy constructor
		this.width = image.width;
		this.height = image.height;
		argb = image.argb.clone();
	}
 		   		    	 
	public RasterImage(File file) {
		// creates a RasterImage by reading the given file
		Image image = null;
		if(file != null && file.exists()) {
			image = new Image(file.toURI().toString());
		}
		if(image != null && image.getPixelReader() != null) {
			width = (int)image.getWidth();
			height = (int)image.getHeight();
			argb = new int[width * height];
			image.getPixelReader().getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
		} else {
			// file reading failed: create an empty RasterImage
			this.width = 256;
			this.height = 256;
			argb = new int[width * height];
			Arrays.fill(argb, gray);
		}
	}
	
	public RasterImage(ImageView imageView) {
		// creates a RasterImage from that what is shown in the given ImageView
		Image image = imageView.getImage();
		width = (int)image.getWidth();
		height = (int)image.getHeight();
		argb = new int[width * height];
		image.getPixelReader().getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
	}
	
	public Image getImage() {
		// returns a JavaFX image
		if(argb != null) {
			WritableImage wr = new WritableImage(width, height);
			PixelWriter pw = wr.getPixelWriter();
			pw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
			return wr;
		}
		return null;
	}
 		   		    	 
	public void setToView(ImageView imageView) {
		// sets the current argb pixels to be shown in the given ImageView
		Image image = getImage();
		if(image != null) {
			imageView.setImage(image);
		}
	}
	
	
	// image point operations to be added here

	public void convertToGray() {
		// TODO: convert the image to grayscale
		for(int x = 0; x<width;x++){
			for(int y = 0;y<height;y++){
				int pos = y * width +x;
				//argb[pos] = 0xffff0000;

				int rgb = argb[pos];

				int r =(rgb >> 16) & 0xff;
				int g =(rgb >> 8) & 0xff;
				int b =(rgb >> 0) & 0xff;

				int avg = (r+g+b)/3;

				r = avg;
				g = avg;
				b = avg;

				argb[pos] = (0xFF<<24) | (r<<16) | (g<<8) | b;

			}
		}
	}
 		   		    	 
	public RasterImage getOverlayImage(int regionSize, Visualization visualization, double threshold) {
		
		// Will be used in Exercise 5. Nothing to do in Exercise 4.
		
		// Create an overlay image that contains half transparent green pixels where a
		// statistical property locally exceeds the given threshold. 
		// Use a sliding window of size regionSize x regionSize.
		// Use "switch(visualization)" to determine, what statistical property should be used
		RasterImage overlay = new RasterImage(width,height, 0x4000ff00);
		Histogram histo = new Histogram();

		if(visualization == Visualization.ENTROPY){
			for(int y = 0;y<height;y++){
				for(int x = 0;x<width;x++){
					int xN = x+regionSize;
					int yN = y+regionSize;
					if(xN > width-1) xN = width-1;
					else if(xN<0) xN = 0;
					if(yN>height-1) yN = height-1;
					else if(yN < 0) yN = 0;
					int regionX = xN-x;
					int regionY = yN-y;

					int pos = y*width + x;
					histo.setImageRegion(this,x,y,regionX,regionY);
					double entro = histo.getEntropy();
					if(entro> threshold) overlay.argb[pos] = 0x4000ff00;
					else overlay.argb[pos] = 0x00000000;

				}
			}
		}else if(visualization == Visualization.VARIANCE){
			for(int y = 0;y<height;y++){
				for(int x = 0;x<width;x++){
					int xN = x+regionSize;
					int yN = y+regionSize;
					if(xN > width-1) xN = width-1;
					else if(xN<0) xN = 0;
					if(yN>height-1) yN = height-1;
					else if(yN < 0) yN = 0;
					int regionX = xN-x;
					int regionY = yN-y;

					int pos = y*width + x;
					histo.setImageRegion(this,x,y,regionX,regionY);
					double variance = histo.getVariance();
					if(variance> threshold) overlay.argb[pos] = 0x4000ff00;
					else overlay.argb[pos] = 0x00000000;

				}
			}
		}

		return overlay;
	}
 		   		    	 
}
 		   		    	 





