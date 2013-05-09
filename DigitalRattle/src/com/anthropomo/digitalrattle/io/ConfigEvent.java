package com.anthropomo.digitalrattle.io;


public class ConfigEvent implements ICommand {

	private int baseNote, numNotes;
	private int noteRows, noteCols;
	
	public ConfigEvent(){
	}
	
	public interface IConfigConsumer extends IEventSubscriber {
		public void onConfigEvent(int baseNote, int numNotes, int noteRows, int noteCols);
	}
	
	public void setConfig(int baseNote, int numNotes, int noteRows, int noteCols){
		this.baseNote = baseNote;
		this.numNotes = numNotes;
		this.noteRows = noteRows;
		this.noteCols = noteCols;
	}

	@Override
	public void execute(IEventSubscriber e) {
		if(e instanceof IConfigConsumer){
			((IConfigConsumer) e).onConfigEvent(baseNote, numNotes, noteRows, noteCols);
		}
		
	}

}
