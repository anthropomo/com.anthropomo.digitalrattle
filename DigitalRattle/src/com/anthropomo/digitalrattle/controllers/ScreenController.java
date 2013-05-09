package com.anthropomo.digitalrattle.controllers;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.anthropomo.digitalrattle.io.CommandFactory;
import com.anthropomo.digitalrattle.io.IEventPublisher;
import com.anthropomo.digitalrattle.io.ScreenEvent;

public class ScreenController {

	private ScreenEvent screen;
	private IEventPublisher eventPublisher;
	
	public ScreenController (Context context, CommandFactory factory, IEventPublisher eventPublisher){
		screen = factory.getScreenEvent();
		this.eventPublisher = eventPublisher;
		update(context);
	}

	public void update(Context context){
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics ();
		display.getMetrics(metrics);
		
		int pixHeight = metrics.heightPixels;
        int pixWidth = metrics.widthPixels;
		float density  = context.getResources().getDisplayMetrics().density;
		float dpHeight = pixHeight / density;
		float dpWidth  = pixWidth / density;
		screen.setScreenWidth(pixWidth);
		screen.setScreenHeight(pixHeight);
		screen.setScreenWidthDp(dpWidth);
		screen.setScreenHeightDp(dpHeight);
		eventPublisher.receiveCommand(screen);
	}
}