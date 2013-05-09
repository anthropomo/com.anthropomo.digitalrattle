package com.anthropomo.digitalrattle.io;

import android.content.Context;

public abstract class LifeCycleAdapter implements ILifeCycleListener, IEventSubscriber {

	protected Context context;
	
	public LifeCycleAdapter(IEventPublisher eventPublisher){
		eventPublisher.registerSubscriber(this);
	}
	
	@Override
	public void receiveContext(Context context) {
		this.context = context;
	}
	
	@Override
	public void onCreate() { }

	@Override
	public void onResume() { }

	@Override
	public void onPause() { }

	@Override
	public void onDestroy() { }
	
	@Override
	public void onEvent(ICommand e) {
		e.execute(this);
	}

}
