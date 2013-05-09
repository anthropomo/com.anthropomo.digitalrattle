package com.anthropomo.digitalrattle.controllers;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Vibrator;

import com.anthropomo.digitalrattle.io.GravityEvent.IGravityConsumer;
import com.anthropomo.digitalrattle.io.ICommand;
import com.anthropomo.digitalrattle.io.IEventPublisher;
import com.anthropomo.digitalrattle.io.PreferenceEvent.IPreferenceConsumer;

public class VibeController implements IGravityConsumer, IPreferenceConsumer {
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity
	private Vibrator vibe;
	private boolean vibrate = true;

	public VibeController(Context context, IEventPublisher eventPublisher){
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        eventPublisher.registerSubscriber(this);
	}

	@Override
	public void onGravityEvent(float x, float y, float z) {
		if(!vibrate) return;
	    mAccelLast = mAccelCurrent;
	    mAccelCurrent = (float) Math.sqrt(x*x + y*y + z*z);
	    float delta = mAccelCurrent - mAccelLast;
	    mAccel = mAccel * 0.9f + delta; // perform low-cut filter
	    if(mAccel > 5){
	    	vibe.vibrate(50);
	    }
	    else if(mAccel > 3){
	    	vibe.vibrate(25);
	    }
	}

	@Override
	public void onEvent(ICommand e) {
		e.execute(this);
	}

	@Override
	public void onPreferenceEvent(boolean vibrate, int noteRange,
			boolean majorKey, int keySignature, int startNote,
			boolean newSession) {
		this.vibrate = vibrate;
		
	}


}
