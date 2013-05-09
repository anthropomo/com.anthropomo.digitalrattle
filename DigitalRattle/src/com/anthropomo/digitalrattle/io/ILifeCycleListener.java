package com.anthropomo.digitalrattle.io;

import android.content.Context;

public interface ILifeCycleListener {
	
	public void receiveContext(Context context);
	public void onCreate();
	public void onResume();
	public void onPause();
	public void onDestroy();
}
