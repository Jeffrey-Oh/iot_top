package project.spring.travel.model;

/**
 * 공항 코드 집합체 JAVA Beans
 * @author     - JEFFREY_OH
 * @lastupdate - 2019. 2. 13.
 * @filename   - Code.java
 */
public class Code {
	private String cityKor;		// 국문 항공명
	private String cityCode;	// 영문 항공명

	public String getCityKor() {
		return cityKor;
	}

	public void setCityKor(String cityKor) {
		this.cityKor = cityKor;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Override
	public String toString() {
		return "BoardBeans [cityKor=" + cityKor + ", cityCode=" + cityCode + "]";
	}

}
