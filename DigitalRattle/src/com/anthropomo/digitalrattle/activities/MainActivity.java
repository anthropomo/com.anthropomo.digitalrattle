package com.anthropomo.digitalrattle.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.anthropomo.digitalrattle.R;
import com.anthropomo.digitalrattle.controllers.ColorController;
import com.anthropomo.digitalrattle.controllers.ConfigController;
import com.anthropomo.digitalrattle.controllers.KeyController;
import com.anthropomo.digitalrattle.controllers.PdController;
import com.anthropomo.digitalrattle.controllers.PreferenceController;
import com.anthropomo.digitalrattle.controllers.ScreenController;
import com.anthropomo.digitalrattle.controllers.SensorController;
import com.anthropomo.digitalrattle.controllers.VibeController;
import com.anthropomo.digitalrattle.io.CommandFactory;
import com.anthropomo.digitalrattle.io.EventPublisher;
import com.anthropomo.digitalrattle.io.IEventPublisher;
import com.anthropomo.digitalrattle.io.INoteListener;
import com.anthropomo.digitalrattle.io.LCEventFactory;
import com.anthropomo.digitalrattle.models.MusicNotation;
import com.anthropomo.digitalrattle.ui.MusicView;

public class MainActivity extends Activity {
	
	private IEventPublisher eventPublisher;
	private LCEventFactory LCEFactory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*
		 * Register subscribers, receives Events, notifies subscribers of events, distributes state
		 */
		eventPublisher = new EventPublisher();
		
		/*
		 * Produces non-lifecycle events
		 */
		CommandFactory factory = new CommandFactory();
		
		/*
		 * Gets screen dimensions in pix and dp
		 */
		new ScreenController(this, factory, eventPublisher);
		
		/*
		 * Picks cell size and number of notes based on dimensions
		 */
		new ConfigController(factory, eventPublisher);
		
		/*
		 * Gets shared preferences
		 */
		new PreferenceController(this, factory, eventPublisher);
		
		/*
		 * Produces correct note based on key signature, scale, etc.
		 */
		MusicNotation music = new MusicNotation();
		
		/*
		 * Updates MusicNotation of preference change
		 */
		new KeyController(eventPublisher, music);
		
		/*
		 * Initiates/closes pd, communicates w/ pd
		 */
		INoteListener pdController = new PdController(this, eventPublisher, music);
		
		/*
		 * Gets x, y, z, accelerometer data
		 */
		new SensorController(this, eventPublisher, factory);
		
		/*
		 * Produces vibration based on "shake" from accelerometer
		 */
		new VibeController(this, eventPublisher);
		
		
		FrameLayout fl = new FrameLayout(this);        
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

		/*
		 * Draws pulsing radial gradiant and triggers note on touch 
		 */
		MusicView musicView = new MusicView(this, 300, eventPublisher, pdController); 
		musicView.setBackgroundColor(0x00000000);
		musicView.setLayoutParams(params);
		musicView.setOnTouchListener(musicView);
		fl.addView(musicView);

		/*
		 * Changes color based on accelerometer
		 */
		View colorView = new View(this);
		new ColorController(colorView, eventPublisher);
		colorView.setBackgroundColor(0x00000000);
		colorView.setLayoutParams(params);
		fl.addView(colorView);		

		setContentView(fl);
        
        /*
         * produces lifecycle events
         */
        LCEFactory = new LCEventFactory();
        
		eventPublisher.receiveCommand(LCEFactory.create());
	}

	@Override
	protected void onResume() {
		super.onResume();
		eventPublisher.receiveCommand(LCEFactory.resume());
	}

	@Override
	protected void onPause() {
		super.onPause();
		eventPublisher.receiveCommand(LCEFactory.pause());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		eventPublisher.receiveCommand(LCEFactory.destroy());
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_digital_rattle, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){ 	
    	//check selected menu item
    	if(item.getItemId() == R.id.menu_settings){
    		startActivity(new Intent(this, SettingsActivity.class));
    		return true;
    	}
    	return false;
    }

}
