package project.spring.travel.model;

/**
 * 운항 스케줄 서비스 처리를 위한 JAVA Beans
 * 
 * @author - JEFFREY_OH
 * @lastupdate - 2019. 2. 13.
 * @filename - Domestic.java
 */
public class Domestic {

	private String domesticId;
	private String airlineKorean; // 항공사
	private String domesticNum; // 항공편명
	private String startCity; // 출발공항
	private String startCityCode; // 출발공항코드
	private String arrivalCity; // 도착공항
	private String arrivalCityCode; // 도착공항코드
	private String domesticStartTime; // 출발시간
	private String domesticArrivalTime; // 도착시간
	private String domesticStdate; // 항공운영 시작일자
	private String domesticEddate; // 항공운영 마감일자
	private String domesticMon; // 운항요일
	private String domesticTue; // 운항요일
	private String domesticWed; // 운항요일
	private String domesticThu; // 운항요일
	private String domesticFri; // 운항요일
	private String domesticSat; // 운항요일
	private String domesticSun; // 운항요일
	private int groupId; // 그룹화
	private int limitStart;
	private int listCount;
	private String logo;

	public String getDomesticId() {
		return domesticId;
	}

	public void setDomesticId(String domesticId) {
		this.domesticId = domesticId;
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

	public String getDomesticStdate() {
		return domesticStdate;
	}

	public void setDomesticStdate(String domesticStdate) {
		this.domesticStdate = domesticStdate;
	}

	public String getDomesticEddate() {
		return domesticEddate;
	}

	public void setDomesticEddate(String domesticEddate) {
		this.domesticEddate = domesticEddate;
	}

	public String getDomesticMon() {
		return domesticMon;
	}

	public void setDomesticMon(String domesticMon) {
		this.domesticMon = domesticMon;
	}

	public String getDomesticTue() {
		return domesticTue;
	}

	public void setDomesticTue(String domesticTue) {
		this.domesticTue = domesticTue;
	}

	public String getDomesticWed() {
		return domesticWed;
	}

	public void setDomesticWed(String domesticWed) {
		this.domesticWed = domesticWed;
	}

	public String getDomesticThu() {
		return domesticThu;
	}

	public void setDomesticThu(String domesticThu) {
		this.domesticThu = domesticThu;
	}

	public String getDomesticFri() {
		return domesticFri;
	}

	public void setDomesticFri(String domesticFri) {
		this.domesticFri = domesticFri;
	}

	public String getDomesticSat() {
		return domesticSat;
	}

	public void setDomesticSat(String domesticSat) {
		this.domesticSat = domesticSat;
	}

	public String getDomesticSun() {
		return domesticSun;
	}

	public void setDomesticSun(String domesticSun) {
		this.domesticSun = domesticSun;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "Domestic [domesticId=" + domesticId + ", airlineKorean=" + airlineKorean + ", domesticNum="
				+ domesticNum + ", startCity=" + startCity + ", startCityCode=" + startCityCode + ", arrivalCity="
				+ arrivalCity + ", arrivalCityCode=" + arrivalCityCode + ", domesticStartTime=" + domesticStartTime
				+ ", domesticArrivalTime=" + domesticArrivalTime + ", domesticStdate=" + domesticStdate
				+ ", domesticEddate=" + domesticEddate + ", domesticMon=" + domesticMon + ", domesticTue=" + domesticTue
				+ ", domesticWed=" + domesticWed + ", domesticThu=" + domesticThu + ", domesticFri=" + domesticFri
				+ ", domesticSat=" + domesticSat + ", domesticSun=" + domesticSun + ", groupId=" + groupId
				+ ", limitStart=" + limitStart + ", listCount=" + listCount + ", logo=" + logo + "]";
	}

}