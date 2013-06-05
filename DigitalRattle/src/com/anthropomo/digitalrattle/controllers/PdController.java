package com.anthropomo.digitalrattle.controllers;

import java.io.File;
import java.io.IOException;

import org.puredata.android.io.AudioParameters;
import org.puredata.android.io.PdAudio;
import org.puredata.android.utils.PdUiDispatcher;
import org.puredata.core.PdBase;
import org.puredata.core.utils.IoUtils;

import android.app.Activity;

import com.anthropomo.digitalrattle.R;
import com.anthropomo.digitalrattle.io.ICommand;
import com.anthropomo.digitalrattle.io.IEventPublisher;
import com.anthropomo.digitalrattle.io.INoteListener;
import com.anthropomo.digitalrattle.io.LifeCycleAdapter;
import com.anthropomo.digitalrattle.io.PolyEvent.IPolyConsumer;
import com.anthropomo.digitalrattle.models.MusicNotation;

public class PdController implements INoteListener, IPolyConsumer {
	private PdUiDispatcher dispatcher;
	private Activity activity;
	private MusicNotation music;
	private String pdCommand = "polynote";
	
	public PdController(Activity activity, IEventPublisher eventPublisher, MusicNotation music){
		this.activity = activity;
		this.music = music;
		new LifeCycler(eventPublisher);
		eventPublisher.registerSubscriber(this);
	}
	
	/*
	 * Receives lifecycle events from activity and calls necessary Pd methods
	 */
	
	private class LifeCycler extends LifeCycleAdapter {

		public LifeCycler(IEventPublisher eventPublisher) {
			super(eventPublisher);
		}
		
		@Override
		public void onCreate() {
	        try{
	        	initPd();
	        	loadPatch();
	        } catch (IOException e) {
	        	activity.finish();
	        }
		}

		@Override
		public void onResume() {
	        PdAudio.startAudio(activity);
		}

		@Override
		public void onPause() {
	    	PdAudio.stopAudio();
		}

		@Override
		public void onDestroy() {
	    	PdAudio.release();
	    	PdBase.release();
		}
	}
	
	private void initPd() throws IOException {
	 	// sizeConfig audio glue
		int sampleRate = AudioParameters.suggestSampleRate();
		PdAudio.initAudio(sampleRate, 0, 2, 8, true);
	
		// create, install dispatcher
	 	dispatcher = new PdUiDispatcher();
	 	PdBase.setReceiver(dispatcher);
	}
	 
	private void loadPatch() throws IOException {
	 	File dir = activity.getFilesDir();
	 	IoUtils.extractZipResource(activity.getResources().openRawResource(R.raw.rattle), dir, true);
	 	File patchFile = new File(dir, "rattle.pd");
	 	PdBase.openPatch(patchFile.getAbsolutePath());
	}
	
	@Override
	public void onNotePlayed(int note) {
		PdBase.sendFloat(pdCommand, music.getNote(note));		
	}

	@Override
	public void onEvent(ICommand e) {
		e.execute(this);
	}

	@Override
	public void onPolyEvent(boolean polyphonic) {
		if(polyphonic)
			pdCommand = "polynote";
		else
			pdCommand = "mononote";		
	}
	
	

}
