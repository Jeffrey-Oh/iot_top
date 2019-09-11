package project.spring.travel.model;

/**
 * 실시간 항공 서비스 처리를 위한 JAVA Beans
 * 
 * @author - JEFFREY_OH
 * @lastupdate - 2019. 2. 13.
 * @filename - Live.java
 */
public class Live {
	private String liveId;
	private String airlineKorean; // 항공사 분류
	private String airFln; // 편명 분류
	private String airport; // 도착지 공항코드
	private String boardingKor; // 출발지 공항이름
	private String city; // 출발지 공항코드
	private String arrivedKor; // 도착지 공항이름
	private String std; // 계획시간
	private String etd; // 예상시간
	private String rmkKor; // 구분을 도착 or 출발
	private int groupId; // 데이터 그룹화
	private int limitStart;
	private int listCount;
	private long time;

	public String getLiveId() {
		return liveId;
	}

	public void setLiveId(String liveId) {
		this.liveId = liveId;
	}

	public String getAirlineKorean() {
		return airlineKorean;
	}

	public void setAirlineKorean(String airlineKorean) {
		this.airlineKorean = airlineKorean;
	}

	public String getAirFln() {
		return airFln;
	}

	public void setAirFln(String airFln) {
		this.airFln = airFln;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	public String getBoardingKor() {
		return boardingKor;
	}

	public void setBoardingKor(String boardingKor) {
		this.boardingKor = boardingKor;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArrivedKor() {
		return arrivedKor;
	}

	public void setArrivedKor(String arrivedKor) {
		this.arrivedKor = arrivedKor;
	}

	public String getStd() {
		return std;
	}

	public void setStd(String std) {
		this.std = std;
	}

	public String getEtd() {
		return etd;
	}

	public void setEtd(String etd) {
		this.etd = etd;
	}

	public String getRmkKor() {
		return rmkKor;
	}

	public void setRmkKor(String rmkKor) {
		this.rmkKor = rmkKor;
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

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Live [liveId=" + liveId + ", airlineKorean=" + airlineKorean + ", airFln=" + airFln + ", airport="
				+ airport + ", boardingKor=" + boardingKor + ", city=" + city + ", arrivedKor=" + arrivedKor + ", std="
				+ std + ", etd=" + etd + ", rmkKor=" + rmkKor + ", groupId=" + groupId + ", limitStart=" + limitStart
				+ ", listCount=" + listCount + ", time=" + time + "]";
	}

}
