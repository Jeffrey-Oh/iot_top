package project.spring.travel.model;

public class Weather {
	private int weather_id;
	private float temp;
	private int humidity;
	private float temp_min;
	private float temp_max;
	private float speed;
	private String icon;
	private String dt_txt;
	private String query;
	private float minTemp;
	private float maxTemp;
	
	
	
	public float getMinTemp() {
		return minTemp;
	}
	public void setMinTemp(float minTemp) {
		this.minTemp = minTemp;
	}
	public float getMaxTemp() {
		return maxTemp;
	}
	public void setMaxTemp(float maxTemp) {
		this.maxTemp = maxTemp;
	}
	public int getWeather_id() {
		return weather_id;
	}
	public void setWeather_id(int weather_id) {
		this.weather_id = weather_id;
	}
	public float getTemp() {
		return temp;
	}
	public void setTemp(float temp) {
		this.temp = temp;
	}
	public int getHumidity() {
		return humidity;
	}
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	public float getTemp_min() {
		return temp_min;
	}
	public void setTemp_min(float temp_min) {
		this.temp_min = temp_min;
	}
	public float getTemp_max() {
		return temp_max;
	}
	public void setTemp_max(float temp_max) {
		this.temp_max = temp_max;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getDt_txt() {
		return dt_txt;
	}
	public void setDt_txt(String dt_txt) {
		this.dt_txt = dt_txt;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	@Override
	public String toString() {
		return "Weather [weather_id=" + weather_id + ", temp=" + temp + ", humidity=" + humidity + ", temp_min="
				+ temp_min + ", temp_max=" + temp_max + ", speed=" + speed + ", icon=" + icon + ", dt_txt=" + dt_txt
				+ ", query=" + query + ", minTemp=" + minTemp + ", maxTemp=" + maxTemp + "]";
	}
	
}
