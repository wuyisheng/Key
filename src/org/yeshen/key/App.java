package org.yeshen.key;

import org.yeshen.key.common.Cache;
import org.yeshen.key.common.KeyerOptionManager;
import org.yeshen.key.common.Res;

import android.app.Application;
import android.content.res.Configuration;

public class App extends Application{
	
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		
		Cache.init(getApplicationContext());
		Res.init(getApplicationContext());
		KeyerOptionManager.init(getApplicationContext());
	}
	
	@Override
	public void onLowMemory(){
		super.onLowMemory();
	}
	
	@Override
	public void onTerminate(){
		super.onTerminate();
	}
}
