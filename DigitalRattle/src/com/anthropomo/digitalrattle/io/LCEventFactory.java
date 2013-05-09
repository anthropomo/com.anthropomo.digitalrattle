package com.anthropomo.digitalrattle.io;

public class LCEventFactory {
	LifeCycleEvent LCE;
	
	public LifeCycleEvent create(){
		return getter(LifeCycle.CREATE);
	}
	
	public LifeCycleEvent resume(){
		return getter(LifeCycle.RESUME);
	}
	
	public LifeCycleEvent pause(){
		return getter(LifeCycle.PAUSE);
	}
	
	public LifeCycleEvent destroy(){
		return getter(LifeCycle.PAUSE);
	}
	
	private LifeCycleEvent getter(LifeCycle event){
		if(LCE == null){
			LCE = new LifeCycleEvent(event){};
		}
		else{
			LCE.setLifeCycle(event);
		}
		return LCE;
	}
}
