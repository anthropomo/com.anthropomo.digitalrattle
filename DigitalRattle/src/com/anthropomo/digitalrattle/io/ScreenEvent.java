package com.anthropomo.digitalrattle.io;


public class ScreenEvent implements ICommand {

	private float screenHeight, screenWidth;
	private float screenHeightDp, screenWidthDp;
	
	public void setScreenHeight(float screenHeight) { this.screenHeight = screenHeight; }
	public void setScreenWidth(float screenWidth) { this.screenWidth = screenWidth; }
	public void setScreenHeightDp(float screenHeightDp) { this.screenHeightDp = screenHeightDp; }
	public void setScreenWidthDp(float screenWidthDp) { this.screenWidthDp = screenWidthDp; }
	
	public ScreenEvent(){ }
	
	public interface IScreenConsumer extends IEventSubscriber {
		public void onScreenEvent(float screenHeight, float screenWidth, float screenHeightDp, float screenWidthDp);
	}

	@Override
	public void execute(IEventSubscriber e) {
		if(e instanceof IScreenConsumer){
			((IScreenConsumer) e).onScreenEvent(screenHeight, screenWidth, screenHeightDp, screenWidthDp);
		}
		
	}

}
