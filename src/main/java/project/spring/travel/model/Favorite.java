package project.spring.travel.model;

/**
 * 좋아요 처리를 위한 JAVA Beans
 * 
 * @author - 임형진
 * @lastupdate - 2019. 5. 3.
 * @filename - Favorite.java
 */

public class Favorite {
	
	private int favoriteId;
	private Integer memberId = null;
	private String regDate;
	private String thumbnail;
	private String refType;
	private String link;
	private String title;
	private String ctitle;
	private String cftitle;
	private String serviceareaName;
	private String firstImage;
	private Integer servicearea_groupId = null;
	private Integer tourId = null;
	private Integer cultureId = null;
	private Integer boardId = null;
	private String imageUrl;
	private Integer seq = null;
	private int limitStart;
	private int listCount;
	private Integer contentId = null;
	private String category;
	
	
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getContentId() {
		return contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	public String getFirstImage() {
		return firstImage;
	}
	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}
	public String getCftitle() {
		return cftitle;
	}
	public void setCftitle(String cftitle) {
		this.cftitle = cftitle;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public int getFavoriteId() {
		return favoriteId;
	}
	public void setFavoriteId(int favoriteId) {
		this.favoriteId = favoriteId;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getRefType() {
		return refType;
	}
	public void setRefType(String refType) {
		this.refType = refType;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCtitle() {
		return ctitle;
	}
	public void setCtitle(String ctitle) {
		this.ctitle = ctitle;
	}
	public String getServiceareaName() {
		return serviceareaName;
	}
	public void setServiceareaName(String serviceareaName) {
		this.serviceareaName = serviceareaName;
	}
	public Integer getServicearea_groupId() {
		return servicearea_groupId;
	}
	public void setServicearea_groupId(Integer servicearea_groupId) {
		this.servicearea_groupId = servicearea_groupId;
	}
	public Integer getTourId() {
		return tourId;
	}
	public void setTourId(Integer tourId) {
		this.tourId = tourId;
	}
	public Integer getCultureId() {
		return cultureId;
	}
	public void setCultureId(Integer cultureId) {
		this.cultureId = cultureId;
	}
	public Integer getBoardId() {
		return boardId;
	}
	public void setBoardId(Integer boardId) {
		this.boardId = boardId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
		return "Favorite [favoriteId=" + favoriteId + ", memberId=" + memberId + ", regDate=" + regDate + ", thumbnail="
				+ thumbnail + ", refType=" + refType + ", link=" + link + ", title=" + title + ", ctitle=" + ctitle
				+ ", cftitle=" + cftitle + ", serviceareaName=" + serviceareaName + ", firstImage=" + firstImage
				+ ", servicearea_groupId=" + servicearea_groupId + ", tourId=" + tourId + ", cultureId=" + cultureId
				+ ", boardId=" + boardId + ", imageUrl=" + imageUrl + ", seq=" + seq + ", limitStart=" + limitStart
				+ ", listCount=" + listCount + ", contentId=" + contentId + ", category=" + category + "]";
	}
	
	
	
	
	
	
	
}
