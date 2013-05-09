package com.anthropomo.digitalrattle.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.anthropomo.digitalrattle.R;

public class SettingsActivity extends PreferenceActivity {
	SharedPreferences sp;
	
	@SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
	
	@Override
	public void onPause(){
		super.onPause();
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean("edited", true);
		editor.commit();
	}
    
}
