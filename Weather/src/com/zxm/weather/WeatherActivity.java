package com.zxm.weather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkland.juheapi.common.JsonCallBack;
import com.thinkland.juheapi.data.weather.WeatherData;
import com.zxm.bean.FutureWeatherBean;
import com.zxm.bean.HoursWeatherBean;
import com.zxm.bean.WeatherBean;

public class WeatherActivity extends Activity {
	 private Context mContext;
	private List<HoursWeatherBean> list;
	private String chageCity = "北京";
	private TextView top_city,// 城市
			tv_release,// 发布时间
			tv_now_weather,// 天气
			tv_today_temp,// 温度
			tv_now_temp,// 当前温度
			tv_aqi,// 空气质量指数
			tv_quality,// 空气质量
			tv_next_three,// 3小时
			tv_next_six,// 6小时
			tv_next_nine,// 9小时
			tv_next_twelve,// 12小时
			tv_next_fifteen,// 15小时
			tv_next_three_temp,// 3小时温度
			tv_next_six_temp,// 6小时温度
			tv_next_nine_temp,// 9小时温度
			tv_next_twelve_temp,// 12小时温度
			tv_next_fifteen_temp,// 15小时温度
			tv_today_temp_a,// 今天温度a
			tv_today_temp_b,// 今天温度b
			tv_tommorrow,// 明天
			tv_tommorrow_temp_a,// 明天温度a
			tv_tommorrow_temp_b,// 明天温度b
			tv_thirdday,// 第三天
			tv_thirdday_temp_a,// 第三天温度a
			tv_thirdday_temp_b,// 第三天温度b
			tv_fourthday,// 第四天
			tv_fourthday_temp_a,// 第四天温度a
			tv_fourthday_temp_b,// 第四天温度b
			tv_humidity,// 湿度
			tv_wind, tv_uv_index,// 紫外线指数
			tv_dressing_index;// 穿衣指数

