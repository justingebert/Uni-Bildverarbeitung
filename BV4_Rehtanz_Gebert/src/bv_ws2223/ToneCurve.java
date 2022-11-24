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
		
		// TODO: Fill the grayTable[] array to map gay input values to gray output values.
		// It will be used as follows: grayOut = grayTable[grayIn].
		//
		// Use minInput, maxInput, minOutput, and maxOutput settings.

	}
	
	public void applyTo(RasterImage image) {
		
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

		// Remark: This is some dummy code to give you an idea for graphics drawing using paths		
		gc.beginPath();
		gc.moveTo(64 + shift, 128 + shift);
		gc.lineTo(128 + shift, 192 + shift);
		gc.lineTo(192 + shift, 64 + shift);
		gc.stroke();
	}

 		   		    	 
}
 		   		    	 




