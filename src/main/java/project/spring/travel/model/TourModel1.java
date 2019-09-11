/**
 * @Author: choseonjun
 * @Date:   2019-04-10T14:43:32+09:00
 * @Email:  seonjun92@naver.com
 * @ProjectName:
 * @Filename: TourModel1.java
 * @Last modified by:   choseonjun
 * @Last modified time: 2019-04-10T17:32:33+09:00
 */

package project.spring.travel.model;


public class TourModel1 {

	public int tourId; // 투어컬럼번호.
	public String addr1; // 상세주소
	public String addr2; // 주소
	public int areacode;	//지역코드
	public int tuMapInfo;		//주변 지역설명(번호)
	public int contentid;		// json 생성코드
	public long createdtime; 	// 생성날짜
	public String firstimage;	// 이미지1
	public String firstimage2;	// 이미지2
	public int mlevel;		//번호코드
	public long modifiedtime;		// 수정날짜
	public String tuInformationText;	// 텍스트 정보
	public String tel; // 전화번호
	public String title; // 제목
	public String tourLike; // 공유

	public int getTourId() {
		return tourId;
	}
	public void setTourId(int tourId) {
		this.tourId = tourId;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public int getAreacode() {
		return areacode;
	}
	public void setAreacode(int areacode) {
		this.areacode = areacode;
	}
	public int getTuMapInfo() {
		return tuMapInfo;
	}
	public void setTuMapInfo(int tuMapInfo) {
		this.tuMapInfo = tuMapInfo;
	}
	public int getContentid() {
		return contentid;
	}
	public void setContentid(int contentid) {
		this.contentid = contentid;
	}
	public long getCreatedtime() {
		return createdtime;
	}
	public void setCreatedtime(long createdtime) {
		this.createdtime = createdtime;
	}
	public String getFirstimage() {
		return firstimage;
	}
	public void setFirstimage(String firstimage) {
		this.firstimage = firstimage;
	}
	public String getFirstimage2() {
		return firstimage2;
	}
	public void setFirstimage2(String firstimage2) {
		this.firstimage2 = firstimage2;
	}
	public int getMlevel() {
		return mlevel;
	}
	public void setMlevel(int mlevel) {
		this.mlevel = mlevel;
	}
	public long getModifiedtime() {
		return modifiedtime;
	}
	public void setModifiedtime(long modifiedtime) {
		this.modifiedtime = modifiedtime;
	}
	public String getTuInformationText() {
		return tuInformationText;
	}
	public void setTuInformationText(String tuInformationText) {
		this.tuInformationText = tuInformationText;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTourLike() {
		return tourLike;
	}
	public void setTourLike(String tourLike) {
		this.tourLike = tourLike;
	}


	@Override
	public String toString() {
		return "TourModel1 [tourId=" + tourId + ", addr1=" + addr1 + ", addr2=" + addr2 + ", areacode=" + areacode
				+ ", tuMapInfo=" + tuMapInfo + ", contentid=" + contentid + ", createdtime=" + createdtime
				+ ", firstimage=" + firstimage + ", firstimage2=" + firstimage2 + ", mlevel=" + mlevel
				+ ", modifiedtime=" + modifiedtime + ", tuInformationText=" + tuInformationText + ", tel=" + tel
				+ ", title=" + title + ", tourLike=" + tourLike + "]";
	}


}
