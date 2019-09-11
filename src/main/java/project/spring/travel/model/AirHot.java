package project.spring.travel.model;

public class AirHot {
	private int hotId;
	private String title;
	private String content;
	private String imageUrl;
	private String arrivalCity;
	private int limitStart;
	private int listCount;
	private String sql;

	public int getHotId() {
		return hotId;
	}

	public void setHotId(int hotId) {
		this.hotId = hotId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public int getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	public int getListCount() {
		return listCount;
	}

	public void setListCount(int listCount) {
		this.listCount = listCount;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	@Override
	public String toString() {
		return "AirHot [hotId=" + hotId + ", title=" + title + ", content=" + content + ", imageUrl=" + imageUrl
				+ ", arrivalCity=" + arrivalCity + ", limitStart=" + limitStart + ", listCount=" + listCount + ", sql="
				+ sql + "]";
	}

}
