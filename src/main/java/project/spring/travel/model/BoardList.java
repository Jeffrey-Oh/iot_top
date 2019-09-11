package project.spring.travel.model;

/**
 * 커뮤니티 게시판 List Beans
 * 
 * @author - 오태현
 * @lastupdate - 2019. 2. 15.
 * @filename - BoardList.java
 */
public class BoardList {
	private int boardId;
	private String category;
	private String korCtg;
	private String title;
	private String userName;
	private String content;
	private int boardCount;
	private int boardLike;
	private String regDate;
	private String editDate;
	private int memberId;
	private String type;
	private String noticeChk = "off";
	private int limitStart;
	private int listCount;

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getKorCtg() {
		return korCtg;
	}

	public void setKorCtg(String korCtg) {
		this.korCtg = korCtg;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getBoardCount() {
		return boardCount;
	}

	public void setBoardCount(int boardCount) {
		this.boardCount = boardCount;
	}

	public int getBoardLike() {
		return boardLike;
	}

	public void setBoardLike(int boardLike) {
		this.boardLike = boardLike;
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

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNoticeChk() {
		return noticeChk;
	}

	public void setNoticeChk(String noticeChk) {
		this.noticeChk = noticeChk;
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
		return "BoardList [boardId=" + boardId + ", category=" + category + ", korCtg=" + korCtg + ", title=" + title
				+ ", userName=" + userName + ", content=" + content + ", boardCount=" + boardCount + ", boardLike="
				+ boardLike + ", regDate=" + regDate + ", editDate=" + editDate + ", memberId=" + memberId + ", type="
				+ type + ", noticeChk=" + noticeChk + ", limitStart=" + limitStart + ", listCount=" + listCount + "]";
	}

}