	private ImageView iv_now_weather,// 现在
			iv_next_three,// 3小时
			iv_next_six,// 6小时
			iv_next_nine,// 9小时
			iv_next_twelve,// 12小时
			iv_next_fifteen,// 15小时
			iv_today_weather,// 今天
			iv_tommorrow_weather,// 明天
			iv_thirdday_weather,// 第三天
			iv_fourthday_weather;// 第四天

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_main);
		mContext=this;
		WeatherData data = WeatherData.getInstance();
		parse3h(data);
		// data.getForecast3h(cityname, jsonCallBack);
		 initWeatherData(data);
		 initView();

	}

	/**
	 * 未来3小时内容  
	 * 
	 * @param data
	 */
	private List<HoursWeatherBean> parse3h(WeatherData data) {
		data.getForecast3h(chageCity, new JsonCallBack() {
			@Override
			public void jsonLoaded(JSONObject json) {
				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmss");
				Date date = new Date(System.currentTimeMillis());
				try {
					int code = json.getInt("resultcode");
					int error_code = json.getInt("error_code");
					if (error_code == 0 && code == 200) {
						JSONArray array = json.getJSONArray("result");
						for (int i = 0; i < array.length(); i++) {
							JSONObject hjson = array.getJSONObject(i);
							Date sdate = sf.parse(hjson.getString("sfdate"));

							if (!sdate.after(date)) {
								continue;
							}
							HoursWeatherBean bean = new HoursWeatherBean();
							bean.setWeather_id(hjson.getString("weatherid"));
							bean.setTemp(hjson.getString("temp1"));
							Calendar c = Calendar.getInstance();
							c.setTime(sdate);
							bean.setTime(c.get(Calendar.HOUR_OF_DAY) + "");
							list.add(bean);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		return list;
	}

	private void initView() {
		top_city = (TextView) findViewById(R.id.top_city);
		tv_release = (TextView) findViewById(R.id.tv_release);
		tv_now_weather = (TextView) findViewById(R.id.tv_now_weather);
		tv_today_temp = (TextView) findViewById(R.id.tv_today_temp);
		tv_now_temp = (TextView) findViewById(R.id.tv_now_temp);
		tv_aqi = (TextView) findViewById(R.id.tv_aqi);
		tv_quality = (TextView) findViewById(R.id.tv_quality);
		tv_today_temp_a = (TextView) findViewById(R.id.tv_today_temp_a);
		tv_today_temp_b = (TextView) findViewById(R.id.tv_today_temp_b);
		iv_now_weather = (ImageView) findViewById(R.id.iv_now_weather);
		iv_today_weather = (ImageView) findViewById(R.id.iv_today_weather);
	}

	/**
	 * 初始化天气数据
	 */
	private void initWeatherData(WeatherData data) {
		data.getByCitys(chageCity, 2, new JsonCallBack() {
			@Override
			public void jsonLoaded(JSONObject obj) {
				setWeatherViews(parseWeather(obj));
			}

		});
	}

	/**
	 * 天气数据赋值
	 * 
	 * @param parseWeather
	 */
	private void setWeatherViews(WeatherBean bean) {
		top_city.setText(bean.getCity());
		tv_release.setText(bean.getRelease());

		tv_now_weather.setText(bean.getWeather_str());
		String[] tempArr = bean.getTemp().split("~");
		String temp_str_a = tempArr[1].substring(0, tempArr[1].indexOf("℃"));
		String temp_str_b = tempArr[0].substring(0, tempArr[0].indexOf("℃"));
		// 温度 8℃~16℃" ↑ ↓ °
		tv_today_temp.setText("↑ " + temp_str_a + "°   ↓" + temp_str_b + "°");
		tv_now_temp.setText(bean.getNow_temp() + " °");
		iv_today_weather.setImageResource(getResources().getIdentifier("d" + bean.getWeather_id(), "drawable", mContext.getPackageName()));

		tv_today_temp_a.setText(temp_str_a + "°");
		tv_today_temp_b.setText(temp_str_b + "°");
		List<FutureWeatherBean> futureList = bean.getfList();
		/*if (futureList != null && futureList.size() == 3) {
			setFutureData(tv_tommorrow, iv_tommorrow_weather, tv_tommorrow_temp_a, tv_tommorrow_temp_b, futureList.get(0));
			setFutureData(tv_thirdday, iv_thirdday_weather, tv_thirdday_temp_a, tv_thirdday_temp_b, futureList.get(1));
			setFutureData(tv_fourthday, iv_fourthday_weather, tv_fourthday_temp_a, tv_fourthday_temp_b, futureList.get(2));
		}*/
		Calendar c = Calendar.getInstance();
		int time = c.get(Calendar.HOUR_OF_DAY);
		String prefixStr = null;
		if (time >= 6 && time < 18) {
			prefixStr = "d";
		} else {
			prefixStr = "n";
		}
		iv_now_weather.setImageResource(getResources().getIdentifier(prefixStr + bean.getWeather_id(), "drawable", mContext.getPackageName()));

		
	}

	/**
	 * 解析天气.
	 * 
	 * @param obj
	 */
	private WeatherBean parseWeather(JSONObject json) {
		WeatherBean bean = null;

		try {
			int code = json.getInt("resultcode");
			int error_code = json.getInt("error_code");
			if (error_code == 0 && code == 200) {
				bean = new WeatherBean();
				JSONObject jsonResult = json.getJSONObject("result");
				// toady
				JSONObject todayJson = jsonResult.getJSONObject("today");
				bean.setCity(todayJson.getString("city"));
				bean.setUv_index(todayJson.getString("uv_index"));
				bean.setTemp(todayJson.getString("temperature"));
				bean.setWeather_str(todayJson.getString("weather"));
				bean.setWeather_id(todayJson.getJSONObject("weather_id")
						.getString("fa"));
				bean.setDressing_index(todayJson.getString("dressing_index"));
				// sk
				JSONObject skJson = jsonResult.getJSONObject("sk");
				bean.setWind(skJson.getString("wind_direction")
						+ skJson.getString("wind_strength"));
				bean.setNow_temp(skJson.getString("temp"));
				bean.setRelease(skJson.getString("time"));
				bean.setHumidity(skJson.getString("humidity"));
				// 获取未来三天天气情况
				JSONArray futureArray = jsonResult.getJSONArray("future");
				List<FutureWeatherBean> list = new ArrayList<FutureWeatherBean>();
				for (int i = 0; i < futureArray.length(); i++) {
					JSONObject futureJson = futureArray.getJSONObject(i);
					FutureWeatherBean futureBean = new FutureWeatherBean();
					futureBean.setTemp(futureJson.getString("temperature"));
					if (i == 0) {
						futureBean.setWeek(futureJson.getString("今天"));
					} else {
						futureBean.setWeek(futureJson.getString("week"));

					}
					futureBean.setWeather_id(futureJson.getJSONObject(
							"weather_id").getString("fa"));
					list.add(futureBean);
					if (list.size() == 4) {
						break;
					}
				}

				bean.setfList(list);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bean;
	}
}
