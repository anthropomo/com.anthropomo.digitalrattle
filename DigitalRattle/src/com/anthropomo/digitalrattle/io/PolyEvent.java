package com.anthropomo.digitalrattle.io;

public class PolyEvent implements ICommand {
	private boolean polyphonic;
	
	public interface IPolyConsumer extends IEventSubscriber{
		public void onPolyEvent(boolean polyphonic);
	}
	
	public void setPolyphonic(boolean p){
		this.polyphonic = p;
	}

	@Override
	public void execute(IEventSubscriber e) {
		if(e instanceof IPolyConsumer){
			((IPolyConsumer) e).onPolyEvent(polyphonic);
		}
	}

}
