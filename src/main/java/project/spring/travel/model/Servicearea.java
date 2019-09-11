package project.spring.travel.model;

/**
 * @fileName    : Servicearea.java
 * @author      : 홍준성
 * @description : 휴게소의 기본 데이터가 들어갈 Beans Class
 * @lastUpdate  : 2019-04-22
 */
/**
 * MyBatis 프로젝트에 라이브러리 설정하기
 * 기본 생성자의 사용을 위해서 파라미터가 포함된 생성자를 생략한다.
 * -> MyBatis에서는 본디 이 형태를 권장한다. (코드 단순화)
 */
public class Servicearea {
    // 데이터베이스 테이블의 컬럼을 표현하는 멤버변수
    private int serviceareaId; // 휴게소 일련번호
    private String unitName; // 휴게소명
    private String unitCode; // 휴게소코드
    private String routeNo; // 노선코드
    private String routeName; // 노선명
    private String xValue; // 휴게소 X좌표값(WGS84 경위도 좌표)
    private String yValue; // 휴게소 Y좌표값(WGS84 경위도 좌표)
    private String regDate; // 작성일
    private String editDate = null; // 수정일
    
    public int getServiceareaId() {
        return serviceareaId;
    }
    public void setServiceareaId(int serviceareaId) {
        this.serviceareaId = serviceareaId;
    }
    public String getUnitName() {
        return unitName;
    }
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    public String getUnitCode() {
        return unitCode;
    }
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
    public String getRouteNo() {
        return routeNo;
    }
    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }
    public String getRouteName() {
        return routeName;
    }
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
    public String getxValue() {
        return xValue;
    }
    public void setxValue(String xValue) {
        this.xValue = xValue;
    }
    public String getyValue() {
        return yValue;
    }
    public void setyValue(String yValue) {
        this.yValue = yValue;
    }
    public String getRegDate() {
        return regDate;
    }
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
    public String getEditDate() {
        return editDate;
    }
    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }
    
    @Override
    public String toString() {
        return "Servicearea [serviceareaId=" + serviceareaId + ", unitName=" + unitName + ", unitCode=" + unitCode
                + ", routeNo=" + routeNo + ", routeName=" + routeName + ", xValue=" + xValue + ", yValue=" + yValue
                + ", regDate=" + regDate + ", editDate=" + editDate + "]";
    }
    
}
