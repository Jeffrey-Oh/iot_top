package project.spring.travel.model;

public class CulturePerformance {
	private int cultureId;
	private int seq;
	private String title;
	private Integer startDate = null;
	private Integer endDate = null;
	private String place;
	private String realmName;
	private String area;
	private String gpsX;
	private String gpsY;
	private String thumbnail;
	private String subTitle;
	private String price;    
	private String contents1;      
	private String contents2;      
	private String url;            
	private String phone;          
	private String imgUrl;         
	private String placeUrl;       
	private String placeAddr;
	private int cultureLike;
	private int limitStart;
	private int listCount;
	public int getCultureId() {
		return cultureId;
	}
	public void setCultureId(int cultureId) {
		this.cultureId = cultureId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getStartDate() {
		return startDate;
	}
	public void setStartDate(Integer startDate) {
		this.startDate = startDate;
	}
	public Integer getEndDate() {
		return endDate;
	}
	public void setEndDate(Integer endDate) {
		this.endDate = endDate;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getRealmName() {
		return realmName;
	}
	public void setRealmName(String realmName) {
		this.realmName = realmName;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getGpsX() {
		return gpsX;
	}
	public void setGpsX(String gpsX) {
		this.gpsX = gpsX;
	}
	public String getGpsY() {
		return gpsY;
	}
	public void setGpsY(String gpsY) {
		this.gpsY = gpsY;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getContents1() {
		return contents1;
	}
	public void setContents1(String contents1) {
		this.contents1 = contents1;
	}
	public String getContents2() {
		return contents2;
	}
	public void setContents2(String contents2) {
		this.contents2 = contents2;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getPlaceUrl() {
		return placeUrl;
	}
	public void setPlaceUrl(String placeUrl) {
		this.placeUrl = placeUrl;
	}
	public String getPlaceAddr() {
		return placeAddr;
	}
	public void setPlaceAddr(String placeAddr) {
		this.placeAddr = placeAddr;
	}
	public int getCultureLike() {
		return cultureLike;
	}
	public void setCultureLike(int cultureLike) {
		this.cultureLike = cultureLike;
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
	@Override
	public String toString() {
		return "CulturePerformance [cultureId=" + cultureId + ", seq=" + seq + ", title=" + title + ", startDate="
				+ startDate + ", endDate=" + endDate + ", place=" + place + ", realmName=" + realmName + ", area="
				+ area + ", gpsX=" + gpsX + ", gpsY=" + gpsY + ", thumbnail=" + thumbnail + ", subTitle=" + subTitle
				+ ", price=" + price + ", contents1=" + contents1 + ", contents2=" + contents2 + ", url=" + url
				+ ", phone=" + phone + ", imgUrl=" + imgUrl + ", placeUrl=" + placeUrl + ", placeAddr=" + placeAddr
				+ ", cultureLike=" + cultureLike + ", limitStart=" + limitStart + ", listCount=" + listCount + "]";
	}
	
	
	
	
	
	
	
}
