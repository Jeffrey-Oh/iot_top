package project.spring.travel.model;

public class BoardReplyReplyLike {
	private int replyReplyLikeId;
	private int replyReplyId;
	private int memberId;
	private int replyId;

	public int getReplyReplyLikeId() {
		return replyReplyLikeId;
	}

	public void setReplyReplyLikeId(int replyReplyLikeId) {
		this.replyReplyLikeId = replyReplyLikeId;
	}

	public int getReplyReplyId() {
		return replyReplyId;
	}

	public void setReplyReplyId(int replyReplyId) {
		this.replyReplyId = replyReplyId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	@Override
	public String toString() {
		return "BoardReplyReplyLike [replyReplyLikeId=" + replyReplyLikeId + ", replyReplyId=" + replyReplyId
				+ ", memberId=" + memberId + ", replyId=" + replyId + "]";
	}

}
