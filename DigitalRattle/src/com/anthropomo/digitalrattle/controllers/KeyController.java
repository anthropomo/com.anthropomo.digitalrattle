package com.anthropomo.digitalrattle.controllers;

import com.anthropomo.digitalrattle.io.ConfigEvent.IConfigConsumer;
import com.anthropomo.digitalrattle.io.ICommand;
import com.anthropomo.digitalrattle.io.IEventPublisher;
import com.anthropomo.digitalrattle.io.PreferenceEvent.IPreferenceConsumer;
import com.anthropomo.digitalrattle.models.MusicNotation;

public class KeyController implements IConfigConsumer, IPreferenceConsumer {
	MusicNotation music;
	IEventPublisher eventPublisher;
	int baseNote = -1;
	int numNotes = -1;
	int keySignature = -1;
	int noteRange = -1;
	int startNote = -1;
	boolean majorKey;
	boolean newSession;
	boolean config = false;
	boolean pref = false;
	
	public KeyController (IEventPublisher eventPublisher, MusicNotation music){
		this.music = music;
		eventPublisher.registerSubscriber(this);
	}

	
	@Override
	public void onEvent(ICommand e) {
		e.execute(this);
	}
	
	private void updateMusic(){
		
	    int newStartNote = baseNote;
	    
	    switch(noteRange)
	    {
	    case 0:
	    	newStartNote -= 12;
	    	break;
	    case 1:
	    	break;
	    case 2:
	    	newStartNote += 12;
	    	break;
	    }

	    if(majorKey){
	    	newStartNote = newStartNote + startNote;
	    }
	    if(newSession){
	    	music.setNewMusic(majorKey, keySignature, newStartNote, numNotes);
	    }
	    else{
	    	music.setNotes(majorKey,newStartNote,keySignature);
	    }
	}

	@Override
	public void onConfigEvent(int baseNote, int numNotes, int noteRows, int noteCols) {
		this.baseNote = baseNote;
		this.numNotes = numNotes;
		config = true;
		if(pref){
			updateMusic();
		}
	}


	@Override
	public void onPreferenceEvent(boolean vibrate, int noteRange,
			boolean majorKey, int keySignature, int startNote,
			boolean newSession) {
		this.noteRange = noteRange;
		this.majorKey = majorKey;
		this.keySignature = keySignature;
		this.startNote = startNote;
		this.newSession = newSession;
		pref = true;
		if(config){
			updateMusic();
		}
	}
}
