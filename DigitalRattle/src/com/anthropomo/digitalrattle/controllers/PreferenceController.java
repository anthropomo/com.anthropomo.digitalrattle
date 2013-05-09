package com.anthropomo.digitalrattle.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.anthropomo.digitalrattle.io.CommandFactory;
import com.anthropomo.digitalrattle.io.ICommand;
import com.anthropomo.digitalrattle.io.IEventPublisher;
import com.anthropomo.digitalrattle.io.LifeCycleAdapter;
import com.anthropomo.digitalrattle.io.PreferenceEvent;

public class PreferenceController {
	// update some kind of vos object(s), then call update state on listeners, or look at that mvc again
	private SharedPreferences sharedPref;
	private Context context;
	
	private PreferenceEvent pref;
	private IEventPublisher eventPublisher;
	private ICommand poly;
	private CommandFactory factory;

	private boolean vibrate;
	private int noteRange;
	private boolean majorKey;
	private int keySignature;
	private int startNote;
	
	
	public PreferenceController(Context context, CommandFactory factory, IEventPublisher eventPublisher){
		this.context = context;
		this.eventPublisher = eventPublisher;
		this.factory = factory;
		pref = factory.getPreferenceEvent();
		new LifeCycleAdapter(eventPublisher){
			
			@Override
			public void onCreate(){
				setPreferences();
			}
			
			@Override
			public void onResume(){
				updatePreferences();
			}
		};
	}
	
	private void updateVibrate(){
		vibrate = sharedPref.getBoolean("vibrate", true);
	}
	
	private void updatePolyphonic(){
		if(sharedPref.getBoolean("polyphonic", true)){
			poly = factory.getPolyEvent();
		}
		else{
			poly = factory.getMonoEvent();
		}
		eventPublisher.receiveCommand(poly);
	}
	
	private void updateMusic(){
		noteRange = Integer.parseInt(sharedPref.getString("range", "1"));
        majorKey = sharedPref.getBoolean("major", true);
        keySignature = Integer.parseInt(sharedPref.getString("key_signature", "0"));
        startNote = Integer.parseInt(sharedPref.getString("start_note", "0"));
	}
	
	public void setPreferences(){
		sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		updateVibrate();
		updateMusic();
		updatePolyphonic();
		pref.setPreferences(vibrate, noteRange, majorKey, keySignature, startNote, true);
		eventPublisher.receiveCommand(pref);
	}
	
	public void updatePreferences(){
	    sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
	    if (sharedPref.getBoolean("edited", false)){
	        updateVibrate();
	        updateMusic();
	        updatePolyphonic();
	    	setEditedFalse();
			pref.setPreferences(vibrate, noteRange, majorKey, keySignature, startNote, false);
			eventPublisher.receiveCommand(pref);
	    }
	}
	
	private void setEditedFalse(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("edited", false);
        editor.commit();
	}
}
