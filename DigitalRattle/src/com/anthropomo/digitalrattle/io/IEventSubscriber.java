package com.anthropomo.digitalrattle.io;


public interface IEventSubscriber {
	public void onEvent(ICommand e);
}
