package project.spring.travel.model;

/**
 * @fileName    : ServiceareaImg.java
 * @author      : 홍준성
 * @description : 휴게소의 이미지 데이터가 들어갈 Beans Class
 * @lastUpdate  : 2019-04-22
 */
public class ServiceareaImage {
    private int servicearea_imageId; // 휴게소 이미지 일련번호;
    private String query; // 질의어
    private String image_url; // 이미지 URL
    private String thumbnail_url; // 이미지 썸네일 URL
    private int width; // 이미지의 가로 크기
    private int height; // 이미지의 세로 크기
    private String regDate; // 작성일
    private String editDate = null; // 수정일
    
    public int getServicearea_imageId() {
        return servicearea_imageId;
    }
    public void setServicearea_imageId(int servicearea_imageId) {
        this.servicearea_imageId = servicearea_imageId;
    }
    public String getQuery() {
        return query;
    }
    public void setQuery(String query) {
        this.query = query;
    }
    public String getImage_url() {
        return image_url;
    }
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
    public String getThumbnail_url() {
        return thumbnail_url;
    }
    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
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
        return "ServiceareaImage [servicearea_imageId=" + servicearea_imageId + ", query=" + query + ", image_url="
                + image_url + ", thumbnail_url=" + thumbnail_url + ", width=" + width + ", height=" + height
                + ", regDate=" + regDate + ", editDate=" + editDate + "]";
    }
    
}
