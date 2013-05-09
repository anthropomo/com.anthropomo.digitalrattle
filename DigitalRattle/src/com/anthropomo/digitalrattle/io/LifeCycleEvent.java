package com.anthropomo.digitalrattle.io;


public class LifeCycleEvent implements ICommand {
	private LifeCycle lifeCycle;
	
	public LifeCycleEvent(LifeCycle l){
		this.lifeCycle = l;
	}
	
	public void setLifeCycle(LifeCycle l){
		this.lifeCycle = l;
	}

	@Override
	public void execute(IEventSubscriber e) {
		if(e instanceof ILifeCycleListener){
			ILifeCycleListener l = (ILifeCycleListener) e;
			switch(lifeCycle){
			case CREATE:
				l.onCreate();
				break;
			case RESUME:
				l.onResume();
				break;
			case PAUSE:
				l.onPause();
				break;
			case DESTROY:
				l.onDestroy();
				break;
			}
		}	
	}

}
