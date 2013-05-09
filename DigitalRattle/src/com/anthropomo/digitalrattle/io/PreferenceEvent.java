package com.anthropomo.digitalrattle.io;


public class PreferenceEvent implements ICommand {
	private boolean vibrate, majorKey, newSession;
	private int noteRange, keySignature, startNote;
	
	public PreferenceEvent(){ }
	
	public interface IPreferenceConsumer extends IEventSubscriber{
		public void onPreferenceEvent(boolean vibrate, int noteRange, boolean majorKey, int keySignature, int startNote, boolean newSession);
	}
	
	public void setPreferences(boolean vibrate, int noteRange, boolean majorKey, int keySignature, int startNote, boolean newSession){
		this.vibrate = vibrate;
		this.noteRange = noteRange;
		this.majorKey = majorKey;
		this.keySignature = keySignature;
		this.startNote = startNote;
		this.newSession = newSession;
	}

	@Override
	public void execute(IEventSubscriber e) {
		if(e instanceof IPreferenceConsumer){
			((IPreferenceConsumer) e).onPreferenceEvent(vibrate, noteRange, majorKey, keySignature, startNote, newSession);
		}
	}

}
