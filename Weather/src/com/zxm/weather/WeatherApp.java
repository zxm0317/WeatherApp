package com.zxm.weather;

import com.thinkland.juheapi.common.CommonFun;

import android.app.Application;

public class WeatherApp extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		CommonFun.initialize(getApplicationContext());
	}

}
