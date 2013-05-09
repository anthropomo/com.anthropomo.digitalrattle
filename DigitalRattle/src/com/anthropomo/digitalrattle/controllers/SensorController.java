package com.anthropomo.digitalrattle.controllers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.anthropomo.digitalrattle.io.CommandFactory;
import com.anthropomo.digitalrattle.io.GravityEvent;
import com.anthropomo.digitalrattle.io.IEventPublisher;
import com.anthropomo.digitalrattle.io.LifeCycleAdapter;

public class SensorController implements SensorEventListener  {
	private SensorManager sensorMan;
	private Sensor accelerometer;
	private GravityEvent gravityEvent;
	private IEventPublisher eventPublisher;

	public SensorController(Context context, IEventPublisher e, CommandFactory factory){
		gravityEvent = factory.getGravityEvent();
        sensorMan = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        eventPublisher = e;
        new LifeCycleAdapter(e){
			@Override
			public void onResume() {
				resume();
			}

			@Override
			public void onPause() {
				pause();
			}
        };
	}

	private void resume() {
		sensorMan.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
	}
	
	private void pause() {
        sensorMan.unregisterListener(this);
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) { }

	@Override
	public void onSensorChanged(SensorEvent event) {
    	if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
    		float x = event.values[0];
    		float y = event.values[1];
    		float z = event.values[2];
        	gravityEvent.setGravity(x, y, z);
        	eventPublisher.receiveCommand(gravityEvent);
    	}
	}

}