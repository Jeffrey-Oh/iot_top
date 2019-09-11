package project.spring.travel.model;

/**
 * 커뮤니티 게시판 댓글 Reply Beans
 * 
 * @author - 오태현
 * @lastupdate - 2019. 2. 15.
 * @filename - BoardReply.java
 */
public class BoardReply {
	private int replyId;
	private String userName;
	private String content;
	private int replyLike;
	private String regDate;
	private String editDate;
	private int boardId;
	private int memberId;
	private int limitStart;
	private int listCount;
	private String chk = "N";
	private String chkMemberId = "N";
	private String grade;

	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
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

	public int getReplyLike() {
		return replyLike;
	}

	public void setReplyLike(int replyLike) {
		this.replyLike = replyLike;
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

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
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

	public String getChk() {
		return chk;
	}

	public void setChk(String chk) {
		this.chk = chk;
	}

	public String getChkMemberId() {
		return chkMemberId;
	}

	public void setChkMemberId(String chkMemberId) {
		this.chkMemberId = chkMemberId;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "BoardReply [replyId=" + replyId + ", userName=" + userName + ", content=" + content + ", replyLike="
				+ replyLike + ", regDate=" + regDate + ", editDate=" + editDate + ", boardId=" + boardId + ", memberId="
				+ memberId + ", limitStart=" + limitStart + ", listCount=" + listCount + ", chk=" + chk
				+ ", chkMemberId=" + chkMemberId + ", grade=" + grade + "]";
	}

}
