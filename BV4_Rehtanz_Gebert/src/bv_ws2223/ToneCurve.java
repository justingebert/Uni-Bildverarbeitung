// BV Ue4 WS2022/23 Vorgabe
//
// Copyright (C) 2022 by Klaus Jung
// All rights reserved.
// Date: 2022-09-20
 		   		    	 

package bv_ws2223;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ToneCurve {
 		   		    	 
	private static final int grayLevels = 256;
	
    private GraphicsContext gc;
    
    private int[] grayTable = new int[grayLevels];
 		   		    	 
	public int[] getGrayTable() {
		return grayTable;
	}

	public ToneCurve(GraphicsContext gc) {
		this.gc = gc;
	}
	
	public void updateTable(int minInput, int maxInput, int minOutput, int maxOutput) {
		
		// TODO: Fill the grayTable[] array to map gray input values to gray output values.
		for(int i = 0; i<minInput; i++){
			grayTable[i] = 255-minOutput;
		}
		for(int j = maxInput; j <= 255; j++){
			grayTable[j] = 255-maxOutput;
		}
		for(int i = minInput; i<=maxInput;i++){
			grayTable[i] = (int) (255-(((float)(maxOutput-minOutput)/(maxInput-minInput))*(i-minInput)+minOutput));
		}
		// It will be used as follows: grayOut = grayTable[grayIn].
		//
		// Use minInput, maxInput, minOutput, and maxOutput settings.

	}
	
	public void applyTo(RasterImage image) {
		for(int y = 0;y<image.height;y++){
			for(int x = 0; x< image.width;x++){
				int pos = y*image.width+x;
				int argb = image.argb[pos];

				int r =(argb >> 16) & 0xff;

				r = 255-grayTable[r];

				image.argb[pos] = (0xFF<<24) | (r<<16) | (r<<8) | r;
			}
		}
		// TODO: apply the gray value mapping to the given image

	}
	
	public void draw(Color lineColor) {
		if(gc == null) return;
		gc.clearRect(0, 0, grayLevels, grayLevels);
		gc.setStroke(lineColor);
		gc.setLineWidth(3);
		
		// TODO: draw the tone curve into the gc graphic context
		// Note that we need to add 0.5 to all coordinates to align points to pixel centers
		double shift = 0.5;

		gc.beginPath();
		for(int i = 0; i< grayTable.length;i++){
			gc.lineTo(i+shift,grayTable[i]+shift);
		}
		gc.stroke();



		// Remark: This is some dummy code to give you an idea for graphics drawing using paths		
		/*gc.beginPath();
		gc.moveTo(64 + shift, 128 + shift);
		gc.lineTo(128 + shift, 192 + shift);
		gc.lineTo(192 + shift, 64 + shift);
		gc.stroke();*/
	}

 		   		    	 
}
 		   		    	 




