package com.anthropomo.digitalrattle.io;


public class GravityEvent implements ICommand {
	private float x, y, z;
	
	public interface IGravityConsumer extends IEventSubscriber {
		public void onGravityEvent(float x, float y, float z);
	}
	
	public GravityEvent(){
	}
	
	@Override
	public void execute(IEventSubscriber e){
		if(e instanceof IGravityConsumer){
			((IGravityConsumer)e).onGravityEvent(x, y, z);
		}
	}

	public void setGravity(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
}
