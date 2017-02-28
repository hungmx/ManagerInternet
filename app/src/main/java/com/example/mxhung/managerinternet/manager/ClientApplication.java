package com.example.mxhung.managerinternet.manager;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by MXHung on 6/14/2016.
 */
public class ClientApplication extends Application{
	@Override
	public void onCreate() {
		super.onCreate();
		DBManager.init(this);
	}
}
