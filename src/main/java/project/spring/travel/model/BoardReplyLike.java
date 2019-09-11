package project.spring.travel.model;

public class BoardReplyLike {
	private int replyLikeId;
	private int replyId;
	private int memberId;

	public int getReplyLikeId() {
		return replyLikeId;
	}

	public void setReplyLikeId(int replyLikeId) {
		this.replyLikeId = replyLikeId;
	}

	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return "BoardReplyLike [replyLikeId=" + replyLikeId + ", replyId=" + replyId + ", memberId=" + memberId + "]";
	}

}
