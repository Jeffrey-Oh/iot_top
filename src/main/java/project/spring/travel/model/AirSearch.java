package project.spring.travel.model;

/**
 * 항공권 검색 서비스를 위한 JAVA Beans
 * 
 * @author - JEFFREY_OH
 * @lastupdate - 2019. 2. 13.
 * @filename - Search.java
 */
public class AirSearch {
	private int airSearchId;
	private String airlineKorean; // 국문 항공사명
	private String domesticNum; // 편명
	private String startCity; // 출발지
	private String startCityCode; // 출발지 코드
	private String arrivalCity; // 도착지
	private String arrivalCityCode; // 도착지 코드
	private String domesticStartTime; // 출발 예상 시간
	private String domesticArrivalTime; // 도착 예상 시간
	private int std; // 운항시작날짜 DB에 저장된 값은 varchar이지만 조건을 가리기 위해 모델에서 int형으로 씀
	private int etd; // 운항마감날짜 DB에 저장된 값은 varchar이지만 조건을 가리기 위해 모델에서 int형으로 씀
	private String price; // 가격
	private String logo; // 항공사 로고
	private String sql; // 임의 값
	private int limitStart;
	private int listCount;

	public int getAirSearchId() {
		return airSearchId;
	}

	public void setAirSearchId(int airSearchId) {
		this.airSearchId = airSearchId;
	}

	public String getAirlineKorean() {
		return airlineKorean;
	}

	public void setAirlineKorean(String airlineKorean) {
		this.airlineKorean = airlineKorean;
	}

	public String getDomesticNum() {
		return domesticNum;
	}

	public void setDomesticNum(String domesticNum) {
		this.domesticNum = domesticNum;
	}

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getStartCityCode() {
		return startCityCode;
	}

	public void setStartCityCode(String startCityCode) {
		this.startCityCode = startCityCode;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public String getArrivalCityCode() {
		return arrivalCityCode;
	}

	public void setArrivalCityCode(String arrivalCityCode) {
		this.arrivalCityCode = arrivalCityCode;
	}

	public String getDomesticStartTime() {
		return domesticStartTime;
	}

	public void setDomesticStartTime(String domesticStartTime) {
		this.domesticStartTime = domesticStartTime;
	}

	public String getDomesticArrivalTime() {
		return domesticArrivalTime;
	}

	public void setDomesticArrivalTime(String domesticArrivalTime) {
		this.domesticArrivalTime = domesticArrivalTime;
	}

	public int getStd() {
		return std;
	}

	public void setStd(int std) {
		this.std = std;
	}

	public int getEtd() {
		return etd;
	}

	public void setEtd(int etd) {
		this.etd = etd;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
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
		return "AirSearch [airSearchId=" + airSearchId + ", airlineKorean=" + airlineKorean + ", domesticNum="
				+ domesticNum + ", startCity=" + startCity + ", startCityCode=" + startCityCode + ", arrivalCity="
				+ arrivalCity + ", arrivalCityCode=" + arrivalCityCode + ", domesticStartTime=" + domesticStartTime
				+ ", domesticArrivalTime=" + domesticArrivalTime + ", std=" + std + ", etd=" + etd + ", price=" + price
				+ ", logo=" + logo + ", sql=" + sql + ", limitStart=" + limitStart + ", listCount=" + listCount + "]";
	}

}
