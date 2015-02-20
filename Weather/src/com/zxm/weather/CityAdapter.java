package com.zxm.weather;

import java.util.List;

import com.zxm.weather.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CityAdapter extends BaseAdapter{
	
	private Context context;
	private List<String> list;
	private LayoutInflater inflater;
	
	
	public CityAdapter(Context context, List<String> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		return null;
	}

}
