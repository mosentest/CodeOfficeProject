package mu.codeoffice.entity;

import java.util.Date;
import java.util.List;

public class AskSolution {
	
	public static final int HIDE_LIMIT = -20;

	private long id;
	private long questionId;
	private String solution;
	private User creator;
	private Date create;
	private int votes;
	private boolean accepted;
	private boolean hide;
	private List<AskComment> comments;
	
	public AskSolution() {}
	
	public void creationInit() {
		create = new Date();
		votes = 0;
		accepted = false;
		hide = false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Date getCreate() {
		return create;
	}

	public void setCreate(Date create) {
		this.create = create;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public List<AskComment> getComments() {
		return comments;
	}

	public void setComments(List<AskComment> comments) {
		this.comments = comments;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	
	
}
