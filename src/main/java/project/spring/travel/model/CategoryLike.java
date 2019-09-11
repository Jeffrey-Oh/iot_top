package project.spring.travel.model;

public class CategoryLike {
	private int categoryLikeId;
	private int memberId;
	private int boardId;
	private int servicearea_groupId;
	private int cultureId;
	private int tourId;
	private String regDate;
	private String editDate;

	public int getCategoryLikeId() {
		return categoryLikeId;
	}

	public void setCategoryLikeId(int categoryLikeId) {
		this.categoryLikeId = categoryLikeId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public int getServicearea_groupId() {
		return servicearea_groupId;
	}

	public void setServicearea_groupId(int servicearea_groupId) {
		this.servicearea_groupId = servicearea_groupId;
	}

	public int getCultureId() {
		return cultureId;
	}

	public void setCultureId(int cultureId) {
		this.cultureId = cultureId;
	}

	public int getTourId() {
		return tourId;
	}

	public void setTourId(int tourId) {
		this.tourId = tourId;
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
		return "CategoryLike [categoryLikeId=" + categoryLikeId + ", memberId=" + memberId + ", boardId=" + boardId
				+ ", servicearea_groupId=" + servicearea_groupId + ", cultureId=" + cultureId + ", tourId=" + tourId
				+ ", regDate=" + regDate + ", editDate=" + editDate + "]";
	}

}
