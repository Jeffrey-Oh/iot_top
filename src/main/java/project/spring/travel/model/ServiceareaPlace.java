package project.spring.travel.model;

/**
 * @fileName    : ServiceareaLocal.java
 * @author      : 홍준성
 * @description : 휴게소의 위치 데이터가 들어갈 Beans Class
 * @lastUpdate  : 2019-04-22
 */
public class ServiceareaPlace {
    private int servicearea_placeId; // 휴게소 위치 일련번호
    private String query; // 질의어
    private String queryX = null; // 중심 좌표의 X값 혹은 longitude
    private String queryY = null; // 중심 좌표의 X값 혹은 latitude
    private String id; // 장소 ID
    private String category_name; // 카테고리 이름
    private String place_name; // 장소명, 업체명
    private String phone = null; // 전화번호
    private String address_name; // 전체 지번 주소
    private String road_address_name; // 전체 도로명 주소
    private String place_url; // 장소 상세페이지 URL
    private String distance = null; // 중심좌표까지의 거리(x, y 파라미터를 준 경우에만 존재). 단위 meter
    private String x; // X 좌표값 혹은 longitude
    private String y; // Y 좌표값 혹은 latitude
    private String regDate; // 작성일
    private String editDate = null; // 수정일
    
    public int getServicearea_placeId() {
        return servicearea_placeId;
    }
    public void setServicearea_placeId(int servicearea_placeId) {
        this.servicearea_placeId = servicearea_placeId;
    }
    public String getQuery() {
        return query;
    }
    public void setQuery(String query) {
        this.query = query;
    }
    public String getQueryX() {
        return queryX;
    }
    public void setQueryX(String queryX) {
        this.queryX = queryX;
    }
    public String getQueryY() {
        return queryY;
    }
    public void setQueryY(String queryY) {
        this.queryY = queryY;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCategory_name() {
        return category_name;
    }
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
    public String getPlace_name() {
        return place_name;
    }
    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress_name() {
        return address_name;
    }
    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }
    public String getRoad_address_name() {
        return road_address_name;
    }
    public void setRoad_address_name(String road_address_name) {
        this.road_address_name = road_address_name;
    }
    public String getPlace_url() {
        return place_url;
    }
    public void setPlace_url(String place_url) {
        this.place_url = place_url;
    }
    public String getDistance() {
        return distance;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }
    public String getX() {
        return x;
    }
    public void setX(String x) {
        this.x = x;
    }
    public String getY() {
        return y;
    }
    public void setY(String y) {
        this.y = y;
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
        return "ServiceareaPlace [servicearea_placeId=" + servicearea_placeId + ", query=" + query + ", queryX="
                + queryX + ", queryY=" + queryY + ", id=" + id + ", category_name=" + category_name + ", place_name="
                + place_name + ", phone=" + phone + ", address_name=" + address_name + ", road_address_name="
                + road_address_name + ", place_url=" + place_url + ", distance=" + distance + ", x=" + x + ", y=" + y
                + ", regDate=" + regDate + ", editDate=" + editDate + "]";
    }
    
}
