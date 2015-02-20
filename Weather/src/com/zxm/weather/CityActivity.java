package com.zxm.weather;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class CityActivity extends Activity {
	ListView cityListView;
	CityAdapter cityAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_city);
//		cityAdapter=new CityAdapter();
		cityListView.setAdapter(cityAdapter);
	}
}
