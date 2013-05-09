package com.anthropomo.digitalrattle.io;

import java.util.List;

public interface IEventPublisher {

	public void registerSubscriber(IEventSubscriber subscriber);
	public void unregisterSubscriber(IEventSubscriber subscriber);
	public void sendEvent(ICommand e);
	public void receiveCommand(ICommand c);
	public List<ICommand> getEventRecord();
	
}
