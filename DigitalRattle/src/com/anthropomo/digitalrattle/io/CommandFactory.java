package com.anthropomo.digitalrattle.io;


public class CommandFactory {
	private GravityEvent grav;
	private PreferenceEvent pref;
	private ConfigEvent config;
	private ScreenEvent screen;
	private PolyEvent poly;
	
	public CommandFactory(){
	}
	
	public GravityEvent getGravityEvent(){
		if(grav == null){
			grav = new GravityEvent();
		}
		return grav;
	}
	
	public PreferenceEvent getPreferenceEvent(){
		if(pref == null){
			pref = new PreferenceEvent();
		}
		return pref;
	}
	
	public ConfigEvent getConfigEvent(){
		if(config == null){
			config = new ConfigEvent();
		}
		return config;
	}
	
	public ScreenEvent getScreenEvent(){
		if(screen == null){
			screen = new ScreenEvent();
		}
		return screen;
	}
	
	public PolyEvent getPolyEvent(){
		return setPolyEvent(true);
	}
	
	public PolyEvent getMonoEvent(){
		return setPolyEvent(false);
	}
	
	private PolyEvent setPolyEvent(boolean polyphonic){
		if(poly == null){
			poly = new PolyEvent();
		}
		poly.setPolyphonic(polyphonic);
		return poly;
	}
	
}
