package com.anthropomo.digitalrattle.controllers;

import android.graphics.Color;
import android.view.View;

import com.anthropomo.digitalrattle.io.GravityEvent.IGravityConsumer;
import com.anthropomo.digitalrattle.io.ICommand;
import com.anthropomo.digitalrattle.io.IEventPublisher;

public class ColorController implements IGravityConsumer {

	private View colorView;
	
	public ColorController(View view, IEventPublisher eventPublisher){
		colorView = view;
		eventPublisher.registerSubscriber(this);
	}
	
	@Override
	public void onGravityEvent(float x, float y, float z) {
		float colorArray[] = { x, y, z };
    	colorArray = getHSV(colorArray, 10);
    	colorView.setBackgroundColor(Color.HSVToColor(150, colorArray));		
	}
	
	@Override
	public void onEvent(ICommand e) {
		e.execute(this);
	}

    static private float[] getHSV(float[] values, float halfDivisor){
    	for(int i = 0; i < values.length; i++){
    		values[i] = (float)((values[i]+halfDivisor)/(halfDivisor*2));
    		if(values[i]>1){
    			values[i] = 1;
    		}
    		else if(values[i]<0){
    			values[i] = 0;
    		}
    	}
    	values[0] *= 360;
    	return values;
    }

	
}
