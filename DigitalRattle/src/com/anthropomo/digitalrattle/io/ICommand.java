package com.anthropomo.digitalrattle.io;

public interface ICommand {
	public void execute(IEventSubscriber e);
}
